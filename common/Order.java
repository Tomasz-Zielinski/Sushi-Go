package common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Order extends Model implements Serializable {

    private String name;
    private Number distance;
    private boolean complete;
    private String status;
    private Number cost;
    private HashMap<Dish, Number> basket;
    private Lock lock = new ReentrantLock();

    public Order(String name,  Number cost, Number distance) {
        this.name = name;
        this.distance = distance;
        this.complete = false;
        this.status = "Unfinished";
        this.cost = cost;
    }

    public Order(String name, Number cost, Number distance, HashMap<Dish, Number> basket) {
        this.name = name;
        this.distance = distance;
        this.complete = false;
        this.status = "Unfinished";
        this.cost = cost;
        this.basket = basket;
    }

    @Override
    public String getName() {
        return name;
    }
    public Number getDistance() { return distance; }
    public boolean isComplete() { return complete; }
    public String getStatus() { return status; }
    public Number getCost() { return cost; }
    public HashMap<Dish, Number> getBasket() { return basket; }
    public Lock getLock() {
        return lock;
    }
    public void setComplete() {this.complete = !complete;}

}
