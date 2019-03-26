package client;

import common.Request;
import common.Response;

import java.io.IOException;
import java.util.Random;

public abstract class Client {
    protected int id;
    protected RequestExecutor executor;
    protected Random random;

    protected static final int SLEEP_DURATION = 10000;

    public Client(int id, RequestExecutor executor) {
        this.id = id;
        this.executor = executor;
        this.random = new Random();
    }

    abstract void log(Response response) throws IOException;

    protected void executeRequest(Request request) throws IOException, ClassNotFoundException {
        log(executor.executeRequest(request));
    }
}