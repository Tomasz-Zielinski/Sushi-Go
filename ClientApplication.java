import client.Client;
import client.ClientOrders;
import client.ClientWindow;
import java.io.IOException;
import java.net.Socket;


public class ClientApplication {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8000);
            System.out.println("Connected to " + socket.getRemoteSocketAddress());
            Client client = new Client(socket);
            ClientWindow window = new ClientWindow(client);
            client.assign(window);
            Thread updateOrders = new Thread(new ClientOrders(client));
            updateOrders.run();
            while(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}