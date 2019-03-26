package client;

import common.ClientType;
import server.Server;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws IOException {
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
                Client reader = new Reader(clientId, executor, numRequests);
                break;
            case WRITER:
                Client writer = new Writer(clientId, executor, numRequests);
                break;
            default:
                break;
        }

    }
}
