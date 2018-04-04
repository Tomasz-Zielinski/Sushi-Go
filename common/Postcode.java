package common;

public class Postcode extends Model {

    private String code;
    private Number distance;

    public Postcode(String code, Number distance) {
        this.code = code;
        this.distance = distance;
    }

    @Override
    public String getName() {
        return null;
    }
}
