import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class KopiBerkasBuffer{
    public void kopi(String sumber, String sasaran) throws IOException{
        FileInputStream masukan = null;
        FileOutputStream keluaran = null;
        BufferedInputStream masukanBuffer = null;
        BufferedOutputStream keluaranBuffer = null;
        try {
            masukan = new FileInputStream(sumber);
            masukanBuffer = new BufferedInputStream(masukan);
            keluaran = new FileOutputStream(sasaran);
            keluaranBuffer = new BufferedOutputStream(keluaran);
            int karakter = masukanBuffer.read();
            while (karakter != -1) {
                keluaranBuffer.write(karakter);
                karakter = masukanBuffer.read();
            }
            keluaranBuffer.flush();
        } 
        finally {
            if (masukan != null)
                masukan.close();
            if (masukanBuffer != null)
                masukanBuffer.close();
            if (keluaran != null)
                keluaran.close();
            if (keluaranBuffer != null){
                keluaranBuffer.close();
            }
        }
    }

    public static void main (String[] args){
        //Ujicoba manual pada kelas KopiBerkasBuffer
        try {
            KopiBerkasBuffer kbb = new KopiBerkasBuffer();
            kbb.kopi("README.txt","kopi.txt");
        }
        catch (IOException kesalahan){
            System.out.printf("Terjadi kesalahan : %s",kesalahan);
        }
    }

}