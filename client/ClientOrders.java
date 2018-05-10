package client;

import common.User;

import java.util.concurrent.TimeUnit;

public class ClientOrders implements Runnable {

    private Client client;
    private User user;

    ClientOrders(Client client, User user) {
        this.client = client;
        this.user = user;
    }

    @Override
    public void run() {
        while(true) {
            client.getOrders(user);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
