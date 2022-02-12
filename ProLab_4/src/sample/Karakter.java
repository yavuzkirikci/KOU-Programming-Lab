package sample;

public class Karakter {
    private int id;
    private String ad;
    private String tur;
    private Lokasyon lokasyon;

    public Lokasyon getLokasyon() {
        return lokasyon;
    }

    public void setLokasyon(Lokasyon lokasyon) {
        this.lokasyon = lokasyon;
        this.lokasyon.IDHesapla();
    }

    public Karakter(){
        id = -1; ad = ""; tur = "";
        lokasyon = new Lokasyon(0, 0);

    }

    public Karakter(int id_, String ad_, String tur_, int x, int y){
        id = id_; ad = ad_; tur = tur_;
        lokasyon = new Lokasyon(x, y);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public void enKisaYol(){

    }
}
