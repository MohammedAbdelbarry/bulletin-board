package client;

import common.Request;
import common.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class Client {
    protected Socket socket;
    protected int id;

    public Client(int id, String serverIP, int serverPort) {
        this.id = id;
        try {
            socket = new Socket(serverIP, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract void log(Response response) throws IOException;

    public void sendRequest(Request request) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(request);
    }

    protected void executeRequest(Request request) throws IOException, ClassNotFoundException {
        sendRequest(request);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Response response = (Response) in.readObject();
        log(response);
    }
}