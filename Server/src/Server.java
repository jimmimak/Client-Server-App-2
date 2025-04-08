import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(5555);
            CubbyHole ch = new CubbyHole();

            while (true) {
                Socket sock = server.accept(); //anamonh gia sundesh apo ton pelath
                
                System.out.println("A new client is connected: " + sock);
                System.out.println("Assigning new thread for this client\n");
                
                //dhmiourgia enos thread
                Thread t = new ClientHandler(sock, ch);
                t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
