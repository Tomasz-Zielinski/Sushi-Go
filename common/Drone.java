package common;

import server.Server;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;

public class Drone extends Model implements Runnable {

    private String name, status;
    private Number speed;
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

                // I tried implementing trylock mechanism to skip if other drone
                // is currently restocking but weird loops occurred

                Lock lock = ingredient.getLock();
//                if(!lock.tryLock()) { continue; }
                try {

                    //Lock object
                    lock.lock();
                    notifyUpdate("Checking " + ingredient.getName() + " level");
                    Thread.sleep(2000);

                    // If stock is below threshold, restock
                    if ((int) ingredient.getStock() < (int) ingredient.getRestockThreshold()) {

                        // Calculate wait time, execute, update every second
                        int waitTime = (int) (Math.random()*10) + (int) ingredient.getSupplier().getDistance() + (int) speed;
                        for (int t = waitTime; t >= 0; t--) {
                            Thread.sleep(1000);
                            notifyUpdate("Restocking " + ingredient.getName() + " - " + t + "s left");
                        }

                        // Add restocking amount to the stock
                        ingredient.setStock((int) ingredient.getStock() + (int) ingredient.getRestockAmount());
                    }

                    // Set status to idle, unlock object
                    notifyUpdate("Idle");
                    lock.unlock();

                } catch (InterruptedException e) {
                    // Unlock object and interrupt thread on deletion
                    lock.unlock();
                    Thread.currentThread().interrupt();
                } catch (ConcurrentModificationException e) {
                    // Unlock object on modification during restocking
                    lock.unlock();
                    break;
                }
            }
        } catch (ConcurrentModificationException e) {
            // Restart on modification during checking
            deliver();
        }
    }

    private void deliver() {
        try {
            if (server.getOrders().isEmpty()) return;
            for (Order order : server.getOrders()) {
                Lock lock = order.getLock();
                try {
                    // Check if there are enough dishes in stock to make order
                    for (Map.Entry<Dish, Number> recipeItem : order.getBasket().entrySet()) {
                        for (Dish dish : server.getDishes()) {
                            if (dish.equals(recipeItem.getKey())) {
                                if ((int) dish.getStock() < (int) recipeItem.getValue()) {
                                    notifyUpdate(order.getName() + " order not yet complete");
                                    Thread.sleep(1000);
                                    notifyUpdate("Idle");
                                    return;
                                }
                            }
                        }
                    }

                    // Acquire lock
                    lock.lock();
                    notifyUpdate("Checking if I can deliver order for " + order.getName());
                    Thread.sleep(1000);

                    // Calculate wait time, execute, update status every second
                    int waitTime = (int) (Math.random()*10) + (int) order.getDistance() + (int) speed;
                    for (int t = waitTime ; t > 0 ; t--) {
                        order.setStatus("On the way - " + t + "s left");
                        notifyUpdate("Delivering " + order.getName() + " order - " + t + "s left");
                        Thread.sleep(1000);
                    }

                    // Update order status, remove it, unlock object
                    order.setComplete();
                    notifyUpdate(order.getName() + " order finished");
                    server.removeOrder(order);
                    lock.unlock();
                    Thread.sleep(1000);
                    notifyUpdate("Idle");
                } catch (InterruptedException e) {
                    // Unlock object and interrupt thread on deletion
                    lock.unlock();
                    Thread.currentThread().interrupt();
                } catch (ConcurrentModificationException | IllegalMonitorStateException e) {
                    // Unlock object on modification during restocking
                    lock.unlock();
                    break;
                }
            }
        } catch (ConcurrentModificationException | NoSuchElementException e) {
            // Restart on modification during checking
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

    // Helper function to keep track of updates
    private void notifyUpdate(String status) {
        this.status = status;
        server.notifyUpdate();
    }

}
