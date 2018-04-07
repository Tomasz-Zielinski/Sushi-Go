import server.Receive;
import server.Send;
import server.Server;
import server.ServerWindow;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerApplication extends Thread {

    private ServerSocket serverSocket;

    public static void main(String[] args) throws FileNotFoundException {
        Server server = new Server();
        ServerWindow window = new ServerWindow(server);
        server.assign(window);
        try {
            Thread t = new ServerApplication(6666);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerApplication(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket socket = serverSocket.accept();
                System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                Thread t1 = new Thread(new Receive(socket));
                Thread t2 = new Thread(new Send(socket));
                t1.start();
                t2.start();
                while (true) {

                }

//                server.close();

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}