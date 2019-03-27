package server;

import java.io.IOException;
import java.util.Arrays;

public class ServerMain {

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        int serverPort = Integer.parseInt(args[0]);
        int numRequests = Integer.parseInt(args[1]);

        System.out.println(Arrays.toString(args));

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
