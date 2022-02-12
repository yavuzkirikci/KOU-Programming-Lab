import java.util.ArrayList;
import java.util.List;

public abstract class Oyuncu {
    private int oyuncuID;
    private String oyuncuAdi;
    private int Skor;
    public List<Object> kartListesi;

    public abstract int skorGoster();

    public abstract Object kartSec(String tip);

    Oyuncu(){
        setOyuncuAdi("?");
        setOyuncuID(-1);
        setSkor(0);
        kartListesi = new ArrayList<Object>();
    }

    Oyuncu(int id, String ad, int skor){
        setOyuncuID(id);
        setOyuncuAdi(ad);
        setSkor(skor);
        kartListesi = new ArrayList<Object>();
    }

    public int getOyuncuID() {
        return oyuncuID;
    }

    public void setOyuncuID(int oyuncuID) {
        this.oyuncuID = oyuncuID;
    }

    public String getOyuncuAdi() {
        return oyuncuAdi;
    }

    public void setOyuncuAdi(String oyuncuAdi) {
        this.oyuncuAdi = oyuncuAdi;
    }

    public int getSkor() {
        return Skor;
    }

    public void setSkor(int skor) {
        Skor = skor;
    }
}
