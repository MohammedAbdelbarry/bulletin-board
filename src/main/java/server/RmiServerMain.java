package server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class RmiServerMain {
    public static void main(String[] args) {
        if (args.length < 4) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }

        int numRequests = Integer.parseInt(args[1]);
        numRequests *= Integer.parseInt(args[2]);
        int rmiPort = Integer.parseInt(args[3]);

        System.out.println(Arrays.toString(args));

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        FileHandler handler = new FileHandler();
        try {
            RemoteHandler stub = (RemoteHandler) UnicastRemoteObject.exportObject(handler, rmiPort);
            Registry reg = LocateRegistry.createRegistry(rmiPort);
            reg.bind("bulletin", stub);
            while (handler.getNumRequests() < numRequests) {
                Thread.sleep(1000);
            }
            handler.log();
            reg.unbind("bulletin");
            UnicastRemoteObject.unexportObject(handler, true);
            System.exit(0);
        } catch (RemoteException | AlreadyBoundException | InterruptedException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
