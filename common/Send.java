package server;

import common.Packet;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Send implements Runnable {

    private Socket server;

    public Send(Socket server) {
        this.server = server;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Scanner s = new Scanner(System.in);
                Packet packet = new Packet(s.next());
                ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
                out.writeObject(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}