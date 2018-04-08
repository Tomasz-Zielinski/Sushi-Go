package server;

import java.io.*;
import java.net.Socket;

import common.Drone;
import common.Packet;
import common.User;

public class ServerReceive implements Runnable {

    private Socket socket;
    private Server server;

    public ServerReceive(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        while(true) {
            try {
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Object u = in.readObject();
                if(u instanceof User) {
                    server.userList.add((User) u);
                    server.notifyUpdate();
                } else if(u instanceof Packet) {
                    if(((Packet) u).getMessage().equals("dish")) {
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(server.getDishes());
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}