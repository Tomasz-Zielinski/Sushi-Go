import server.ServerSend;
import server.ServerReceive;
import server.Server;
import server.ServerWindow;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerApplication extends Thread {

    private ServerSocket serverSocket;
    private Server server;

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        ServerWindow window = new ServerWindow(server);
        server.assign(window);
        server.loadConfiguration("src/server/config.txt");
        Thread t = new ServerApplication(6666, server);
        t.start();
        System.out.println(server.getUsers());
    }

    private ServerApplication(int port, Server server) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
        this.server = server;
    }

    public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket socket = serverSocket.accept();
                System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                Thread t1 = new Thread(new ServerReceive(socket, server));
                Thread t2 = new Thread(new ServerSend(socket));
                t1.start();
                t2.start();
                while (true);
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