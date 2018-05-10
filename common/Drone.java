package common;

import server.Server;
import server.ServerInterface;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Map;
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
                    System.out.println(i.getLock());
                    status = "Idle";
                    server.notifyUpdate();
                    TimeUnit.SECONDS.sleep(2);
                    continue;
                }
                status = "Checking " + i.getName() + " level";
                server.notifyUpdate();
                TimeUnit.SECONDS.sleep(2);
                if ((int) i.getStock() < (int) i.getRestockThreshold()*1000) {
                    i.getLock().lock();
                    int waitTime =  (int) i.getSupplier().getDistance() / (int) speed * 5;
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

    public void deliver() throws InterruptedException, ServerInterface.UnableToDeleteException, IOException {
        for (Order order : server.getOrders()) {
            if(!order.getLock().tryLock()) {
                status = "Another drone is making order for " + order.getName();
                server.notifyUpdate();
                TimeUnit.SECONDS.sleep(2);
                continue;
            }
            for (Map.Entry<Dish, Number> dishNumberEntry : order.getBasket().entrySet()) {
                for (Dish dish : server.getDishes()) {
                    if(dish == dishNumberEntry.getKey()) {
                        if((int)dish.getStock() < (int)dishNumberEntry.getValue()) {
                            return;
                        }
                    }
                }
            }
            order.getLock().lock();
            int waitTime = (int) order.getDistance() / (int) speed ;
            status = "Estimated " + waitTime;
            server.notifyUpdate();
            TimeUnit.SECONDS.sleep(1);
            for (int t = waitTime; t > 0; t--) {
                TimeUnit.SECONDS.sleep(1);
                status = "Delivering " + order.getName() + " order - " + t + "s left";
                server.notifyUpdate();
            }
            order.setComplete();
            notifyUpdate();
            server.removeOrder(order);
            order.getLock().unlock();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
//                deliver();
                restock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
