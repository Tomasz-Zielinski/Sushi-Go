import server.ServerReceive;
import server.Server;
import server.ServerWindow;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication extends Thread {

    private ServerSocket serverSocket;
    private Server server;

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        ServerWindow window = new ServerWindow(server);
        server.build(window);
        server.loadConfiguration("src/server/config.txt");
        Thread t = new ServerApplication(6666, server);
        t.start();
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
        Thread t = new Thread(sr);
        t.start();
        while(true) {
            try {
                socket = serverSocket.accept();
                System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                sr.setSocket(socket);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

}