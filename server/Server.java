package server;

import common.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements ServerInterface {

    private ArrayList<Dish> dishes = new ArrayList<>();
    private boolean restockingIngredientsEnbaled = true;
    private boolean restockingDishesEnbaled = true;
    private Map<Dish, Number> dishStockLevels = new HashMap<>();
    private List<Ingredient> ingredients = new ArrayList<>();
    private Map<Ingredient, Number> ingredientStockLevels = new HashMap<>();
    private List<Supplier> suppliers = new ArrayList<>();
    public List<Drone> drones = new ArrayList<>(){{add(new Drone(10));}};
    private List<Staff> staff = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Postcode> postcodes = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    @Override
    public void loadConfiguration(String filename) throws FileNotFoundException {

    }

    @Override
    public void setRestockingIngredientsEnabled(boolean enabled) { restockingIngredientsEnbaled = enabled; }

    @Override
    public void setRestockingDishesEnabled(boolean enabled) { restockingDishesEnbaled = enabled; }

    @Override
    public void setStock(Dish dish, Number stock) { dish.setStock(stock); }

    @Override
    public void setStock(Ingredient ingredient, Number stock) { ingredient.setStock(stock); }

    @Override
    public List<Dish> getDishes() { return dishes; }

    @Override
    public Dish addDish(String name, String description, Number price, Number restockThreshold, Number restockAmount) {
        return new Dish(name, description, price, restockThreshold, restockAmount);
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
    public void setRecipe(Dish dish, Map<Ingredient, Number> recipe) { dish.setRecipe(recipe); }

    @Override
    public void setRestockLevels(Dish dish, Number restockThreshold, Number restockAmount) { dish.setRestockLevels(restockThreshold, restockAmount); }

    @Override
    public Number getRestockThreshold(Dish dish) {
        return dish.getRestockThreshold();
    }

    @Override
    public Number getRestockAmount(Dish dish) {
        return dish.getRestockamount();
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
        return new Ingredient(name, unit, supplier, restockThreshold, restockAmount);
    }

    @Override
    public void removeIngredient(Ingredient ingredient) throws UnableToDeleteException {
        ingredients.remove(ingredient);
    }

    @Override
    public void setRestockLevels(Ingredient ingredient, Number restockThreshold, Number restockAmount) {
        ingredient.setRestockLevels(restockThreshold, restockAmount);
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
        return new Supplier(name, distance);
    }

    @Override
    public void removeSupplier(Supplier supplier) throws UnableToDeleteException {
        suppliers.remove(supplier);
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
        return new Drone(speed);
    }

    @Override
    public void removeDrone(Drone drone) throws UnableToDeleteException {
        drones.remove(drone);
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
        return new Staff(name);
    }

    @Override
    public void removeStaff(Staff staff) throws UnableToDeleteException { this.staff.remove(staff); }

    @Override
    public String getStaffStatus(Staff staff) {
        return staff.getStatus();
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public void removeOrder(Order order) throws UnableToDeleteException { orders.remove(order); }

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
    }

    @Override
    public void removePostcode(Postcode postcode) throws UnableToDeleteException {
        postcodes.remove(postcode);
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void removeUser(User user) throws UnableToDeleteException {
        users.remove(user);
    }

    @Override
    public void addUpdateListener(UpdateListener listener) {

    }

    @Override
    public void notifyUpdate() {

    }
}
