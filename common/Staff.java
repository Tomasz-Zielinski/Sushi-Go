package common;

public class Staff extends Model implements Runnable {

    private String name;
    private String status;

    public Staff(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return null;
    }
    public String getStatus() { return status; }

    @Override
    public void run() {
        while(true) {


        }
    }
}