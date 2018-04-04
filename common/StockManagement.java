package common;

import java.util.*;

public class StockManagement {

    private HashMap<String, Integer> max = new HashMap<String,Integer>();
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private HashMap<Dish, Number> dishStockLevels = new HashMap<Dish, Number>();

    public ArrayList<Ingredient> getIngredients() { return ingredients; }
    public String checkStock() {
        ArrayList<String> count = new ArrayList<String>();
        ingredients.forEach(el -> count.add(el.getName()));
        for (HashMap.Entry<String, Integer> entry : max.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(Collections.frequency(count, key) > value) return key;
        }
        return "";
    }

    public Map<Dish, Number> getDishStockLevels() { return dishStockLevels; }

}