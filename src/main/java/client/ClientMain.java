package client;

import common.ClientType;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // serverIp, serverPort, numAccesses, clientId, rmiPort
        if (args.length < 5) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        ClientType type = ClientType.valueOf(args[0]);
        String serverIp = args[1];
        int serverPort = Integer.parseInt(args[2]);
        int numRequests = Integer.parseInt(args[3]);
        int clientId = Integer.parseInt(args[4]);

        RequestExecutor executor = new SocketExecutor(serverIp, serverPort);
        switch (type) {
            case READER:
                new Reader(clientId, executor, numRequests).read();
                break;
            case WRITER:
                new Writer(clientId, executor, numRequests).write();
                break;
            default:
                break;
        }

    }
}
