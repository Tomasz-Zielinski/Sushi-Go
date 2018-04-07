package common;

import java.util.*;

public class StockManagement {

    private HashMap<String, Integer> max = new HashMap<String,Integer>();
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private HashMap<Dish, Number> dishStockLevels = new HashMap<Dish, Number>();

    public ArrayList<Ingredient> getIngredients() { return ingredients; }
    public Map<Dish, Number> getDishStockLevels() { return dishStockLevels; }

}