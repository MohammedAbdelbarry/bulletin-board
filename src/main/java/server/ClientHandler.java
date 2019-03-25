package server;

import common.Request;
import common.RequestType;
import common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler extends Thread {
    private Socket socket;
    private FileHandler fileHandler;
    private AtomicInteger numberOfRequests;

    public ClientHandler(Socket socket, FileHandler fileHandler,
                            AtomicInteger numberOfRequests) {
        this.socket = socket;
        this.fileHandler = fileHandler;
        this.numberOfRequests = numberOfRequests;
    }

    public void run() {
        boolean isLast = false;
        Request request = null;
        Response response = null;
        do {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                request = (Request) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (request != null) {
                numberOfRequests.decrementAndGet(); // not interested in the return value
                isLast = request.isLast();
                if (request.getType() == RequestType.READ) {
                    try {
                        response = fileHandler.read(request.getClientId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        response = fileHandler.write(request.getClientId(), request.getBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } while (!isLast);
    }
}
