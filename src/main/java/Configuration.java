import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Configuration {
    private InetAddress serverIp;
    private int serverPort;
    private List<InetAddress> readersAddresses;
    private List<InetAddress> writersAddresses;
    private int numberOfAccesses;
    private static final String PROPERTY_PREFIX = "RW.";

    public Configuration(String filePath) throws IOException {
        readersAddresses = new ArrayList<>();
        writersAddresses = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Couldn't find configuration file in directory: " + file.getParentFile().getAbsolutePath());
        }
        Properties prop = new Properties();
        try (InputStream in = new FileInputStream(file)) {
            prop.load(in);
            serverIp = InetAddress.getByName(prop.getProperty(PROPERTY_PREFIX + "server"));
            serverPort = Integer.parseInt(prop.getProperty(PROPERTY_PREFIX + "server.port"));
            int numReaders = Integer.parseInt(prop.getProperty(PROPERTY_PREFIX + "numberOfReaders"));
            for (int i = 0; i < numReaders; i++) {
                readersAddresses.add(InetAddress.getByName(prop.getProperty(PROPERTY_PREFIX + "reader" + i)));
            }
            int numWriters = Integer.parseInt(prop.getProperty(PROPERTY_PREFIX + "numberOfWriters"));
            for (int i = 0; i < numWriters; i++) {
                writersAddresses.add(InetAddress.getByName(prop.getProperty(PROPERTY_PREFIX + "writer" + i)));
            }
            numberOfAccesses = Integer.parseInt(prop.getProperty(PROPERTY_PREFIX + "numberOfAccesses"));
        }
    }

    public InetAddress getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public List<InetAddress> getReadersAddresses() {
        return readersAddresses;
    }

    public List<InetAddress> getWritersAddresses() {
        return writersAddresses;
    }

    public int getNumberOfAccesses() {
        return numberOfAccesses;
    }
}
