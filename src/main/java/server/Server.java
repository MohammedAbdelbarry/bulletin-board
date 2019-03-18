package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private ServerSocket serverSocket;
    private int numReaders;
    private int numWriters;

    public Server(int serverPort, int numReaders, int numWriters) {
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.numReaders = numReaders;
        this.numWriters = numWriters;
    }

    public void start() {
        // TODO: start server
    }

    public void stop() {
        // TODO: stop server
    }
}
