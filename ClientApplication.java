import client.Client;
import client.ClientWindow;
import client.ClientReceive;
import client.ClientSend;

import java.io.IOException;
import java.net.Socket;


public class ClientApplication {

    public static void main(String[] args) {
        String serverName = "localhost";
        int port = 6666;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket socket = new Socket(serverName, port);
            System.out.println("Connected to " + socket.getRemoteSocketAddress());
//            Thread t1 = new Thread(new ClientReceive(socket));
            Thread t2 = new Thread(new ClientSend(socket));
//            t1.start();
            t2.start();
            Client client = new Client(socket);
            ClientWindow window = new ClientWindow(client);
            client.assign(window);
            while(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}