package client;

import common.Request;
import common.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class Client {
    protected int id;
    protected RequestExecutor executor;

    public Client(int id, RequestExecutor executor) {
        this.id = id;
        this.executor = executor;
    }

    abstract void log(Response response) throws IOException;

    protected void executeRequest(Request request) throws IOException, ClassNotFoundException {
        log(executor.executeRequest(request));
    }
}