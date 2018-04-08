package common;

import java.io.Serializable;
import java.util.Map;

public class Dish extends Model implements Serializable {

    private String name;
    private String description;
    private Number price;
    private Number restockThreshold;
    private Number restockAmount;
    private Map<Ingredient, Number> recipe;
    private Number stock;

    public Dish(String name, String description, Number price, Number restockThreshold, Number restockAmount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.restockThreshold = restockThreshold;
        this.restockAmount = restockAmount;
        this.stock = 0;
    }

    @Override
    public String getName() {
        return name;
    }
    public String getDescription() { return description; }
    public Number getPrice() { return price; }
    public Number getRestockThreshold() { return restockThreshold; }
    public Number getRestockAmount() { return restockAmount; }
    public Number getStock() { return stock; }
    public Map<Ingredient, Number> getRecipe() { return recipe; }

    public void setStock(Number stock) { this.stock = stock; }
    public void setRecipe(Map<Ingredient, Number> recipe) { this.recipe = recipe; }
    public void setRestockLevels(Number restockThreshold, Number restockAmount) {
        this.restockThreshold = restockThreshold;
        this.restockAmount = restockAmount;
    }
}