package server;

import common.Message;
import common.Order;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerReceive implements Runnable {

    private Socket socket;
    private Server server;

    public ServerReceive(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Read stream
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Object o = in.readObject();

                // [Register] If User does not exist, add him to the list and send back, otherwise return null
                if (o instanceof User) {
                    for (User user : server.getUsers()) {
                        if (user.getName().equals(((User) o).getName())) o = null;
                    }
                    if (o != null) server.getUsers().add((User) o);
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(o);
                }
                // [Make Order]
                else if (o instanceof Order) {
                    server.addOrder((Order)o);
                }
                // If Message object is sent
                else if (o instanceof Message) {
                    Message message = (Message) o;
                    // [Get Dishes]
                    if (message.getMessage().equals("dish")) {
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(server.getDishes());
                    }
                    // [Cancel Order]
                    else if (message.getMessage().equals("cancel")) {
                        server.removeOrder(message.getOrder());
                    }
                    // [Get Orders]
                    else if(message.getMessage().equals("orders")) {
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(server.getOrders());
                    }
                    // [Login] If user with given username and password exists return him, otherwise return null
                    else if (message.getMessage().equals("login")) {
                        User user = null;
                        for (User u : server.getUsers()) {
                            if (u.getName().equals(message.getUsername()) &&
                                    u.getPassword().equals(message.getPassword())) {
                                user = u;
                            }
                        }
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(user);
                    }
                    // [Get Postcodes]
                    else if (message.getMessage().startsWith("postcodes")) {
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(server.getPostcodes());
                    }
                }
            } catch (SocketException e) {
                System.out.println("Client disconnected");
                break;
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