package common;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ingredient extends Model implements Serializable {

    private String name, unit;
    private Supplier supplier;
    private Number restockThreshold, restockAmount, stock;
    private ReentrantLock lock = new ReentrantLock();

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

    public Supplier getSupplier() {
        return supplier;
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

    public Lock getLock() {
        return lock;
    }

    public void setStock(Number stock) {
        notifyUpdate("stock", this.stock, stock);
        this.stock = stock;
    }

    public void setRestockLevels(Number restockThreshold, Number restockAmount) {
        notifyUpdate("restockThreshold", this.restockThreshold, restockThreshold);
        this.restockThreshold = restockThreshold;
        notifyUpdate("restockAmount", this.restockAmount, restockAmount);
        this.restockAmount = restockAmount;
    }
}