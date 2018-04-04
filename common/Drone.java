package common;

public class Drone extends Model {

    private String name;
    private Number speed;
    private String status;

    public Drone(Number speed) {
        this.name = "Drone";
        this.speed = speed;
        this.status = "Free";
    }

    @Override
    public String getName() { return name; }
    public Number getSpeed() { return speed;}
    public String getStatus() { return status; }
}
