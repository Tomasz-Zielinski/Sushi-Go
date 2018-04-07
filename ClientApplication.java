import client.Client;
import client.ClientWindow;
import server.Receive;
import server.Send;


import java.io.IOException;
import java.net.Socket;


public class ClientApplication {

    public static void main(String[] args) {
        Client client = new Client();
        ClientWindow window = new ClientWindow(client);
        String serverName = "localhost";
        int port = 6666;
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket socket = new Socket(serverName, port);
            System.out.println("Just connected to " + socket.getRemoteSocketAddress());
            Thread t1 = new Thread(new Receive(socket));
            Thread t2 = new Thread(new Send(socket));
            t1.start();
            t2.start();
            while(true) {

            }

//            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}