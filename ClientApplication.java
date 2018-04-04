import client.Client;
import client.ClientWindow;


public class ClientApplication {

    public static void main(String[] args) {
        Client client = new Client();
        ClientWindow window = new ClientWindow(client);
    }

}