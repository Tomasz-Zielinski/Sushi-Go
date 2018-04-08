package common;

public class Postcode extends Model {

    private String name;
    private String code;
    private Number distance;

    public Postcode(String code, Number distance) {
        this.name = code;
        this.code = code;
        this.distance = distance;
    }

    @Override
    public String getName() {
        return name;
    }
    public String getCode() { return code; }
    public Number getDistance() { return distance; }
}
