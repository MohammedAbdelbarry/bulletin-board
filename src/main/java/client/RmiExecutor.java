package client;

import common.Request;
import common.Response;
import server.RemoteHandler;

import java.rmi.RemoteException;

public class RmiExecutor implements RequestExecutor {
    private RemoteHandler handler;

    public RmiExecutor(RemoteHandler handler) {
        this.handler = handler;
    }


    @Override
    public Response executeRequest(Request request) throws RemoteException {
        switch (request.getType()) {
            case READ:
                return handler.read(request.getClientId());
            case WRITE:
                return handler.write(request.getClientId(), request.getBody());
            default:
                return null;
        }
    }
}
