import client.Client;
import client.ClientWindow;
import client.ClientReceive;
import client.ClientSend;

import java.io.IOException;
import java.net.Socket;


public class ClientApplication {

    public static void main(String[] args) {
        try {
            System.out.println("Connecting to localhost on port 6666");
            Socket socket = new Socket("localhost", 6666);
            System.out.println("Connected to " + socket.getRemoteSocketAddress());
//            Thread t1 = new Thread(new ClientReceive(socket));
            Thread t2 = new Thread(new ClientSend(socket));
//            t1.start();
            System.out.println("1");
            t2.start();
            Client client = new Client(socket);
            System.out.println("2");
            ClientWindow window = new ClientWindow(client);
            System.out.println("3");
            client.assign(window);
            while(true) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}