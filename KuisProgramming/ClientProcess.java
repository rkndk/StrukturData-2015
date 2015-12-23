import java.net.Socket;
import java.net.InetAddress;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.Calendar;

public class ClientProcess implements Runnable {
    public ClientProcess(Socket koneksi) {
        this.koneksi = koneksi;
    }

    public void run() {        
        if (koneksi != null) {
            // Ambil IP dari koneksi
            ipStr = koneksi.getInetAddress().getHostAddress();

            try {
                // Ambil InputStream
                masukan = koneksi.getInputStream();
                masukanReader = new BufferedReader(new InputStreamReader(masukan)); 
                // Ambil OutputStream
                keluaran = koneksi.getOutputStream();
                keluaranWriter = new BufferedWriter(new OutputStreamWriter(keluaran)); 

                // Selama masih terhubung dengan client tangani
                while (koneksi != null) {
                    tangani();
                }            
            }
            catch(IOException salah) { 
                System.out.println(salah);
            }
            finally {
                try { 
                    // Tampilkan pesan
                    System.out.println("Tutup: " + ipStr);
                    System.out.println();

                    // Tutup koneksi
                    koneksi.close();
                }
                catch(IOException salah) {
                    System.out.println(salah);
                }                
            }
        }
    }

    private void tangani() throws IOException {
        // Baca perintah dari socket
        String perintah = masukanReader.readLine();
        String[] hasil = perintah.split(" ");
        // Jika tidak ada perintah keluar saja
        if (perintah == null)
            return;            
        // Ada perintah, hilangkan spasi depan & belakang serta ubah ke huruf besar semua
        perintah = perintah.trim().toUpperCase();

        // Tangani perintahnya
        if (perintah.compareTo("SIAPA") == 0)
            synchronized(this) {
                keluaranWriter.write(ipStr);
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
        else if (perintah.compareTo("WAKTU") == 0)
            synchronized(this) {
                Calendar kalender = Calendar.getInstance();
                String waktu = kalender.getTime().toString();
                keluaranWriter.write(waktu);
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
        else if (hasil[0].compareTo("WAKTU") == 0)
            synchronized(this) {
                try{
                    int perubahan = Integer.parseInt(hasil[1]);
                    Calendar kalender = Calendar.getInstance();
                    kalender.add(Calendar.HOUR_OF_DAY, perubahan-7); // WIB adalah GMT+7 sehingga -7 untuk waktu GMT
                    String waktu = kalender.getTime().toString();
                    keluaranWriter.write(waktu);
                    keluaranWriter.newLine();
                    keluaranWriter.flush();
                }catch (NumberFormatException ex) {
                    //jika yang diinput user tidak sesuai ketentuan
                    keluaranWriter.write("Perintah tidak dikenal !");
                    keluaranWriter.newLine();
                    keluaranWriter.flush();
                }
            }
        else {
            keluaranWriter.write("Perintah tidak dikenal !");
            keluaranWriter.newLine();
            keluaranWriter.flush();
        }

        // Tampilkan perintahnya
        System.out.println("Dari: " + ipStr);
        System.out.println("perintah: " + perintah);
        System.out.println();
    }

    // Koneksi ke Client
    private Socket koneksi; 
    // IP address dari client
    private String ipStr; 

    // InputStream untuk baca perintah
    private InputStream masukan = null;
    // Reader untuk InputStream, pakai yang buffer
    private BufferedReader masukanReader = null;
    // OutputStream untuk tulis balasan
    private OutputStream keluaran = null;
    // Writer untuk OutputStream, pakai yang buffer
    private BufferedWriter keluaranWriter = null;
}
