package client;

import common.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend implements Runnable {

    private Socket socket;

    public ClientSend(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Scanner s = new Scanner(System.in);
                Packet packet = new Packet(s.next());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}