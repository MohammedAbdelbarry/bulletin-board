package server;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        int serverPort = Integer.parseInt(args[0]);
        int numRequests = Integer.parseInt(args[1]);

        Server server = null;
        try {
            server = new Server(serverPort, numRequests);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (server != null) {
            server.start();
        }
    }
}
