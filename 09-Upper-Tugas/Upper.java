/* Sesuai soal, asumsi sudah pasti file adalah berkas text, maka menggunakan FileReader dan FileWriter */
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Upper{
    public static void main (String[] args){
        try {
            Upper up = new Upper();
            up.upper("README.txt","kopi.txt");
        }
        catch (IOException kesalahan){
            System.out.printf("Terjadi kesalahan : %s",kesalahan);
        }
    }

    public void upper(String sumber, String sasaran) throws IOException {
        FileReader masukan = null;
        FileWriter keluaran = null;
        try {
            masukan = new FileReader(sumber);
            keluaran = new FileWriter(sasaran);
            int karakter = masukan.read();
            while (karakter != -1) {
                karakter = Character.toUpperCase(karakter);
                keluaran.write(karakter);
                karakter = masukan.read();
            }
            keluaran.flush();
        } 
        finally {
            if (masukan != null)
                masukan.close();
            if (keluaran != null)
                keluaran.close();
        }
    }
}