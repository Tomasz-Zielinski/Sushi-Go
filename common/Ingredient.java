package common;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ingredient extends Model {

    private String name;
    private String unit;
    private Supplier supplier;
    private Number restockThreshold;
    private Number restockAmount;
    private Number stock;
    private Lock lock = new ReentrantLock();

    public Ingredient(String name, String unit, Supplier supplier, Number restockThreshold, Number restockAmount) {
        this.name = name;
        this.unit = unit;
        this.supplier = supplier;
        this.restockThreshold = restockThreshold;
        this.restockAmount = restockAmount;
        this.stock = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    public Number getRestockThreshold() {
        return restockThreshold;
    }

    public Number getRestockAmount() {
        return restockAmount;
    }

    public Number getStock() {
        return stock;
    }

    public String getUnit() {
        return unit;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setStock(Number stock) {
        this.stock = stock;
    }

    public Lock getLock() {
        return lock;
    }

    public void setRestockLevels(Number restockThreshold, Number restockAmount) {
        notifyUpdate("restockThreshold", this.restockThreshold, restockThreshold);
        notifyUpdate("restockAmount", this.restockAmount, restockAmount);
        notifyUpdate("name", this.name, "xD");
        this.restockThreshold = restockThreshold;
        this.restockAmount = restockAmount;
    }
}