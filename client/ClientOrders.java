package client;

import common.Comms;
import common.Order;

import java.util.List;

public class ClientOrders implements Runnable {

    private List<Order> orders;
    private Comms comms;
    private Client client;

    public ClientOrders(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            if (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                    if(client.getUser() != null) {
                        client.notifyUpdate();
                    }
                } catch (InterruptedException e) {
                    break;
                }
            } else {
                break;
            }
        }
    }


}
