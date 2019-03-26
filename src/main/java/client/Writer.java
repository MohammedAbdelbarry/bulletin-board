package client;

import common.Request;
import common.RequestType;
import common.Response;

import java.io.IOException;

public class Writer extends Client {
    private int numberOfAccesses;

    public Writer(int id, RequestExecutor executor, int numberOfAccesses) {
        super(id, executor);
        try {
            Thread.sleep(random.nextInt(SLEEP_DURATION));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.numberOfAccesses = numberOfAccesses;
        System.out.println("Client type: Writer\nClient id:"
                + id + "\n" + "rSeq  sSeq");
    }

    public void write() throws IOException, ClassNotFoundException {
        boolean isLast = false;
        while (numberOfAccesses != 0) {
            if (numberOfAccesses == 1) {
                isLast = true;
            }
            Request request = new Request(id, RequestType.WRITE, Integer.toString(id), isLast);
            executeRequest(request);
            try {
                Thread.sleep(random.nextInt(SLEEP_DURATION));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numberOfAccesses--;
        }
    }

    void log(Response response) {
        System.out.println(response.getRequestSequence() + " " + response.getServiceSequence());
    }
}
