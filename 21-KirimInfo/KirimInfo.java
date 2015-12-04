import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;

public class KirimInfo
{
    public static void main(String[] args) throws UnknownHostException, IOException 
    {
        String info = "M REKI ANDIKA (1408107010001)";
        String IPAddress = "192.168.43.139";
        Socket koneksi = new Socket(IPAddress, 33333);
        OutputStream keluaran = koneksi.getOutputStream();
        Writer keluaranWriter = new OutputStreamWriter(keluaran);
        keluaranWriter.write(info);
        keluaranWriter.write("\r\n");
        keluaranWriter.flush();
        koneksi.close();
    }
}
