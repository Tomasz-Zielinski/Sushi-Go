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
        this.status = "Preparing to work";
        this.server = server;
    }

    private void makeDish() {
        try {
            for (Dish dish : server.getDishes()) {
                Lock lock = dish.getLock();
                lock.lock();
                notifyUpdate("Checking if " + dish.getName() + " can be made");
                Thread.sleep(1000);
                for (HashMap.Entry<Ingredient, Number> entry : dish.getRecipe().entrySet()) {
                    Ingredient ingredient = entry.getKey();
                    Number quantity = entry.getValue();
                    notifyUpdate("Checking if " + ingredient.getName() + " is sufficient to make " + dish.getName());
                    Thread.sleep(1000);
                    if ((int) ingredient.getStock() < (int) quantity) {
                        notifyUpdate("Not enough " + ingredient.getName() + " to make " + dish.getName());
                        Thread.sleep(1000);
                        return;
                    }
                }
                dish.getRecipe().forEach((key, value) -> key.setStock((int) key.getStock() - (int) value));
                int waitTime = (int) (Math.random() * 40 + 20);
                for (int t = waitTime; t > 0; t--) {
                    notifyUpdate("Making " + dish.getName() + " - " + t + "s left");
                    Thread.sleep(1000);
                }
                dish.setStock((int) dish.getStock() + 1);
                lock.unlock();
            }
        } catch (ConcurrentModificationException e) {
            makeDish();
        } catch (InterruptedException e) {
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