package client;

import common.ClientType;
import server.RemoteHandler;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;



public class RmiClientMain {
    public static void main(String[] args) {
        if (args.length < 6) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        ClientType type = ClientType.valueOf(args[0]);
        String serverIp = args[1];
        int numRequests = Integer.parseInt(args[3]);
        int clientId = Integer.parseInt(args[4]);
        int rmiPort = Integer.parseInt(args[5]);

        System.out.println(Arrays.toString(args));

        try {
            Registry registry = LocateRegistry.getRegistry(serverIp, rmiPort);
            RemoteHandler stub = (RemoteHandler) registry.lookup("bulletin");

            RequestExecutor executor = new RmiExecutor(stub);
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
        } catch (IOException | ClassNotFoundException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
