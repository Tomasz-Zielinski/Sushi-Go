package common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Dish extends Model implements Serializable {

    private String name, description;
    private Number price, restockThreshold, restockAmount, stock;
    private Map<Ingredient, Number> recipe;
    private Lock lock;

    public Dish(String name, String description, Number price, Number restockThreshold, Number restockAmount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restockThreshold = restockThreshold;
        this.restockAmount = restockAmount;
        this.recipe = new HashMap<>();
        this.stock = 0;
        this.lock = new ReentrantLock();
    }

    @Override
    public String getName() {
        return name;
    }
    public String getDescription() { return description; }
    public Number getPrice() { return price; }
    public Number getRestockThreshold() { return restockThreshold; }
    public Number getRestockAmount() { return restockAmount; }
    public Map<Ingredient, Number> getRecipe() { return recipe; }
    public Number getStock() { return stock; }
    public Lock getLock() {
        return lock;
    }

    public void setStock(Number stock) { this.stock = stock; }
    public void setRecipe(Map<Ingredient, Number> recipe) { this.recipe = recipe; }
    public void setRestockLevels(Number restockThreshold, Number restockAmount) {
        this.restockThreshold = restockThreshold;
        this.restockAmount = restockAmount;
    }
}