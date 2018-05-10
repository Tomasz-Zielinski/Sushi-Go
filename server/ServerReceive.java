package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import common.Drone;
import common.Order;
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
                Object o = in.readObject();
                if(o instanceof User) {
                    if (!server.userList.contains(o)) {
                        server.userList.add((User) o);
                        server.notifyUpdate();
                    } else {
                        System.out.println("User already exist");
                    }
                } else if(o instanceof Order) {
                    server.getOrders().add((Order) o);
                } else if(o instanceof Packet) {
                    if(((Packet) o).getMessage().equals("dish")) {
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(server.getDishes());
                    } else if(((Packet) o).getMessage().equals("orders")) {
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(server.getOrders());
                    } else if(((Packet) o).getMessage().equals("cancel")) {
                        server.getOrders().remove(((Packet) o).getOrder());
                    } else if(((Packet) o).getMessage().startsWith("login")) {
                        String arr[] = ((Packet) o).getMessage().split(" ");
                        System.out.println("Somebody wants to login" + arr[1] + arr[2]);
                        for (User user : server.getUsers()) {
                            if(user.getName().equals(arr[1]) && user.getPassword().equals(arr[2])){
                                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                                out.writeObject(user);
                            } else {
                                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                                out.writeObject(new Packet("Wrong"));
                            }
                        }
                    } else if(((Packet) o).getMessage().startsWith("postcodes")) {
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(server.getPostcodes());
                        System.out.println(server.getPostcodes());
                    }
                }
            } catch (SocketException e) {
                System.out.println("Client disconnected");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public synchronized void setSocket(Socket socket) {
        this.socket = socket;
    }
}