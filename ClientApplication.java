import client.Client;
import client.ClientWindow;
import java.io.IOException;
import java.net.Socket;


public class ClientApplication {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 6666);
            System.out.println("Connected to " + socket.getRemoteSocketAddress());
            Client client = new Client(socket);
            ClientWindow window = new ClientWindow(client);
            client.assign(window);
            while(true) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}