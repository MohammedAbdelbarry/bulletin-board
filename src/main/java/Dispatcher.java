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
    private static final String READER_JAR_PATH = "./reader.jar";
    private static final String WRITER_JAR_PATH = "./writer.jar";
    private static final String SERVER_JARPATH = "./server.jar";

    public Dispatcher() {

    }

    private void start(Configuration configuration) throws UnknownHostException, JSchException, SftpException {
        InetAddress serverIp = InetAddress.getByName(configuration.getServerInfo().getMachineAddress());
        // TODO: Test the nohup channel termination.
        startServer(configuration.getServerInfo(),
                configuration.getServerPort(),
                configuration.getReadersAddresses().size(),
                configuration.getWritersAddresses().size());
        for (int i = 0; i < configuration.getReadersAddresses().size(); i++) {
            startReader(configuration.getReadersAddresses().get(i),
                    serverIp,
                    configuration.getServerPort(),
                    configuration.getNumberOfAccesses());
        }
        for (int i = 0; i < configuration.getWritersAddresses().size(); i++) {
            startWriter(configuration.getWritersAddresses().get(i),
                    serverIp,
                    configuration.getServerPort(),
                    configuration.getNumberOfAccesses());
        }
    }

    private Session getSession(SSHCredentials creds) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(creds.getUserName(), creds.getMachineAddress());
        session.setPassword(creds.getPassword());
        session.connect();

        return session;
    }

    private void copyToRemote(Session session, String localPath, String remotePath) throws JSchException, SftpException {
        ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
        sftpChannel.connect();
        sftpChannel.cd("~");
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

    private void startServer(SSHCredentials serverInfo, int port, int numReaders,
                                int numWriters) throws JSchException, SftpException {
        Session session = getSession(serverInfo);
        copyToRemote(session, SERVER_JARPATH, SERVER_JARPATH);
        // Redirect stdout to client.log and stderr to stdout for ssh not to hang (more info here https://stackoverflow.com/a/6274137)
        execOnRemote(session, String.format("nohup java -jar %s %d %d %d > server.log  2>&1 &", SERVER_JARPATH, port, numReaders, numWriters));
    }

    private void startClient(String jarPath, SSHCredentials clientInfo,
                                InetAddress serverIp, int serverPort, int numAccesses) throws JSchException, SftpException {
        Session session = getSession(clientInfo);
        copyToRemote(session, jarPath, jarPath);
        // Redirect stdout to client.log and stderr to stdout for ssh not to hang (more info here https://stackoverflow.com/a/6274137)
        execOnRemote(session, String.format("nohup java -jar %s %s %d %d > client.log  2>&1 &", jarPath, serverIp, serverPort, numAccesses));
    }

    private void startReader(SSHCredentials readerInfo,
                                InetAddress serverIp, int serverPort, int numAccesses) throws SftpException, JSchException {
        startClient(READER_JAR_PATH, readerInfo, serverIp, serverPort, numAccesses);
    }

    private void startWriter(SSHCredentials writerInfo,
                                InetAddress serverIp, int serverPort, int numAccesses) throws SftpException, JSchException {
        startClient(WRITER_JAR_PATH, writerInfo, serverIp, serverPort, numAccesses);
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
