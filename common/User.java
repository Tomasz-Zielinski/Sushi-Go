package common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User extends Model implements Serializable {

    private String name;
    private String password;
    private String address;
    private Postcode postcode;
    private Map<Dish, Number> basket = new HashMap<>();

    public User(String name, String password, String address, Postcode postcode) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.postcode = postcode;
    }

    public void addToBasket(Dish dish, Number quantity) { this.basket.put(dish, quantity); }

    public String getName() {
        return name;
    }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public Postcode getPostcode() { return postcode; }
    public Map<Dish, Number> getBasket() { return basket; }
}
