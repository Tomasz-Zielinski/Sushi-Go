package common;

public class Order extends Model {

    private String name;
    private Number distance;
    private boolean complete;
    private String status;
    private Number cost;

    public Order(String name,  Number cost, Number distance) {
        this.name = name;
        this.distance = distance;
        this.complete = false;
        this.status = "Unfinished";
        this.cost = cost;
    }

    @Override
    public String getName() {
        return name;
    }
    public Number getDistance() { return distance; }
    public boolean isComplete() { return complete; }
    public String getStatus() { return status; }
    public Number getCost() { return cost; }

}
