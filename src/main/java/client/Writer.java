package client;

import common.Request;
import common.RequestType;
import common.Response;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer extends Client {
    private int numAccesses;

    public Writer(int id, String serverIP, int port, int numAccesses) throws IOException {
        super(id, serverIP, port);
        this.numAccesses = numAccesses;
        BufferedWriter out = new BufferedWriter(
                new FileWriter("log" + id));
        out.write("Client type: Writer\nClient type:"
                + id + "\n" + "rSeq  sSeq\n");
        out.close();
    }

    public void write() throws IOException, ClassNotFoundException {
        boolean isLast = false;
        while (numAccesses != 0) {
            if (numAccesses == 1) {
                isLast = true;
            }
            Request request = new Request(id, RequestType.WRITE, Integer.toString(id), isLast);
            executeRequest(request);
            numAccesses--;
        }
    }

    void log(Response response) throws IOException {
        BufferedWriter out = new BufferedWriter(
             new FileWriter("log" + id, true));
        out.write(response.getRequestSequence() + " " + response.getServiceSequence());
        out.close();
    }
}
