import java.net.Socket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

public class ProcessServerThread implements Runnable {
    private Socket koneksi; 

    public ProcessServerThread(Socket koneksiKiriman) {
        koneksi = koneksiKiriman;
    }

    public void run()
    {
        try{
            if (koneksi != null)
                prosesPermintaanClient(koneksi);
        }catch(IOException err) {
            System.out.println(err);
        }
    }

    private void prosesPermintaanClient(Socket socket) throws IOException 
    {
        String ip = socket.getInetAddress().getHostAddress();
        System.out.println("Dari: " + ip);

        // Ambil dan tampilkan masukan
        InputStream masukan = socket.getInputStream();
        BufferedReader masukanReader = new BufferedReader(new InputStreamReader(masukan)); 
        String baris = masukanReader.readLine();
        System.out.println(baris);

        // Baca pesan dari keyboard
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Pesan kembali: ");
        baris = keyboard.nextLine();

        // Kirim ke client
        OutputStream keluaran = socket.getOutputStream();
        BufferedWriter keluaranBuf = new BufferedWriter(new OutputStreamWriter(keluaran)); 
        keluaranBuf.write(baris);
        keluaranBuf.newLine();
        keluaranBuf.flush();

        // Tunggu kirim balasan client
        masukanReader = new BufferedReader(new InputStreamReader(masukan)); 
        baris = masukanReader.readLine();
        System.out.println(baris);
    }
}