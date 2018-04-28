package common;

import server.Server;

import java.util.ConcurrentModificationException;
import java.util.concurrent.TimeUnit;

public class Drone extends Model implements Runnable {

    private String name;
    private Number speed;
    private String status;
    private Server server;

    public Drone(Number speed, Server server) {
        this.name = "Drone";
        this.speed = speed;
        this.status = "Idle";
        this.server = server;
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

    private void restock() throws InterruptedException {
        try {
            for (Ingredient i : server.getIngredients()) {
                if(!i.getLock().tryLock()) {
                    status = "Another drone is checking " + i.getName() + ", moving on";
                    server.notifyUpdate();
                    TimeUnit.SECONDS.sleep(2);
                    continue;
                }
                i.getLock().lock();
                status = "Checking " + i.getName() + " level";
                server.notifyUpdate();
                TimeUnit.SECONDS.sleep(2);
                if ((int) i.getStock() < (int) i.getRestockThreshold()*1000) {
                    int waitTime = (int) speed + (int) i.getSupplier().getDistance();
                    status = "Need more " + i.getName();
                    server.notifyUpdate();
                    TimeUnit.SECONDS.sleep(1);
                    for (int t = waitTime; t > 0; t--) {
                        TimeUnit.SECONDS.sleep(1);
                        status = "Restocking " + i.getName() + " - " + t + "s left";
                        server.notifyUpdate();
                    }
                    i.setStock((int) i.getStock() + 100);
                    notifyUpdate();
                }
                i.getLock().unlock();
            }
        } catch(ConcurrentModificationException e) {
            restock();
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                restock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
