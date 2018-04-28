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
        this.server = server;
    }

    public void run() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerReceive sr = new ServerReceive(socket, server);
        ServerSend sd = new ServerSend(socket);
        Thread t1 = new Thread(sr);
        Thread t2 = new Thread(sd);
        t1.start();
        t2.start();
        while(true) {
            try {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                socket = serverSocket.accept();
                System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                sr.setSocket(socket);
                sd.setSocket(socket);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}