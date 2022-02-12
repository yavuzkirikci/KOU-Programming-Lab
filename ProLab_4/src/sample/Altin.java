package sample;

import javafx.scene.image.ImageView;

public class Altin extends Obje {
    private int puan;
    private int durmaSuresi;
    private int maxOlusmaSuresi;
    private ImageView image;
    private boolean gecildiMi;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int x,y;

    public boolean isGecildiMi() {
        return gecildiMi;
    }

    public void setGecildiMi(boolean gecildiMi) {
        this.gecildiMi = gecildiMi;
    }

    public ImageView getImage() {
        return image;
    }

    public void updateXY(){
        setX((int)image.getX() / 60);
        setY((int)image.getY() / 60);
    }

    public void setImage(ImageView image) {
        this.image = image;
        updateXY();
    }

    public Altin(){
        setPuan(0); setDurmaSuresi(0); setMaxOlusmaSuresi(0); setGecildiMi(false);
        image = new ImageView();
    }

    public Altin(int x, int y, int z){
        setPuan(5); setDurmaSuresi(5); setMaxOlusmaSuresi(10); setGecildiMi(false);
        image = new ImageView();
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }

    public int getDurmaSuresi() {
        return durmaSuresi;
    }

    public void setDurmaSuresi(int durmaSuresi) {
        this.durmaSuresi = durmaSuresi;
    }

    public int getMaxOlusmaSuresi() {
        return maxOlusmaSuresi;
    }

    public void setMaxOlusmaSuresi(int maxOlusmaSuresi) {
        this.maxOlusmaSuresi = maxOlusmaSuresi;
    }


}
