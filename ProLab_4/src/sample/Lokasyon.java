package sample;

public class Lokasyon {
    private int x;
    private int y;
    private int ID;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        IDHesapla();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        IDHesapla();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void IDHesapla(){
        setID(getY() * 13 + getX());
    }

    Lokasyon(int x, int y){
        setX(x);
        setY(y);
        IDHesapla();
    }
}
