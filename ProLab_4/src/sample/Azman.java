package sample;

import javafx.scene.image.ImageView;

public class Azman extends Dusman{
    private int adim;

    public String getBaslangicKapisi() {
        return baslangicKapisi;
    }

    public void setBaslangicKapisi(String baslangicKapisi) {
        this.baslangicKapisi = baslangicKapisi;
    }

    private String baslangicKapisi;

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setImageXY(){
        image.setX(getLokasyon().getX() * 60);
        image.setY(getLokasyon().getY() * 60);
    }

    public void setPos(int id){
        int x = id % 13;
        int y = id / 13;
        getLokasyon().setX(x);
        getLokasyon().setY(y);
        setImageXY();
    }

    private ImageView image;

    public Azman(){
        super();
    }

    public int getAdim() {
        return adim;
    }

    public void setAdim(int adim) {
        this.adim = adim;
    }

    public Azman(int id_, String ad_, String tur_, int x, int y){
        super(id_, ad_, tur_, x, y);
        setAdim(1);
    }
}
