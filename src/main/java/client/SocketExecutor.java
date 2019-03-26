package client;

import common.Request;
import common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketExecutor implements RequestExecutor {
    protected Socket socket;

    public SocketExecutor(String serverIP, int serverPort) {
        try {
            InetAddress address = InetAddress.getByName(serverIP);
            socket = new Socket(address, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response executeRequest(Request request) throws IOException, ClassNotFoundException {
        sendRequest(request);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return (Response) in.readObject();
    }

    private void sendRequest(Request request) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(request);
    }
}
