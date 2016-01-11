import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataUser {
    // ID yang didaftar
    private String idUser;
    // Data perjalanan
    private ArrayList<Object[]> dataUser;

    public DataUser(String idUser){
        //simpan ID User
        this.idUser=idUser;
        //buat arraylist untuk menampung data setiap melangkah
        dataUser=new ArrayList<Object[]>();
        //atur koordinat dan waktu awal dimulai
        setData("0 0");
    }

    public void setData(String koordinat){
        Object[] data= new Object[2];
        data[0]=koordinat;
        data[1]=Calendar.getInstance().getTime();
        dataUser.add(data);
    }

    public String getID(){
        return idUser;
    }

    public Date getDate(String[] waktu){
        try{
            Calendar result = Calendar.getInstance();
            result.set( Calendar.HOUR_OF_DAY, Integer.parseInt(waktu[0]) );
            result.set( Calendar.MINUTE, Integer.parseInt(waktu[1]) );
            result.set( Calendar.SECOND, Integer.parseInt(waktu[2]) );
            return result.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    public int getLangkah(String waktuAwal, String waktuAkhir){
        int hasil=0;
        String[] timeAwal=waktuAwal.split("\\.");
        String[] timeAkhir=waktuAkhir.split("\\.");
        Date awal,akhir,current;

        awal=getDate(timeAwal);
        akhir=getDate(timeAkhir);

        if(awal!=null&&akhir!=null){
            for(int i=1; i<dataUser.size() ;i++){
                current=(Date)((dataUser.get(i))[1]);
                if(current.after(awal)&&current.before(akhir))
                    synchronized(this) {
                        hasil++;
                    }
                else if(current.after(akhir))
                    break;
            }
            return hasil;
        }
        else
            return -1;
    }

    public int getLangkah(){
        int hasil=0;
        for(int i=0; i<dataUser.size() ;i++){
            synchronized(this) {
                hasil++;
            }
        }
        return hasil-1;
    }

    public String getRute(String waktuAwal, String waktuAkhir){
        String hasil="";
        String[] timeAwal=waktuAwal.split("\\.");
        String[] timeAkhir=waktuAkhir.split("\\.");
        Date awal,akhir,current;

        awal=getDate(timeAwal);
        akhir=getDate(timeAkhir);
        if(awal!=null&&akhir!=null){
            for(int i=0; i<dataUser.size() ;i++){
                current=(Date)((dataUser.get(i))[1]);
                if(current.after(awal)&&current.before(akhir))
                    hasil+="("+(String)((dataUser.get(i))[0])+") ";
                else if(current.after(akhir))
                    break;
            }     
            return hasil;
        }
        else
            return null;
    }

    public String getRute(){
        String hasil="";
        for(int i=0; i<dataUser.size() ;i++){
            hasil+="("+(String)((dataUser.get(i))[0])+") ";
        }
        return hasil;
    }
}