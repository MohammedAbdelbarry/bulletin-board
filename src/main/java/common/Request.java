package common;

import java.io.Serializable;

public class Request implements Serializable {
    private int clientId;
    private RequestType type;
    private String body;
    private boolean isLast;

    public Request(int clientId, RequestType type, String body, boolean isLast) {
        this.clientId = clientId;
        this.type = type;
        this.body = body;
        this.isLast = isLast;
    }

    public int getClientId() {
        return clientId;
    }

    public RequestType getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    public boolean isLast() {
        return isLast;
    }
}
