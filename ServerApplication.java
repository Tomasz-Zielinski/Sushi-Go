import server.DataPersistence;
import server.Server;
import server.ServerReceive;
import server.ServerWindow;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication extends Thread {

    private ServerSocket serverSocket;
    private Server server;
    private static Thread mainThread, dataPersistance;

    public static void main(String[] args) throws IOException {
        initialise();
        launchGUI();
    }

    private static void initialise() throws IOException {
        Server server = new Server();
        ServerWindow window = new ServerWindow(server);
        server.build(window);
        mainThread = new ServerApplication(8000, server);
        dataPersistance = new Thread(new DataPersistence(server));
    }

    private static void launchGUI() {
        mainThread.start();
        dataPersistance.start();
    }

    private ServerApplication(int port, Server server) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            this.server = server;
        } catch (BindException e) {
            System.err.println("Server is already running");
        }
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
        while (true) {
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