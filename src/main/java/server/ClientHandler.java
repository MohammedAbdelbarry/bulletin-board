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

    public ClientHandler(Socket socket, FileHandler fileHandler) {
        this.socket = socket;
        this.fileHandler = fileHandler;
    }

    public void run() {
        boolean isLast = false;
        Request request = null;
        Response response;
        do {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                request = (Request) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (request != null) {
                isLast = request.isLast();
                if (request.getType() == RequestType.READ) {
                    response = fileHandler.read(request.getClientId());
                } else {
                    response = fileHandler.write(request.getClientId(), request.getBody());
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
