package common;

public class Staff extends Model implements Runnable {

    private String name;
    private String status;

    public Staff(String name) {
        this.name = name;
        this.status = "Free";
    }


    @Override
    public String getName() {
        return name;
    }
    public String getStatus() { return status; }

    @Override
    public void run() {
        while(true) {


        }
    }
}