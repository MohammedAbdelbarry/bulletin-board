package common;

import java.io.Serializable;

public class Response implements Serializable {
    private int serviceSequence;
    private int requestSequence;
    private String body;

    public Response(int serviceSequence, int requestSequence, String body) {
        this.serviceSequence = serviceSequence;
        this.requestSequence = requestSequence;
        this.body = body;
    }

    public Response(int serviceSequence, int requestSequence) {
        this(serviceSequence, requestSequence, "");
    }

    public int getServiceSequence(){
        return serviceSequence;
    }
    public int getRequestSequence(){
        return requestSequence;
    }

    public String getBody(){
        return body;
    }

    public void setBody(String body){
        this.body = body;
    }
}
