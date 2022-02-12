import java.util.Random;
public class Bilgisayar extends Oyuncu {

    @Override
    public int skorGoster() {
        return getSkor();
    }

    @Override
    public Object kartSec(String tip) {
        Random rand = new Random();
        while(true){
            int a = rand.nextInt(8);
            Object obj = kartListesi.get(a);
            if (tip.equals("Futbolcu")){
                if (obj instanceof Futbolcu){
                    Futbolcu f = (Futbolcu) obj;
                    if (!f.isKartKullanildiMi()) {
                        f.setKartKullanildiMi(true);
                        return obj;
                    }
                }
            }
            else if (tip.equals("Basketbolcu")){
                if (obj instanceof Basketbolcu){
                    Basketbolcu b = (Basketbolcu) obj;
                    if (!b.isKartKullanildiMi()){
                        b.setKartKullanildiMi(true);
                        return obj;
                    }
                }
            }
        }
    }


    Bilgisayar(){
        super();
    }

    Bilgisayar(String ad, int id, int skor){
        super(id, ad, skor);
    }

}
