package common;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Dispatcher {
    private static final String SERVER_JAR_PATH = "server.jar";
    private static final String CLIENT_JAR_PATH = "client.jar";

    public Dispatcher() {
        JSch.setConfig("StrictHostKeyChecking", "no");
    }

    private void start(Configuration configuration) throws UnknownHostException, JSchException, SftpException {
        InetAddress serverIp = InetAddress.getByName(configuration.getServerInfo().getMachineAddress());
        // TODO: Test the nohup channel termination.
        int numberOfClients = configuration.getReadersAddresses().size() + configuration.getWritersAddresses().size();
        int rmiPort = configuration.getRmiPort();
        startServer(configuration.getServerInfo(), configuration.getServerPort(),
                    numberOfClients, configuration.getNumberOfAccesses(), rmiPort);
        for (int i = 0; i < configuration.getReadersAddresses().size(); i++) {
            startReader(configuration.getReadersAddresses().get(i),
                    serverIp,
                    configuration.getServerPort(),
                    configuration.getNumberOfAccesses(),
                    i,
                    rmiPort);
            if (i < configuration.getWritersAddresses().size()) {
                startWriter(configuration.getWritersAddresses().get(i),
                        serverIp,
                        configuration.getServerPort(),
                        configuration.getNumberOfAccesses(),
                        i,
                        rmiPort);
            }
        }
        for (int i = configuration.getReadersAddresses().size(); i < configuration.getWritersAddresses().size(); i++) {
            startWriter(configuration.getWritersAddresses().get(i),
                    serverIp,
                    configuration.getServerPort(),
                    configuration.getNumberOfAccesses(),
                    i,
                    rmiPort);
        }
    }

    private Session getSession(SSHCredentials creds) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(creds.getUserName(), creds.getMachineAddress());
        System.out.println(creds.getUserName() + " " + creds.getMachineAddress() + " " + creds.getPassword());
        session.setPassword(creds.getPassword());
        session.connect();

        return session;
    }

    private void copyToRemote(Session session, String localPath, String remotePath) throws JSchException, SftpException {
        ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
//        sftpChannel.lcd(System.getProperty("user.dir"));
        System.out.println(sftpChannel.lpwd());
        sftpChannel.connect();
        sftpChannel.put(localPath, remotePath);
        sftpChannel.disconnect();
    }

    private void execOnRemote(Session session, String command) throws JSchException {
        ChannelExec execChannel = (ChannelExec) session.openChannel("exec");
        execChannel.setCommand(command);
        execChannel.setErrStream(System.err);
        execChannel.connect();
        execChannel.disconnect();
    }

    private void startServer(SSHCredentials serverInfo, int port, int numberOfClients, int numberOfAccesses, int rmiPort) throws JSchException, SftpException {
        Session session = getSession(serverInfo);
        copyToRemote(session, SERVER_JAR_PATH, SERVER_JAR_PATH);
        // Redirect stdout to client.log and stderr to stdout for ssh not to hang (more info here https://stackoverflow.com/a/6274137)
        execOnRemote(session, String.format("nohup java -jar %s %d %d %d %d > server.log  2>&1 &", SERVER_JAR_PATH, port, numberOfClients, numberOfAccesses, rmiPort));
        session.disconnect();
    }

    private void startClient(SSHCredentials clientInfo, String logFile, ClientType type,
                                InetAddress serverIp, int serverPort, int numAccesses, int clientId, int rmiPort) throws JSchException, SftpException {
        Session session = getSession(clientInfo);
        copyToRemote(session, CLIENT_JAR_PATH, CLIENT_JAR_PATH);
        // Redirect stdout to client.log and stderr to stdout for ssh not to hang (more info here https://stackoverflow.com/a/6274137)
        execOnRemote(session, String.format("nohup java -jar %s %s %s %d %d %d %d > %s  2>&1 &", CLIENT_JAR_PATH, type.toString(),
                serverIp.getHostAddress(), serverPort, numAccesses, clientId, rmiPort, logFile));
        session.disconnect();
    }

    private void startReader(SSHCredentials readerInfo, InetAddress serverIp, int serverPort,
                             int numAccesses, int readerId, int rmiPort) throws SftpException, JSchException {
        startClient(readerInfo, String.format("reader-%d.log", readerId), ClientType.READER,
                serverIp, serverPort, numAccesses, readerId, rmiPort);
    }

    private void startWriter(SSHCredentials writerInfo, InetAddress serverIp, int serverPort,
                             int numAccesses, int writerId, int rmiPort) throws SftpException, JSchException {
        startClient(writerInfo, String.format("writer-%d.log", writerId), ClientType.WRITER,
                serverIp, serverPort, numAccesses, writerId, rmiPort);
    }

    public static void main(String[] args) {
        try {
            Configuration conf = new Configuration("bulletin.properties");
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.start(conf);
        } catch (IOException | JSchException | SftpException e) {
            e.printStackTrace();
        }
    }
}
