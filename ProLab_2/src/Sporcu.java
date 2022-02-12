public abstract class Sporcu {
    private String isim;
    private String takim;

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getTakim() {
        return takim;
    }

    public void setTakim(String takim) {
        this.takim = takim;
    }

    public Sporcu(){
        isim = "?";
        takim = "?";
    }

    public Sporcu(String isim, String takim){
        this.isim = isim;
        this.takim = takim;
    }

    public abstract int SporcuPuaniGoster(String tip);

}
