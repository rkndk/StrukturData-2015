import java.io.IOException;

import java.util.Scanner;

public class UtamaClient {
    public static void main(String[] args) {
        try {
            Scanner keyboard = new Scanner(System.in);

            // Buat client untuk berhubungan dengan server
            Client client = new Client("localhost", 33333);

            // Baca perintah
            System.out.print("Perintah: ");
            String perintah = keyboard.nextLine();
            // Hilang spasi depan & belakang serta ubah ke huruf besar semua
            perintah = perintah.trim().toUpperCase();

            // Kirim perintah ke server
            String balasan = client.perintah(perintah);                
            // Jika ada balasan dari server, tampilkan
            if (balasan != null) {
                System.out.print("Server: ");
                System.out.println(balasan);
            }

            System.out.println();
        }
        catch(IOException salah) {
            System.out.println(salah);
        }
    }
}