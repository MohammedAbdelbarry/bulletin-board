package client;

import common.Request;
import common.RequestType;
import common.Response;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Writer extends SocketClient {

    public Writer(int id, String serverIP, int port) throws IOException {
        super(id, serverIP, port);

        BufferedWriter out = new BufferedWriter(
                new FileWriter("log" + id));
        out.write("Client type: Writer\nClient type:"
                + id + "\n" + "rSeq  sSeq\n");
        out.close();
    }

    public void write() throws IOException, ClassNotFoundException {
        Request request = new Request(RequestType.WRITE, Integer.toString(id));
        executeRequest(request);
    }

    void log(Response response) throws IOException {
        BufferedWriter out = new BufferedWriter(
             new FileWriter("log" + id, true));
        out.write(response.getRequestSequence() + " " + response.getServiceSequence());
        out.close();
    }

}
