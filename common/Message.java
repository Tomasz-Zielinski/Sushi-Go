package common;

import java.io.Serializable;

public class Message implements Serializable {

    private String message;
    private Order order;

    public Message(String message) {this.message = message; }
    public Message(String message, Order order) {
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
