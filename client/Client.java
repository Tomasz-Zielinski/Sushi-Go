package client;

import common.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client implements ClientInterface {

    @Override
    public User register(String username, String password, String address, Postcode postcode) {
        return new User(username, password, address, postcode);
    }

    @Override
    public User login(String username, String password) {
        return new User("kek", "kek", "bryk", new Postcode("XD", 1));
    }

    @Override
    public List<Postcode> getPostcodes() {
        return new ArrayList<Postcode>() { {add(new Postcode("08F", 1));}};
    }

    @Override
    public List<Dish> getDishes() {
        return new ArrayList<Dish>(){{

        }};
    }

    @Override
    public String getDishDescription(Dish dish) {
        return dish.getDescription();
    }

    @Override
    public Number getDishPrice(Dish dish) {
        return 15;
    }

    @Override
    public Map<Dish, Number> getBasket(User user) {
        return new HashMap<Dish, Number>();
    }

    @Override
    public Number getBasketCost(User user) {
        return 5;
    }

    @Override
    public void addDishToBasket(User user, Dish dish, Number quantity) {

    }

    @Override
    public void updateDishInBasket(User user, Dish dish, Number quantity) {

    }

    @Override
    public Order checkoutBasket(User user) {
        return new Order("test",1,1);
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

    }
}
