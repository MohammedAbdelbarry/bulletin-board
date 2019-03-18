import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Configuration {
    private SSHCredentials serverInfo;
    private int serverPort;
    private List<SSHCredentials> readersInfo;
    private List<SSHCredentials> writersInfo;
    private int numberOfAccesses;
    private static final String PROPERTY_PREFIX = "RW.";

    public Configuration(String filePath) throws IOException {
        readersInfo = new ArrayList<>();
        writersInfo = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Couldn't find configuration file in directory: " + file.getParentFile().getAbsolutePath());
        }
        Properties prop = new Properties();
        try (InputStream in = new FileInputStream(file)) {
            prop.load(in);
            serverInfo = getSSHCredentials(prop, PROPERTY_PREFIX + "server");
            serverPort = Integer.parseInt(prop.getProperty(PROPERTY_PREFIX + "server.port"));
            int numReaders = Integer.parseInt(prop.getProperty(PROPERTY_PREFIX + "numberOfReaders"));
            for (int i = 0; i < numReaders; i++) {
                readersInfo.add(getSSHCredentials(prop, PROPERTY_PREFIX + "reader" + i));
            }
            int numWriters = Integer.parseInt(prop.getProperty(PROPERTY_PREFIX + "numberOfWriters"));
            for (int i = 0; i < numWriters; i++) {
                writersInfo.add(getSSHCredentials(prop, PROPERTY_PREFIX + "writer" + i));
            }
            numberOfAccesses = Integer.parseInt(prop.getProperty(PROPERTY_PREFIX + "numberOfAccesses"));
        }
    }

    private SSHCredentials getSSHCredentials(Properties prop, String key) {
        String cred = prop.getProperty(key);
        String[] parts = cred.split("@");
        String password = prop.getProperty(key + ".password");
        return new SSHCredentials(parts[0], parts[1], password);
    }

    public SSHCredentials getServerInfo() {
        return serverInfo;
    }

    public int getServerPort() {
        return serverPort;
    }

    public List<SSHCredentials> getReadersAddresses() {
        return readersInfo;
    }

    public List<SSHCredentials> getWritersAddresses() {
        return writersInfo;
    }

    public int getNumberOfAccesses() {
        return numberOfAccesses;
    }
}
