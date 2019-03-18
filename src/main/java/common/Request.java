package common;

import java.io.Serializable;

public class Request implements Serializable {
    private int clientId;
    private RequestType type;
    private String data;
    private boolean isLast;

    public Request(int clientId, RequestType type, String data, boolean isLast) {
        this.clientId = clientId;
        this.type = type;
        this.data = data;
        this.isLast = isLast;
    }

    public int getClientId() {
        return clientId;
    }

    public RequestType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public boolean isLast() {
        return isLast;
    }
}
