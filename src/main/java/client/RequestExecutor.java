package client;

import common.Request;
import common.Response;

import java.io.IOException;


public interface RequestExecutor {
    Response executeRequest(Request request) throws IOException, ClassNotFoundException;
}
