package common;

import java.io.Serializable;

public class Message implements Serializable {

    private String message = "";
    private String username = "";
    private String password = "";
    private Order order;

    public Message(String message) {this.message = message; }
    public Message(String message, Order order) {
        this.message = message;
        this.order = order;
    }
    public Message(String message, String username, String password) {
        this.message = message;
        this.username = username;
        this.password = password;
    }

    public String getMessage() {
        return message;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Order getOrder() {
        return order;
    }
}
