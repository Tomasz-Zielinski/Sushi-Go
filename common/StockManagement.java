package common;

import java.util.*;

public class StockManagement {

    private Map<String, Integer> max;
    private List<Ingredient> ingredients;
    private Map<Dish, Number> dishStockLevels;

    StockManagement() {
        this.max = new HashMap<>();
        this.ingredients = new ArrayList<>();
        this.dishStockLevels = new HashMap<>();
    }

    public List<Ingredient> getIngredients() { return ingredients; }
    public Map<Dish, Number> getDishStockLevels() { return dishStockLevels; }

}