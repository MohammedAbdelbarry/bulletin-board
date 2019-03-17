import java.net.InetAddress;

public class Dispatcher {
    private static final String READER_JAR_PATH = "./reader.jar";
    private static final String WRITER_JAR_PATH = "./writer.jar";

    public Dispatcher() {

    }

    private void start(Configuration configuration) {
        //TODO: start server and clients
    }

    private void startServer(InetAddress ip, int port, int numReaders, int numWriters) {
        //TODO: ssh stuff
    }

    private void startClient(String jarPath, InetAddress clientIp, int clientPort,
                                InetAddress serverIp, int serverPort, int numAccesses) {
        //TODO: ssh stuff
    }

    private void startReader(InetAddress readerIp, int readerPort,
                                InetAddress serverIp, int serverPort, int numAccesses) {
        startClient(READER_JAR_PATH, readerIp, readerPort, serverIp, serverPort, numAccesses);
    }

    private void startWriter(InetAddress writerIp, int writerPort,
                                InetAddress serverIp, int serverPort, int numAccesses) {
        startClient(WRITER_JAR_PATH, writerIp, writerPort, serverIp, serverPort, numAccesses);
    }

    public static void main(String[] args) {
        //TODO: construct configuration object and start dispatcher
    }
}
