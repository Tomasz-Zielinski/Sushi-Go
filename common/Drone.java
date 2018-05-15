package common;

import server.Server;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;

public class Drone extends Model implements Runnable {

    private String name;
    private Number speed;
    private String status;
    private Server server;
    public Thread thread;

    public Drone(Number speed, Server server) {
        this.name = "Drone";
        this.speed = speed;
        this.status = "Charging Battery";
        this.server = server;
        this.thread = null;
    }

    @Override
    public String getName() {
        return name;
    }

    public Number getSpeed() {
        return speed;
    }

    public String getStatus() {
        return status;
    }

    private void restock() {
        try {
            for (Ingredient ingredient : server.getIngredients()) {
                Lock lock = ingredient.getLock();
//                if(!lock.tryLock()) { continue; }
                try {
                    lock.lock();
                    notifyUpdate("Checking " + ingredient.getName() + " level");
                    Thread.sleep(2000);
                    if ((int) ingredient.getStock() < (int) ingredient.getRestockThreshold() * 1000) {
                        int waitTime = (int) ingredient.getSupplier().getDistance();
                        for (int t = waitTime; t >= 0; t--) {
                            Thread.sleep(1000);
                            notifyUpdate("Restocking " + ingredient.getName() + " - " + t + "s left");
                        }
                        ingredient.setStock((int) ingredient.getStock() + 100);
                    }
                    notifyUpdate("Idle");
                    lock.unlock();
                } catch (InterruptedException e) {
                    lock.unlock();
                    Thread.currentThread().interrupt();
                } catch (ConcurrentModificationException e) {
                    lock.unlock();
                    break;
                }
            }
        } catch (ConcurrentModificationException e) {
            deliver();
        }
    }

    private void deliver() {
        try {
            if(server.getOrders().isEmpty()) return;
            for (Order order : server.getOrders()) {
                Lock lock = order.getLock();
//                if(!lock.tryLock()) { return; }
                try {
                    for(Map.Entry<Dish, Number> entry : order.getBasket().entrySet()) {
                        Dish key = entry.getKey();
                        Number value = entry.getValue();
                        for (Dish dish : server.getDishes()) {
                            if(dish.equals(key)) {
                                if((int)dish.getStock() < (int)value) {
                                    notifyUpdate(order.getName() + " order not yet complete");
                                    Thread.sleep(1000);
                                    notifyUpdate("Idle");
                                    return;
                                }
                            }
                        }
                    }
                    lock.lock();
                    notifyUpdate("Checking if I can deliver order for " + order.getName());
                    Thread.sleep(1000);
                    int waitTime = (int) (Math.random() * 10) + (int) order.getDistance() + (int) speed;
                    for (int t = waitTime; t > 0; t--) {
                        order.setStatus("On the way - " + t + "s left");
                        notifyUpdate("Delivering " + order.getName() + " order - " + t + "s left");
                        Thread.sleep(1000);
                    }
                    order.setComplete();
                    notifyUpdate(order.getName() + " order finished");
                    server.removeOrder(order);
                    lock.unlock();
                    Thread.sleep(1000);
                    notifyUpdate("Idle");
                    break;
                } catch (InterruptedException e) {
                    lock.unlock();
                    Thread.currentThread().interrupt();
                } catch (ConcurrentModificationException | IllegalMonitorStateException e) {
                    lock.unlock();
                    break;
                }
            }
        } catch(ConcurrentModificationException | NoSuchElementException e) {
            restock();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!Thread.currentThread().isInterrupted()) {
                deliver();
                restock();
            } else {
                break;
            }
        }
    }

    private void notifyUpdate(String status) {
        this.status = status;
        server.notifyUpdate();
    }

}
