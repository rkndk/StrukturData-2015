import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Merge {
    public void merge (String file1, String file2, String file3, String sasaran)throws IOException {
        FileInputStream input1 = null;
        FileInputStream input2 = null;
        FileInputStream input3 = null;
        FileOutputStream keluaran = null;
        try {
            input1 = new FileInputStream(file1);
            input2 = new FileInputStream(file2);
            input3 = new FileInputStream(file3);
            
            keluaran = new FileOutputStream(sasaran);
            int karakter = input1.read();
            while (karakter != -1) {
                keluaran.write(karakter);
                karakter = input1.read();
            }

            keluaran = new FileOutputStream(sasaran,true);
            karakter = input2.read();
            while (karakter != -1) {
                keluaran.write(karakter);
                karakter = input2.read();
            }

            keluaran = new FileOutputStream(sasaran,true);
            karakter = input3.read();
            while (karakter != -1) {
                keluaran.write(karakter);
                karakter = input3.read();
            }

            keluaran.flush();
        } 
        finally {
            if (input1 !=null)
                input1.close();
            if (input2 != null)
                input2.close();
            if (input3 != null)
                input3.close(); 
            if (keluaran != null)
                keluaran.close();

        }
    }
}