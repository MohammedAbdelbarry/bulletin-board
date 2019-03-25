package server;

import common.Response;

import java.rmi.Remote;

public interface RemoteHandler extends Remote {
    Response read(int rid);
    Response write(int wid, String data);
}
