package client;

import common.Request;
import common.RequestType;
import common.Response;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer extends Client {
    private int numberOfAccesses;

    public Writer(int id, String serverIP, int port, int numberOfAccesses) throws IOException {
        super(id, serverIP, port);
        this.numberOfAccesses = numberOfAccesses;
        BufferedWriter out = new BufferedWriter(
                new FileWriter("log" + id));
        out.write("Client type: Writer\nClient type:"
                + id + "\n" + "rSeq  sSeq\n");
        out.close();
    }

    public void write() throws IOException, ClassNotFoundException {
        boolean isLast = false;
        while (numberOfAccesses != 0) {
            if (numberOfAccesses == 1) {
                isLast = true;
            }
            Request request = new Request(id, RequestType.WRITE, Integer.toString(id), isLast);
            executeRequest(request);
            numberOfAccesses--;
        }
    }

    void log(Response response) throws IOException {
        BufferedWriter out = new BufferedWriter(
             new FileWriter("log" + id, true));
        out.write(response.getRequestSequence() + " " + response.getServiceSequence());
        out.close();
    }
}
