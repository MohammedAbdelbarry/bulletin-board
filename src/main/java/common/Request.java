package common;

import java.io.Serializable;

public class Request implements Serializable {
    private RequestType type;
    private String body;

    public Request(RequestType type, String body) {
        this.type = type;
        this.body = body;
    }
}
