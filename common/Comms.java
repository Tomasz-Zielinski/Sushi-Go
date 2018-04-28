package common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        if(username.equals("") || password.equals("")) {
            return new User("", "", "", new Postcode("", 0));
        }
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Packet("login " + username + " " + password));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object o = in.readObject();
            System.out.println(o);
            if(o instanceof User) {
                return (User) o;
            } else {
                return new User("", "", "", new Postcode("", 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}