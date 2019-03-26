package server;

import common.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteHandler extends Remote {
    Response read(int rid) throws RemoteException;
    Response write(int wid, String data) throws RemoteException;
}
