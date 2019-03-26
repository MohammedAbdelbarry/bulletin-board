package client;

import common.Request;
import common.RequestType;
import common.Response;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Reader extends Client {
    private int numberOfAccesses;

    public Reader(int id, RequestExecutor executor, int numberOfAccesses) throws IOException {
        super(id, executor);
        this.numberOfAccesses = numberOfAccesses;
        BufferedWriter out = new BufferedWriter(
                new FileWriter("log" + id));
        out.write("Client type: Reader\nClient type:"
                + id + "\n" + "rSeq  sSeq  oVal\n");
        out.close();
    }

    public void read() throws IOException, ClassNotFoundException {
        boolean isLast = false;
        while (numberOfAccesses != 0) {
            if (numberOfAccesses == 1) {
                isLast = true;
            }
            Request request = new Request(id, RequestType.READ, Integer.toString(id), isLast);
            executeRequest(request);
            numberOfAccesses--;
        }
    }

    void log(Response response) throws IOException {
        BufferedWriter out = new BufferedWriter(
                new FileWriter("log" + id, true));
        out.write(response.getRequestSequence()
                + " " + response.getServiceSequence()
                + " " + response.getBody() + "\n");
        out.close();
    }
}
