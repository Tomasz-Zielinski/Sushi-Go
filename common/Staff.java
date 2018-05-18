package common;

import server.Server;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class Staff extends Model implements Runnable {

    private String name, status;
    private Server server;

    public Staff(String name, Server server) {
        this.name = name;
        this.status = "Nothing to do";
        this.server = server;
    }

    private void makeDish() {
        try {
            outerloop:
            for (Dish dish : server.getDishes()) {

                // Check if we need more dishes of this type
                if((int) dish.getStock() >= (int) dish.getRestockThreshold()) return;

                // Acquire lock
                Lock lock = dish.getLock();
                lock.lock();
                notifyUpdate("Checking if " + dish.getName() + " can be made");
                Thread.sleep(1000);

                // Check if there are enough ingredients to make dish
                for (HashMap.Entry<Ingredient, Number> recipeItem : dish.getRecipe().entrySet()) {
                    if ((int) recipeItem.getKey().getStock() < (int) recipeItem.getValue()) {
                        notifyUpdate("Not enough " + recipeItem.getKey().getName());
                        Thread.sleep(1000);
                        notifyUpdate("Idle");
                        continue outerloop;
                    }
                }

                // Subtract ingredients necessary to make the dish
                dish.getRecipe().forEach((key, value) -> key.setStock((int) key.getStock() - (int) value));


                // Calculate wait time, wait until dish is made
                for (int t = (int) (Math.random() * 40 + 20); t > 0; t--) {
                    notifyUpdate("Making " + dish.getName() + " - " + t + "s left");
                    Thread.sleep(1000);
                }

                // Dish successfully made, add one to the stock, unlock object
                dish.setStock((int) dish.getStock() + 1);
                lock.unlock();
            }
        } catch (ConcurrentModificationException e) {
            // Restart on modification
            makeDish();
        } catch (InterruptedException e) {
            // Interrupt on deletion
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    // Helper function to make wait time be displayed live
    public void notifyUpdate(String status) {
        this.status = status;
        server.notifyUpdate();
    }

    @Override
    public void run() {
        while (true) {
            if (!Thread.currentThread().isInterrupted()) {
                makeDish();
            } else {
                break;
            }
        }
    }
}