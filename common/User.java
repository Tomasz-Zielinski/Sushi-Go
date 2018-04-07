package common;

public class User extends Model {

    private String name;
    private String password;
    private String address;
    private Postcode postcode;

    public User(String name, String password, String address, Postcode postcode) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public Postcode getPostcode() { return postcode; }
}
