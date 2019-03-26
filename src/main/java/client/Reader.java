package client;

import common.Request;
import common.RequestType;
import common.Response;

import java.io.IOException;

public class Reader extends Client {
    private int numberOfAccesses;

    public Reader(int id, RequestExecutor executor, int numberOfAccesses) {
        super(id, executor);
        this.numberOfAccesses = numberOfAccesses;
        System.out.println("Client type: Reader\nClient id:"
                + id + "\n" + "rSeq  sSeq  oVal");
    }

    public void read() throws IOException, ClassNotFoundException {
        boolean isLast = false;
        while (numberOfAccesses != 0) {
            if (numberOfAccesses == 1) {
                isLast = true;
            }
            Request request = new Request(id, RequestType.READ, Integer.toString(id), isLast);
            executeRequest(request);
            try {
                Thread.sleep(random.nextInt(10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numberOfAccesses--;
        }
    }

    void log(Response response) {
        System.out.println(response.getRequestSequence()
                + " " + response.getServiceSequence()
                + " " + response.getBody());
    }
}
