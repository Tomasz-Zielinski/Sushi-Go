package client;

import common.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Client implements ClientInterface {

    private ClientWindow window;
    private Socket socket;
    private Comms comms;

    public Client(Socket socket) {
        this.socket = socket;
        this.comms = new Comms(socket);
    }

    public void assign(ClientWindow window) {
        this.window = window;
    }

    @Override
    public User register(String username, String password, String address, Postcode postcode) {
        User user = comms.registerNewUser(username, password, address, postcode);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = comms.login(username, password);
        return user;
    }

    @Override
    public List<Postcode> getPostcodes() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(new Packet("postcodes"));
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ArrayList<Postcode> o = (ArrayList<Postcode>) in.readObject();
            return o;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dish> getDishes() {
        ArrayList<Dish> dishes = new ArrayList<>();
        Packet packet = new Packet("dish");
        if (socket.isBound()) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            dishes = (ArrayList<Dish>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    @Override
    public String getDishDescription(Dish dish) {
        return dish.getDescription();
    }

    @Override
    public Number getDishPrice(Dish dish) {
        return dish.getPrice();
    }

    @Override
    public Map<Dish, Number> getBasket(User user) {
        return user.getBasket();
    }

    @Override
    public Number getBasketCost(User user) {
        ArrayList<Integer> prices = new ArrayList<>();
        Integer price = 0;
        user.getBasket().forEach((key, value) -> prices.add((int) key.getPrice() * (int)value));
        for (Integer integer : prices) {
            price += integer;
        }
        return price;
    }

    @Override
    public void addDishToBasket(User user, Dish dish, Number quantity) {
        user.addToBasket(dish, quantity);
    }

    @Override
    public Order checkoutBasket(User user) {
        return new Order("test",1,1);
    }


    @Override
    public void updateDishInBasket(User user, Dish dish, Number quantity) {

    }
    @Override
    public void clearBasket(User user) {

    }

    @Override
    public List<Order> getOrders(User user) {
        return new ArrayList<Order>();
    }

    @Override
    public boolean isOrderComplete(Order order) {
        return false;
    }

    @Override
    public String getOrderStatus(Order order) {
        return "prepping";
    }

    @Override
    public Number getOrderCost(Order order) {
        return 3;
    }

    @Override
    public void cancelOrder(Order order) {

    }

    @Override
    public void addUpdateListener(UpdateListener listener) {

    }

    @Override
    public void notifyUpdate() {
        window.updated(new UpdateEvent());
    }
}
