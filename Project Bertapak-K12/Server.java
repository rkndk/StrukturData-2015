import java.io.IOException;
import java.util.ArrayList;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {
    // Data user
    private ArrayList<DataUser> data = null;
	// Socket server
    private ServerSocket serverSocket = null;
    // Port untuk aplikasi ini
    private static final int PORT = 33333;
    
    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        data = new ArrayList<DataUser>();
    }
    
    public void dengar() throws IOException {
        while (true) {
            // Tunggu sampai ada koneksi dari client
            Socket koneksi = serverSocket.accept();
                
            // Buat thread untuk tangani client
            ClientProcess clientProcess = new ClientProcess(koneksi, data);
            Thread clientProcessThread = new Thread(clientProcess);
            clientProcessThread.start();                
        }
    }
}
