package common;

import java.io.Serializable;

public class Packet implements Serializable {

    private String message;
    private Order order;

    public Packet(String message) {this.message = message; }
    public Packet(String message, Order order) {
        this.message = message;
        this.order = order;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Order getOrder() {
        return order;
    }
}
