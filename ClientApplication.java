import client.Client;
import client.ClientWindow;
import java.io.IOException;
import java.net.Socket;


public class ClientApplication {

    private static Client client;

    public static void main(String[] args) {
        initialise();
        launchGUI();
    }

    private static void initialise() {
        try {
            Socket socket = new Socket("localhost", 8000);
            System.out.println("Connected to " + socket.getRemoteSocketAddress());
            client = new Client(socket);
        } catch (IOException e) {
            System.err.println("Run server first");
        }
    }

    private static void launchGUI() {
        ClientWindow window = new ClientWindow(client);
        client.build(window);
        while(true) {

        }
    }

}