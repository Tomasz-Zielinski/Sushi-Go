package server;

import common.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server extends Model implements ServerInterface {

    private List<User> users = new ArrayList<>();
    private List<Dish> dishes = new ArrayList<>();
    private boolean restockingIngredientsEnbaled = true;
    private boolean restockingDishesEnbaled = true;
    private Map<Dish, Number> dishStockLevels = new HashMap<>();
    private List<Ingredient> ingredients = new ArrayList<>();
    private Map<Ingredient, Number> ingredientStockLevels = new HashMap<>();
    private List<Supplier> suppliers = new ArrayList<>();
    private List<Drone> drones = new ArrayList<>();
    private List<Staff> staff = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Postcode> postcodes = new ArrayList<>();
    private ServerWindow window;

    public void assign(ServerWindow window) {
        this.window = window;
    }

    @Override
    public void loadConfiguration(String filename) throws FileNotFoundException {
        try {
            Files.lines(Paths.get(filename), StandardCharsets.UTF_8).forEach(e -> {
                if(e.length() != 0) {
                    String[] arr = e.split(":");
                    if (e.startsWith("SUPPLIER")) {
                        addSupplier(arr[1], Integer.valueOf(arr[2]));
                    } else if(e.startsWith("INGREDIENT")) {
                        Supplier sup = new Supplier("Blank", 0);
                        for (Supplier supplier : suppliers) {
                            if(supplier.getName().equals(arr[3])) sup = supplier;
                        }
                        addIngredient(arr[1], arr[2], sup, Integer.valueOf(arr[4]), Integer.valueOf(arr[5]));
                    } else if(e.startsWith("DISH")) {
                        addDish(arr[1], arr[2], Integer.valueOf(arr[3]), Integer.valueOf(arr[4]), Integer.valueOf(arr[5]));
                    } else if(e.startsWith("POSTCODE")) {
                        addPostcode(arr[1], Integer.valueOf(arr[2]));
                    } else if(e.startsWith("USER")) {
                        Postcode code = new Postcode("Blank", 0);
                        for (Postcode postcode : postcodes) {
                            if(postcode.getCode().equals(arr[4])) {
                                code = postcode;
                            }
                        }
                        users.add(new User(arr[1], arr[2], arr[3], code));
                    } else if(e.startsWith("ORDER")) {
                        Number cost = 0;
                        int quantity;
                        String[] o = arr[2].split(",");
                        for (String s : o) {
                            String[] order = s.split(" ");
                            if(order.length > 3) order[2] = order[2] + " " + order[3];
                            quantity = Integer.valueOf(order[0]);
                            String dish = order[2];
                            for (Dish d : dishes) {
                                if(d.getName().equals(dish)) {
                                    cost = quantity * (int) d.getPrice();
                                }
                            }
                        }
                        Number distance = 0;
                        for (User user : users) {
                            if(user.getName().equals(arr[1])) {
                                distance = user.getPostcode().getDistance();
                            }
                        }
                        orders.add(new Order(arr[1], cost, distance));
                    } else if(e.startsWith("STOCK")) {

                    } else if(e.startsWith("STAFF")) {
                        addStaff(arr[1]);
                    } else if(e.startsWith("DRONE")) {
                        addDrone(Integer.valueOf(arr[1]));
                    } else {
                        System.out.println("????");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRestockingIngredientsEnabled(boolean enabled) { restockingIngredientsEnbaled = enabled; }

    @Override
    public void setRestockingDishesEnabled(boolean enabled) { restockingDishesEnbaled = enabled; }

    @Override
    public void setStock(Dish dish, Number stock) { dish.setStock(stock); }

    @Override
    public void setStock(Ingredient ingredient, Number stock) {
        ingredient.setStock(stock);
        notifyUpdate();
    }

    @Override
    public List<Dish> getDishes() { return dishes; }

    @Override
    public Dish addDish(String name, String description, Number price, Number restockThreshold, Number restockAmount) {
        Dish dish = new Dish(name, description, price, restockThreshold, restockAmount);
        dishes.add(dish);
        notifyUpdate();
        return dish;
    }

    @Override
    public void removeDish(Dish dish) throws UnableToDeleteException { dishes.remove(dish); }

    @Override
    public void addIngredientToDish(Dish dish, Ingredient ingredient, Number quantity) {

    }

    @Override
    public void removeIngredientFromDish(Dish dish, Ingredient ingredient) {

    }

    @Override
    public void setRecipe(Dish dish, Map<Ingredient, Number> recipe) {
        dish.setRecipe(recipe);
    }

    @Override
    public void setRestockLevels(Dish dish, Number restockThreshold, Number restockAmount) {
        dish.setRestockLevels(restockThreshold, restockAmount);
        notifyUpdate();
    }

    @Override
    public Number getRestockThreshold(Dish dish) {
        return dish.getRestockThreshold();
    }

    @Override
    public Number getRestockAmount(Dish dish) {
        return dish.getRestockAmount();
    }

    @Override
    public Map<Ingredient, Number> getRecipe(Dish dish) {
        return dish.getRecipe();
    }

    @Override
    public Map<Dish, Number> getDishStockLevels() {
        return dishStockLevels;
    }

    @Override
    public List<Ingredient> getIngredients() { return ingredients; }

    @Override
    public Ingredient addIngredient(String name, String unit, Supplier supplier, Number restockThreshold, Number restockAmount) {
        Ingredient ingredient = new Ingredient(name, unit, supplier, restockThreshold, restockAmount);
        ingredients.add(ingredient);
        notifyUpdate();
        return ingredient;
    }

    @Override
    public void removeIngredient(Ingredient ingredient) throws UnableToDeleteException {
        ingredients.remove(ingredient);
        notifyUpdate();
    }

    @Override
    public void setRestockLevels(Ingredient ingredient, Number restockThreshold, Number restockAmount) {
        ingredient.setRestockLevels(restockThreshold, restockAmount);
        notifyUpdate();
    }

    @Override
    public Number getRestockThreshold(Ingredient ingredient) {
        return ingredient.getRestockThreshold();
    }

    @Override
    public Number getRestockAmount(Ingredient ingredient) {
        return ingredient.getRestockAmount();
    }

    @Override
    public Map<Ingredient, Number> getIngredientStockLevels() {
        return ingredientStockLevels;
    }

    @Override
    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    @Override
    public Supplier addSupplier(String name, Number distance) {
        Supplier supplier = new Supplier(name, distance);
        suppliers.add(supplier);
        notifyUpdate();
        return supplier;
    }

    @Override
    public void removeSupplier(Supplier supplier) throws UnableToDeleteException {
        suppliers.remove(supplier);
        notifyUpdate();
    }

    @Override
    public Number getSupplierDistance(Supplier supplier) {
        return supplier.getDistance();
    }

    @Override
    public List<Drone> getDrones() {
        return drones;
    }

    @Override
    public Drone addDrone(Number speed) {
        Drone drone = new Drone(speed);
        drones.add(drone);
        notifyUpdate();
        return drone;
    }

    @Override
    public void removeDrone(Drone drone) throws UnableToDeleteException {
        drones.remove(drone);
        notifyUpdate();
    }

    @Override
    public Number getDroneSpeed(Drone drone) {
        return drone.getSpeed();
    }

    @Override
    public String getDroneStatus(Drone drone) {
        return drone.getStatus();
    }

    @Override
    public List<Staff> getStaff() { return staff; }

    @Override
    public Staff addStaff(String name) {
        Staff s = new Staff(name);
        staff.add(s);
        notifyUpdate();
        return s;
    }

    @Override
    public void removeStaff(Staff staff) throws UnableToDeleteException {
        this.staff.remove(staff);
        notifyUpdate();
    }

    @Override
    public String getStaffStatus(Staff staff) {
        return staff.getStatus();
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public void removeOrder(Order order) throws UnableToDeleteException {
        orders.remove(order);
        notifyUpdate();
    }

    @Override
    public Number getOrderDistance(Order order) {
        return order.getDistance();
    }

    @Override
    public boolean isOrderComplete(Order order) {
        return order.isComplete();
    }

    @Override
    public String getOrderStatus(Order order) {
        return order.getStatus();
    }

    @Override
    public Number getOrderCost(Order order) {
        return order.getCost();
    }

    @Override
    public List<Postcode> getPostcodes() {
        return postcodes;
    }

    @Override
    public void addPostcode(String code, Number distance) {
        postcodes.add(new Postcode(code, distance));
        notifyUpdate();
    }

    @Override
    public void removePostcode(Postcode postcode) throws UnableToDeleteException {
        postcodes.remove(postcode);
        notifyUpdate();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void removeUser(User user) throws UnableToDeleteException {
        users.remove(user);
        notifyUpdate();
    }

    @Override
    public String getName() {
        return "SERVER HERE";
    }

    @Override
    public void addUpdateListener(UpdateListener listener) {

    }

    @Override
    public void notifyUpdate() {
        window.refreshAll();
    }
}
