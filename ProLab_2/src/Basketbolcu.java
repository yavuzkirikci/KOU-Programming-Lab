public class Basketbolcu extends Sporcu {

    private String BasketbolcuAdi;
    private String BasketbolcuTakim;
    private int serbestAtis;
    private int ucluk;
    private int ikilik;
    private boolean kartKullanildiMi;

    public Basketbolcu(){
        super();
        setBasketbolcuAdi(super.getIsim());
        setBasketbolcuTakim(super.getTakim());
        setKartKullanildiMi(false);
    }

    public Basketbolcu(String isim, String takim, int a, int b, int c){
        super(isim, takim);
        setBasketbolcuAdi(super.getIsim());
        setBasketbolcuTakim(super.getTakim());
        setKartKullanildiMi(false);
        setUcluk(a);
        setIkilik(b);
        setSerbestAtis(c);
    }

    @Override
    public int SporcuPuaniGoster(String tip) {
        if (tip.equals("Üçlük"))
            return getUcluk();
        else if(tip.equals("Serbest Atış"))
            return getSerbestAtis();
        return getIkilik();
    }


    public String getBasketbolcuAdi() {
        return BasketbolcuAdi;
    }

    public void setBasketbolcuAdi(String basketbolcuAdi) {
        BasketbolcuAdi = basketbolcuAdi;
    }

    public String getBasketbolcuTakim() {
        return BasketbolcuTakim;
    }

    public void setBasketbolcuTakim(String basketbolcuTakim) {
        BasketbolcuTakim = basketbolcuTakim;
    }

    public int getSerbestAtis() {
        return serbestAtis;
    }

    public void setSerbestAtis(int serbestAtis) {
        this.serbestAtis = serbestAtis;
    }

    public int getUcluk() {
        return ucluk;
    }

    public void setUcluk(int ucluk) {
        this.ucluk = ucluk;
    }

    public int getIkilik() {
        return ikilik;
    }

    public void setIkilik(int ikilik) {
        this.ikilik = ikilik;
    }

    public boolean isKartKullanildiMi() {
        return kartKullanildiMi;
    }

    public void setKartKullanildiMi(boolean kartKullanildiMi) {
        this.kartKullanildiMi = kartKullanildiMi;
    }



}
