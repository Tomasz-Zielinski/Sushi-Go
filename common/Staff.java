package common;

import server.Server;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Staff extends Model implements Runnable {

    private String name;
    private String status;
    private Server server;

    public Staff(String name, Server server) {
        this.name = name;
        this.status = "Idle";
        this.server = server;
    }

    private void makeDish() throws InterruptedException {
        try {
            for (Dish dish : server.getDishes()) {
                if (!dish.getLock().tryLock()) {
                    status = "Idle";
                    server.notifyUpdate();
                    TimeUnit.SECONDS.sleep(2);
                    continue;
                }
                dish.getLock().lock();
                status = "Checking if " + dish.getName() + " can be made";
                server.notifyUpdate();
                boolean canMake = true;
                TimeUnit.SECONDS.sleep(2);
                for (HashMap.Entry<Ingredient, Number> entry : dish.getRecipe().entrySet()) {
                    Ingredient key = entry.getKey();
                    Number value = entry.getValue();
                    status = "Checking if " + key.getName() + " is sufficient to make " + dish.getName();
                    server.notifyUpdate();
                    TimeUnit.SECONDS.sleep(1);
                    if ((Integer) key.getStock() < (Integer) value) {
                        canMake = false;
                        status = "Not enough " + key.getName() + " to make " + dish.getName() + "?";
                        server.notifyUpdate();
                        TimeUnit.SECONDS.sleep(1);
                        break;
                    }
                }
                if (canMake) {
                    dish.getRecipe().forEach((key, value) -> key.setStock((int) key.getStock() - (int) value));
                    int waitTime = (int) (Math.random() * 40 + 20);
                    for (int t = waitTime; t > 0; t--) {
                        status = "Making " + dish.getName() + " - " + t + "s left";
                        server.notifyUpdate();
                        TimeUnit.SECONDS.sleep(1);
                    }
                    dish.setStock((Integer) dish.getStock() + 1);
                }
                dish.getLock().unlock();
            }
        } catch(ConcurrentModificationException e){
            makeDish();
        }
    }

    @Override
    public String getName() {
        return name;
    }
    public String getStatus() { return status; }


    @Override
    public void run() {
        while(true) {
            try {
                makeDish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}