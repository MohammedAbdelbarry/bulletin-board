package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServerMain {
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        int numRequests = Integer.parseInt(args[1]);
        int rmiPort = Integer.parseInt(args[2]);

        FileHandler handler = new FileHandler();
        try {
            RemoteHandler stub = (RemoteHandler) UnicastRemoteObject.exportObject(handler, rmiPort);
            Registry reg = LocateRegistry.createRegistry(rmiPort);
            reg.bind("bulletin", stub);
            while (handler.getNumRequests() < numRequests) {
                Thread.sleep(1000);
            }
        } catch (RemoteException | AlreadyBoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
