package sample;

import javafx.scene.image.ImageView;

public class Oyuncu extends Karakter {
    private int puan;

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }

    public Oyuncu(){
        super();
    }

    public Oyuncu(int id_, String ad_, String tur_, int x,int y, int puan_){
        super(id_, ad_, tur_, x, y);
        setPuan(puan_);
    }

    public int puaniGoster(){
        return getPuan();
    }

    public boolean yemKontrolY(Lokasyon lokasyon, int x, int y, int ekleme){
        if (lokasyon.getX() == x && lokasyon.getY() == y){
            if (Main.karakter.equals("gozluklu"))
                Main.gozlukluSirin.setCan(Main.gozlukluSirin.getCan() + ekleme);
            else
                Main.tembelSirin.setCan(Main.tembelSirin.getCan() + ekleme);
            Main.puanGuncelle();
            return true;
        }
        return false;
    }

    public void yemKontrol(Lokasyon lokasyon){
        for (Altin altin: Main.altinlar) {
            if (altin.isGecildiMi())
                continue;
            if(yemKontrolY(lokasyon, altin.getX(), altin.getY(), 5)){
                altin.setGecildiMi(true);
                altin.getImage().setVisible(false);
            }

        }
        for (Mantar mantar: Main.mantar) {
            if (mantar.isGecildiMi())
                continue;
            if(yemKontrolY(lokasyon, mantar.getX(), mantar.getY(), 50)){
                mantar.setGecildiMi(true);
                mantar.getImage().setVisible(false);
            }

        }
    }

    public void hareketEtY(String type){
        Lokasyon lokasyon = getLokasyon();

        if (lokasyon.getX() == 12 && lokasyon.getY() == 7 && type == "right"){
            Main.oyunSonlandir();
            return;
        }

        if (type.equals("left")){
            Lokasyon gidilen = new Lokasyon(lokasyon.getX() - 1, lokasyon.getY());
            if(lokasyon.getX() > 0 && Main.kareler.get(gidilen.getID()).getAvailable()){
                lokasyon.setX(lokasyon.getX() - 1);
            }
        }
        else if(type.equals("right")){
            Lokasyon gidilen = new Lokasyon(lokasyon.getX() + 1, lokasyon.getY());
            if(lokasyon.getX() < 12 && Main.kareler.get(gidilen.getID()).getAvailable()){
                lokasyon.setX(lokasyon.getX() + 1);
            }
        }
        else if(type.equals("down")){
            Lokasyon gidilen = new Lokasyon(lokasyon.getX(), lokasyon.getY() + 1);
            if (lokasyon.getY() < 10 && Main.kareler.get(gidilen.getID()).getAvailable()){
                lokasyon.setY(lokasyon.getY() + 1);
            }
        }
        else{
            Lokasyon gidilen = new Lokasyon(lokasyon.getX(), lokasyon.getY() - 1);
            if (lokasyon.getY() > 0 && Main.kareler.get(gidilen.getID()).getAvailable() ) {
                lokasyon.setY(lokasyon.getY() - 1);
            }
        }
        yemKontrol(lokasyon);
        lokasyon.IDHesapla();

    }


}
