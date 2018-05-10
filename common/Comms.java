package common;

import client.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Comms {

    private Socket socket;

    public Comms(Socket socket) {
        this.socket = socket;
    }

    public User registerNewUser(String username, String password, String address, Postcode postcode) {
        User user = new User(username, password, address, postcode);
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User login(String username, String password) {
        if(username.equals("") || password.equals("")) return null;
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Packet("login " + username + " " + password));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object user = in.readObject();
            if(user instanceof User) return (User) user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Postcode> getPostcodes() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Packet("postcodes"));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            List<Postcode> postcodes = (List<Postcode>) in.readObject();
            postcodes.forEach(e -> System.out.println(e.getCode()));
            return postcodes;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Dish> getDishes() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Packet("dish"));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            List<Dish> dishes = (List<Dish>) in.readObject();
            return dishes;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order makeOrder(User user, Number cost) {
        try {
            Order order = new Order(user.getName(), cost, user.getPostcode().getDistance(), user.getBasket());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(order);
            return order;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> getOrders() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Packet("orders"));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            List<Order> orders = (List<Order>) in.readObject();
            return orders;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cancelOrder(Order order) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Packet("cancel", order));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}