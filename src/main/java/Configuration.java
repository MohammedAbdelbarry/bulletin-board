import com.sun.tools.javac.util.List;

import java.net.InetAddress;

public class Configuration {
    private InetAddress serverIp;
    private int serverPort;
    private int clientPort;
    private List<InetAddress> readersAddresses;
    private List<InetAddress> writersAddresses;
    private int numberOfAccesses;

    public Configuration(String filePath) {
        //TODO: read and parse configuration file
    }

    public InetAddress getServerIp() {
        return serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getClientPort() {
        return clientPort;
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
