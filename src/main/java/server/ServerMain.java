package server;

public class ServerMain {

    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        int serverPort = Integer.parseInt(args[0]);
        int numReaders = Integer.parseInt(args[1]);
        int numWriters = Integer.parseInt(args[2]);

        Server server = new Server(serverPort, numReaders, numWriters);

        server.start();
    }
}
