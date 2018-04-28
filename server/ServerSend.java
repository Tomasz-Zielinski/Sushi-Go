package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import common.Packet;

public class ServerSend implements Runnable {

    private Socket socket;

    public ServerSend(Socket socket) {
        this.socket = socket;
    }

    public synchronized void setSocket(Socket socket) {
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