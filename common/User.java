package common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User extends Model implements Serializable {

    private String name, password, address;
    private Postcode postcode;
    private Map<Dish, Number> basket;

    public User(String name, String password, String address, Postcode postcode) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.postcode = postcode;
        this.basket = new HashMap<>();
    }

    public void addToBasket(Dish dish, Number quantity) {
        basket.put(dish, quantity);
    }

    public void setPostcode(Postcode postcode) {
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Postcode getPostcode() {
        return postcode;
    }

    public Map<Dish, Number> getBasket() {
        return basket;
    }
}
