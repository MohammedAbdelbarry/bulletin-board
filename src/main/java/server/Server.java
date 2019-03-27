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
    private int  numberOfClients;
    private List<ClientHandler> clientHandlers;
    private FileHandler fileHandler;

    public Server(int serverPort, int numberOfClients) throws IOException {
        serverSocket = new ServerSocket(serverPort);
        fileHandler = new FileHandler();
        this.numberOfClients = numberOfClients;
    }

    public void start() {
        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (clientHandlers == null) {
                clientHandlers = new ArrayList<>();
            }
            ClientHandler clientHandler = new ClientHandler(clientSocket,
                        fileHandler);
            clientHandler.start();
            clientHandlers.add(clientHandler);
            numberOfClients--;
            if (numberOfClients == 0) {
                break;
            }
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
        fileHandler.log();
    }
}
