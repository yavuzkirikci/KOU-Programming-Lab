package sample;

public class GozlukluSirin extends Oyuncu{

    private int adim;
    private int gargamelHit;
    private int can;

    public int getAdim() {
        return adim;
    }

    public int getCan() {
        return can;
    }

    public void setCan(int can) {
        this.can = can;
    }

    public void setAdim(int adim) {
        this.adim = adim;
    }

    public int getGargamelHit() {
        return gargamelHit;
    }

    public void setGargamelHit(int gargamelHit) {
        this.gargamelHit = gargamelHit;
    }

    public int getAzmanHit() {
        return azmanHit;
    }

    public void setAzmanHit(int azmanHit) {
        this.azmanHit = azmanHit;
    }

    private int azmanHit;

    public GozlukluSirin(){
        super();
    }

    public GozlukluSirin(int id_, String ad_, String tur_, int x, int y, int puan_){
        super(id_, ad_, tur_, x, y, puan_);
        setAdim(2);
        setGargamelHit(15);
        setAzmanHit(5);
        setCan(20);
    }

    public void getHit(String dusmanTipi){
        if (dusmanTipi == "Gargamel"){
            setCan(getCan() - getGargamelHit());
        }
        else{
            setCan(getCan() - getAzmanHit());
        }
    }

    public void hareketEt(String type){
        for (int i = 0; i < adim; i++) {
            hareketEtY(type);
        }
        Main.enKisaMesafeHesapla();
    }
}
