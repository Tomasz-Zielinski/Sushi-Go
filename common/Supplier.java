package common;

import java.io.Serializable;

public class Supplier extends Model implements Serializable {

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

    public Number getDistance() {
        return distance;
    }

    public void setName(String name) {
        notifyUpdate("name", this.name, name);
        this.name = name;
    }
}