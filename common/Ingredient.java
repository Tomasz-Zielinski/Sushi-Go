package common;

public class Ingredient extends Model {

    private String name;
    private String unit;
    private Supplier supplier;
    private Number restockThreshold;
    private Number restockAmount;
    private Number stock;

    public Ingredient(String name, String unit, Supplier supplier, Number restockThreshold, Number restockAmount) {
        this.name = name;
        this.unit = unit;
        this.supplier = supplier;
        this.restockThreshold = restockThreshold;
        this.restockAmount = restockAmount;
    }

    @Override
    public String getName() {
        return name;
    }
    public Number getRestockThreshold() { return restockThreshold; }
    public Number getRestockAmount() { return restockAmount; }

    public void setStock(Number stock) { this.stock = stock; }
    public void setRestockLevels(Number restockThreshold, Number restockAmount) {
        this.restockThreshold = restockThreshold;
        this.restockAmount = restockAmount;
    }
}