public class Kullanici extends Oyuncu{

    Kullanici(){
        super();
    }

    Kullanici(String ad, int id, int skor){
        super(id, ad, skor);
    }

    @Override
    public int skorGoster() {
        return getSkor();
    }

    @Override
    public Object kartSec(String tip) {
        for (int i = 0; i < 8; i++) {
            Object obj = kartListesi.get(i);
            if (obj instanceof Futbolcu){
                Futbolcu f = (Futbolcu) obj;
                if(f.getIsim().equals(tip)){
                    return obj;
                }
            }
            else {
                Basketbolcu b = (Basketbolcu) obj;
                if(b.getIsim().equals(tip)){
                    return obj;
                }
            }
        }

        return null;
    }
}
