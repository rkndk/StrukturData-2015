import java.io.FileWriter;
import java.io.IOException;

public class CariPrima {
    public static void main() throws IOException {
        // Buat berkas untuk menulis hasil. Pakai WRITER karena yang ditulis 
        // berkas text.
        FileWriter berkas = new FileWriter(NAMA_BERKAS);

        // Buat array dari thread
        BenarPrima[] benarPrima = new BenarPrima[JUMLAH_THREAD];
        // Mulai hitung dari angka 2, karena 1 otomatis bukan prima
        int angka = 2;
        // Loop sampai batas atas yang diminta
        while (angka<=ANGKA_TERBESAR) {
            ///////////////////////////////////////////////////////////////////
            // MASUKKAN KODE ANDA DI BAWAH INI

            int counter;
            //membuat elemen threads dan menjalankannya
            for (counter=0; counter<JUMLAH_THREAD && angka<=ANGKA_TERBESAR; ++counter, ++angka) {
                benarPrima[counter] = new BenarPrima(angka);
                new Thread(benarPrima[counter]).start();
            }

            // Menulis ke file jika telah selesai cek apakah bilangan prima
            counter=0;
            while(counter<JUMLAH_THREAD)
            {
                //System.out.printf("Angka "+benarPrima[counter].angka()+" sedang di cek\n");
                //jika thread telah selesai dijalankan
                if(benarPrima[counter].selesai() == true){
                    if(benarPrima[counter].prima() == true)
                    {
                        synchronized(berkas) {
                            try {
                                berkas.write(""+benarPrima[counter].angka()+"\n");
                                //Menampilkan pada terminal bilangan prima yang telah berhasil ditulis
                                System.out.printf("Bilangan prima "+benarPrima[counter].angka()+" berhasil ditulis ke file "+NAMA_BERKAS+"\n");
                            }
                            catch (IOException kesalahan) {
                                System.out.printf("Terjadi kesalahan: %s", kesalahan);
                            }
                        }
                    }
                    if(benarPrima[counter].angka()==angka-1)
                        break;
                    counter++;
                }
                try
                {
                    Thread.sleep(3);
                }
                catch(InterruptedException e)
                {
                }
            }

            // ALGORITMA-nya:
            //   Untuk setiap elemen dari benarPrima (=thread yang kita buat)
            //       Jika elemen-nya belum dibuat
            //           Buatkan thread-nya
            //           Simpan thread tersebut di benarPrima
            //       Jika sudah selesai dihitung
            //           Jika benar bilangan prima
            //               Tulis ke berkas. Note: berkas perlu Synchronized
            //               Buang thread yang sudah selesai berhitung
            //               Buat thread baru dengan angka yang baru
            //               Simpan thread tersebut di benarPrima
            //           Jika bukan bilangan prima
            //               abaikan

            // MASUKKAN KODE ANDA DI ATAS INI
            ///////////////////////////////////////////////////////////////////            
        }

        // Tunggu sampai semua thread selesai
        for (int counterThread=0; counterThread<JUMLAH_THREAD; ++counterThread)
            while (benarPrima[counterThread].selesai() == false) { }        

        // Tutup berkas untuk menulis hasil
        berkas.close();
    }

    private final static String NAMA_BERKAS = "prima.log";
    private final static int JUMLAH_THREAD = 10;
    private final static int ANGKA_TERBESAR = 100000;
}