import common.*;
import server.Server;
import server.ServerWindow;

import java.util.concurrent.TimeUnit;


public class ServerApplication {

    public static void main(String[] args) {
        Server server = new Server();
        ServerWindow window = new ServerWindow(server);
        while(true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            server.addDrone(5);
            window.refreshAll();
        }
    }
}