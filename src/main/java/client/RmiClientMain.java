package client;

import common.ClientType;
import server.RemoteHandler;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClientMain {
    public static void main(String[] args) throws IOException, NotBoundException {
        if (args.length < 6) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        ClientType type = ClientType.valueOf(args[0]);
        String serverIp = args[1];
        int numRequests = Integer.parseInt(args[3]);
        int clientId = Integer.parseInt(args[4]);
        int rmiPort = Integer.parseInt(args[5]);

        Registry registry = LocateRegistry.getRegistry(serverIp, rmiPort);
        RemoteHandler stub = (RemoteHandler) registry.lookup("Bulleting Board");

        RequestExecutor executor = new RmiExecutor(stub);
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
