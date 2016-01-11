import java.net.Socket;
import java.net.InetAddress;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

public class Client {    
    /**
     * 1. Buka socket
     * 2. Ambil Input dan Output stream-nya
     * 3. Ubah Stream-nya ke Reader dan Writer
     * 4. Kirim satu perintah ke server dan 
     * 5. Tampung balasannya.
     * 6. Tutup koneksi
     */
	// Socket ke server
    private Socket koneksi;
    // Stream untuk menulis perintah
    private OutputStream keluaran;
    // Writer untuk stream untuk menulis perintah
    private BufferedWriter keluaranWriter;
    // Stream untum membaca balasan dari server
    private InputStream masukan;
    // Reader untuk stream membaca balasan dari server
    private BufferedReader masukanReader;      
    
	public Client(String ipStr, int port) 
	           throws IOException {
	        // Buka socket
	        InetAddress ip = InetAddress.getByName(ipStr);
	        koneksi = new Socket(ip, port);

	        // Minta stream untuk tulis
	        keluaran = koneksi.getOutputStream();
	        keluaranWriter = new BufferedWriter(new OutputStreamWriter(keluaran)); 
	        
	        // Minta stream untuk baca
	        masukan = koneksi.getInputStream();
	        masukanReader = new BufferedReader(new InputStreamReader(masukan)); 
	    }
	
    public String perintah(String perintah) 
           throws IOException {
        String balasan = null;
        
        // Kirim perintah ke server
        keluaranWriter.write(perintah, 0, perintah.length());               
        keluaranWriter.newLine();
        keluaranWriter.flush();
        
        // Baca balasan dari Server
        balasan = masukanReader.readLine();

        // Tutup koneksi
        koneksi.close();
        
        // Kembalikan balasan
        return balasan;
    }
}
