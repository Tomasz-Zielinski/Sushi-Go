package common;

import java.io.IOException;
import java.io.InvalidClassException;
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
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new User(username, password, address, postcode));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            User user = (User) in.readObject();
            if(user.getPostcode() == null) return null;
            return user;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User login(String username, String password) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Message("login", username, password));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return (User) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public List<Postcode> getPostcodes() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.reset();
            out.writeObject(new Message("postcodes"));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return (List<Postcode>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Dish> getDishes() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Message("dish"));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return (List<Dish>) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order makeOrder(User user, Number cost) {
        try {
            if(user.getPostcode() == null) user.setPostcode(getPostcodes().get(0));
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
            out.writeObject(new Message("orders"));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            return (List<Order>) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Socket write error");
        }
        return null;
    }

    public void cancelOrder(Order order) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Message("cancel", order));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}