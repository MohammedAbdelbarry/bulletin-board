package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private AtomicInteger numberOfRequests;
    private List<ClientHandler> clientHandlers;
    private FileHandler fileHandler;

    public Server(int serverPort, int numberOfRequests) throws IOException {
        serverSocket = new ServerSocket(serverPort);
        fileHandler = new FileHandler();
        this.numberOfRequests = new AtomicInteger(numberOfRequests);
    }

    public void start() {
        while (numberOfRequests.get() != 0) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (clientHandlers == null) {
                clientHandlers = new ArrayList<>();
            }
            ClientHandler clientHandler = new ClientHandler(clientSocket,
                        fileHandler, numberOfRequests);
            clientHandler.start();
            clientHandlers.add(clientHandler);
        }
        stop();
    }

    public void stop() {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                clientHandler.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
