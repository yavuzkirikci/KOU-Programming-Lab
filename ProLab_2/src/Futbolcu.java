public class Futbolcu extends Sporcu {

    private String FutbolcuAdi;
    private String FutbolcuTakim;
    private int penalti;
    private int serbestVurus;
    private int kaleciyleKarsiKarsiya;
    private boolean kartKullanildiMi;



    public Futbolcu(){
        super();
        setFutbolcuAdi(super.getIsim());
        setFutbolcuTakim(super.getTakim());
        setKartKullanildiMi(false);
    }

    public Futbolcu(String isim, String takim, int a, int b, int c){
        super(isim, takim);
        setFutbolcuAdi(super.getIsim());
        setFutbolcuTakim(super.getTakim());
        setKartKullanildiMi(false);
        setPenalti(a);
        setSerbestVurus(b);
        setKaleciyleKarsiKarsiya(c);
    }

    @Override
    public int SporcuPuaniGoster(String tip) {
        if (tip.equals("Penaltı"))
            return getPenalti();
        else if(tip.equals("Serbest Vuruş"))
            return getSerbestVurus();
        return getKaleciyleKarsiKarsiya();
    }

    public int getPenalti() {
        return penalti;
    }

    public void setPenalti(int penalti) {
        this.penalti = penalti;
    }

    public int getSerbestVurus() {
        return serbestVurus;
    }

    public void setSerbestVurus(int serbestVurus) {
        this.serbestVurus = serbestVurus;
    }

    public int getKaleciyleKarsiKarsiya() {
        return kaleciyleKarsiKarsiya;
    }

    public void setKaleciyleKarsiKarsiya(int kaleciyleKarsiKarsiya) {
        this.kaleciyleKarsiKarsiya = kaleciyleKarsiKarsiya;
    }

    public String getFutbolcuAdi() {
        return FutbolcuAdi;
    }

    public void setFutbolcuAdi(String futbolcuAdi) {
        FutbolcuAdi = futbolcuAdi;
    }

    public String getFutbolcuTakim() {
        return FutbolcuTakim;
    }

    public void setFutbolcuTakim(String futbolcuTakim) {
        FutbolcuTakim = futbolcuTakim;
    }

    public boolean isKartKullanildiMi() {
        return kartKullanildiMi;
    }

    public void setKartKullanildiMi(boolean kartKullanildiMi) {
        this.kartKullanildiMi = kartKullanildiMi;
    }
}
