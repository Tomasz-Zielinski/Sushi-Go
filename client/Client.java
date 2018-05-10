package client;

import common.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Client implements ClientInterface {

    private ClientWindow window;
    private Socket socket;
    private Comms comms;
    private User user;

    public Client(Socket socket) {
        this.socket = socket;
        this.comms = new Comms(socket);
    }

    public void assign(ClientWindow window) {
        this.window = window;
    }

    @Override
    public User register(String username, String password, String address, Postcode postcode) {
        return comms.registerNewUser(username, password, address, postcode);
    }

    @Override
    public User login(String username, String password) {
        return comms.login(username, password);
    }

    @Override
    public List<Postcode> getPostcodes() {
        return comms.getPostcodes();
    }

    @Override
    public List<Dish> getDishes() {
        return comms.getDishes();
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
        notifyUpdate();
    }

    @Override
    public Order checkoutBasket(User user) {
        ClientOrders clientOrders = new ClientOrders(this, user);
        Thread d = new Thread(clientOrders);
        d.start();
        return comms.makeOrder(user, getBasketCost(user));
    }


    @Override
    public void updateDishInBasket(User user, Dish dish, Number quantity) {
        user.getBasket().put(dish, quantity);
        notifyUpdate();
    }
    @Override
    public void clearBasket(User user) {
        user.getBasket().clear();
        notifyUpdate();
    }

    @Override
    public List<Order> getOrders(User user) {
        this.user = user;
        return comms.getOrders();
    }

    @Override
    public boolean isOrderComplete(Order order) {
        return order.isComplete();
    }

    @Override
    public String getOrderStatus(Order order) {
        return order.getStatus();
    }

    @Override
    public Number getOrderCost(Order order) {
        return order.getCost();
    }

    @Override
    public void cancelOrder(Order order) {
        comms.cancelOrder(order);
        notifyUpdate();
    }

    @Override
    public void addUpdateListener(UpdateListener listener) { }

    @Override
    public void notifyUpdate() {
        window.updated(new UpdateEvent());
    }
}
