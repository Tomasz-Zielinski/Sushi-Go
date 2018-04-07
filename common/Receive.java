package server;

import common.Packet;

import java.io.*;
import java.net.Socket;

public class Receive implements Runnable {

    private Socket socket;

    public Receive(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true) {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Packet o = (Packet) in.readObject();
                System.out.println(o.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}