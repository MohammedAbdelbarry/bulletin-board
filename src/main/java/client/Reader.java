package client;

import common.Request;
import common.RequestType;
import common.Response;

import java.io.*;

public class Reader extends SocketClient{

    public Reader(int id, String serverIP, int port) throws IOException {
        super(id, serverIP, port);
        BufferedWriter out = new BufferedWriter(
                new FileWriter("log" + id));
        out.write("Client type: Reader\nClient type:"
                + id + "\n" + "rSeq  sSeq  oVal\n");
        out.close();
    }

    public void read() throws IOException, ClassNotFoundException {
        Request request = new Request(RequestType.READ, Integer.toString(id));
        executeRequest(request);
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
