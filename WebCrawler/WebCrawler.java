import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;

public class WebCrawler {
    private Socket koneksi = null;

    public static void main(String[] args)
    throws UnknownHostException, IOException {
        WebCrawler crawler = new WebCrawler();
        crawler.craw();
    }

    public void craw() 
    throws UnknownHostException, IOException {
        koneksi = new Socket("google.co.id", 80);

        // Kirim perintah untuk informasi namaDomain
        kirimPerintah("GET index.html");

        // Baca balasannya
        bacaBalasan();

        // Tutup socket-nya => dengan sendirinya menutup semua stream
        koneksi.close();
    }

    public void kirimPerintah(String perintah) throws IOException {
        Writer keluaranWriter = new OutputStreamWriter(koneksi.getOutputStream()); 
        BufferedWriter keluaranBuff = new BufferedWriter(keluaranWriter);
        keluaranBuff.write(perintah);
        keluaranBuff.newLine();
        keluaranBuff.flush();
    }

    public void bacaBalasan() throws IOException {
        System.out.println("Hasil crawler : ");
        // 1 & 2. Minta socket untuk baca -> Langsung dibuka
        InputStream masukan = koneksi.getInputStream();
        // Karena keluarannya panjang sehingga harus dibuffer
        BufferedInputStream masukanBuffer = new BufferedInputStream(masukan);
        // Selagi masih ada data baca
        int data = masukanBuffer.read();
        while (data != -1) {
            System.out.write((char) data);
            data = masukanBuffer.read();
        }
    }
}
