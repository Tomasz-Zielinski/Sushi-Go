package common;

public class Supplier extends Model {

    private String name;
    private Number distance;

    public Supplier(String name, Number distance) {
        this.name = name;
        this.distance = distance;
    }

    @Override
    public String getName() {
        return name;
    }
    public Number getDistance() { return distance; }
}