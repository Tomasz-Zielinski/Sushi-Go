package client;

import common.User;

import java.io.*;
import java.net.Socket;

public class ClientReceive implements Runnable {

    private Socket socket;

    public ClientReceive(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true) {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                User u = (User) in.readObject();
                System.out.println(u.getName());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}