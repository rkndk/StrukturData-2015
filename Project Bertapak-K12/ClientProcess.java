import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;

public class ClientProcess implements Runnable {
    // Data user
    private ArrayList<DataUser>data=null;
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
    // Selain perintah yang ada
    private final static String PERINTAH_TIDAK_DIKENAL = "Perintah tidak dikenal!";

    public ClientProcess(Socket koneksi, ArrayList<DataUser> data) {
        this.koneksi = koneksi;
        this.data=data;
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
                tangani();
            }
            catch(IOException salah) { 
                System.out.println(salah);
            }
            finally {
                try { 
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
        // Jika tidak ada perintah keluar saja
        if (perintah == null)
            return;            
        // Ada perintah, hilangkan spasi depan & belakang serta ubah ke huruf besar semua
        perintah = perintah.trim().toUpperCase();

        // Ambil parameter-nya
        String[] parameter = perintah.split(" +");

        // Tangani perintahnya
        try{
            if (parameter[0].compareTo("ID") == 0 && parameter.length==2) {
                DataUser dataUser = cekData(parameter[1]);
                String pesan;

                if(dataUser==null){
                    dataUser = new DataUser(parameter[1]);
                    data.add(dataUser);
                    pesan="User ID berhasil terdaftar";
                }
                else {
                    pesan="User ID yang sama telah terdaftar";
                }

                // Membalas status pendaftaran ID
                keluaranWriter.write(pesan);
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
            else if (parameter[0].compareTo("LANGKAH") == 0 && parameter.length==4) {
                DataUser dataUser = cekData(parameter[3]);
                String pesan;

                if(dataUser==null){
                    pesan="User ID belum terdaftar";
                }
                else {
                    String koordinat = ""+parameter[1]+" "+parameter[2];
                    dataUser.setData(koordinat);
                    pesan="Langkah berhasil tersimpan";
                }

                keluaranWriter.write(pesan);
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
            else if (parameter[0].compareTo("STEP")== 0 && parameter.length<=4){
                DataUser dataUser=null;
                String pesan;

                if(parameter[1].compareTo("TOTAL")==0){
                    dataUser = cekData(parameter[2]);
                    if(dataUser==null){
                        pesan="User ID belum terdaftar";
                    }
                    else {
                        pesan="jumlah langkah adalah "+dataUser.getLangkah();
                    }
                }
                else{
                    dataUser = cekData(parameter[3]);
                    if(dataUser==null){
                        pesan="User ID belum terdaftar";
                    }
                    else {
                        int hasil = dataUser.getLangkah(parameter[1],parameter[2]);
                        if(hasil>=0)
                            pesan="Jumlah langkah adalah "+hasil;
                        else
                            pesan="Format waktu salah";
                    }
                }
                keluaranWriter.write(pesan,0,pesan.length());
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
            else if (parameter[0].compareTo("RUTE")== 0 && parameter.length<=4){
                DataUser dataUser=null;
                String pesan;

                if(parameter[1].compareTo("TOTAL")==0){
                    dataUser = cekData(parameter[2]);
                    if(dataUser==null){
                        pesan="User ID belum terdaftar";
                    }
                    else {
                        pesan="Rute yang dilewati "+dataUser.getRute();
                    }
                }
                else{
                    dataUser = cekData(parameter[3]);
                    if(dataUser==null){
                        pesan="User ID belum terdaftar";
                    }
                    else {
                        String hasil = dataUser.getRute(parameter[1],parameter[2]);
                        if(hasil!=null)
                            pesan="Rute yang dilewati "+hasil;
                        else
                            pesan="Format waktu salah";
                    }
                }
                keluaranWriter.write(pesan,0,pesan.length());
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }
            else {
                // Perintah tidak dikenal
                keluaranWriter.write(PERINTAH_TIDAK_DIKENAL, 0, PERINTAH_TIDAK_DIKENAL.length());
                keluaranWriter.newLine();
                keluaranWriter.flush();
            }

            // Tampilkan perintahnya
            System.out.println("Dari: " + ipStr);
            System.out.println("perintah: " + perintah);
            System.out.println();
        }catch(Exception e){
            keluaranWriter.write("Kesalahan Format");
            keluaranWriter.newLine();
            keluaranWriter.flush();
        }
    }

    public DataUser cekData(String userID){
        for(DataUser dataUser : data){
            if(userID.equals(dataUser.getID())){
                return dataUser;
            }   
        }
        return null;
    }
}