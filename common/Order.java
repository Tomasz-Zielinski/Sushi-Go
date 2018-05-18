package common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Order extends Model implements Serializable {

    private String name, status;
    private Number distance, cost;
    private boolean complete;
    private Map<Dish, Number> basket;
    private Lock lock = new ReentrantLock();

    public Order(String name, Number cost, Number distance) {
        this.name = name;
        this.status = "Unfinished";
        this.distance = distance;
        this.cost = cost;
        this.complete = false;
        this.basket = new HashMap<>();
    }

    public Order(String name, Number cost, Number distance, Map<Dish, Number> basket) {
        this.name = name;
        this.status = "Unfinished";
        this.distance = distance;
        this.complete = false;
        this.cost = cost;
        this.basket = basket;
    }

    @Override
    public String getName() {
        return name;
    }

    public Number getDistance() {
        return distance;
    }

    public boolean isComplete() {
        return complete;
    }

    public String getStatus() {
        return status;
    }

    public Number getCost() {
        return cost;
    }

    public Map<Dish, Number> getBasket() {
        return basket;
    }

    public Lock getLock() {
        return lock;
    }

    public void setComplete() {
        this.complete = !complete;
        notifyUpdate("complete", this.complete, complete);
    }

    public void setStatus(String status) {
        notifyUpdate("status", this.status, status);
        this.status = status;
    }

}
