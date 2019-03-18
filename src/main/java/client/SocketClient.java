package client;

import common.Request;
import common.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class SocketClient {

    protected Socket sock;
    protected int id;

    public SocketClient(int id, String serverIP, int port) {
        this.id = id;
        try {
            sock = new Socket(serverIP, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract void log(Response response) throws IOException;

    public void sendRequest(Request request) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
        out.writeObject(request);
    }

    protected void executeRequest(Request request) throws IOException, ClassNotFoundException {
        sendRequest(request);
        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
        Response response = (Response) in.readObject();
        log(response);
    }
}