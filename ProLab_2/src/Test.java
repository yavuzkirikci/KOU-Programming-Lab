import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class Test {
    // pozisyon sec fonksiyonu yaz !!!

    static Kullanici kullanıcı;
    static Bilgisayar bilgisayar;
    static boolean futbolcu_modu;
    static List<String> kullanici_kart_isimleri;
    static List<String> bilgisayar_kart_isimleri;
    static int futbolcu_kart_sayisi;
    static int basketbolcu_kart_sayisi;

    private void liste_kar(ArrayList<Integer> liste){
        Random rand = new Random();
        for (int i=0; i<1000; i++){
            int a = rand.nextInt(8);
            int b = rand.nextInt(8);
            int temp = liste.get(a);
            liste.set(a, liste.get(b));
            liste.set(b, temp);
        }
    }

    static String PozisyonSec(){
        Random rand = new Random();
        if (futbolcu_kart_sayisi != 0 && futbolcu_modu){
            String[] pozisyonlar = {"Penaltı", "Serbest Vuruş", "K.Karşıya"};
            int a = rand.nextInt(3);
            return pozisyonlar[a];
        }
        String[] pozisyonlar = {"Üçlük", "İkilik", "Serbest Atış"};
        int a = rand.nextInt(3);
        return pozisyonlar[a];
    }

    Test(){

        kullanıcı = new Kullanici();
        bilgisayar = new Bilgisayar();
        futbolcu_modu = true;
        kullanici_kart_isimleri = new ArrayList<String>();
        bilgisayar_kart_isimleri = new ArrayList<String>();
        futbolcu_kart_sayisi = 4;
        basketbolcu_kart_sayisi = 4;

        ArrayList<Integer> liste = new ArrayList<Integer>();
        for (int i = 0; i < 8; i++) {
            liste.add(i+1);
        }
        liste_kar(liste);
        System.out.println(liste);
        // PENALTI - SERBEST VURUS - KALECI
        Object obj1 = new Futbolcu("Mbappe", "PSG", 90, 90, 90);
        if (liste.indexOf(1) < 4)
            kullanıcı.kartListesi.add(obj1);
        else
            bilgisayar.kartListesi.add(obj1);

        Object obj2 = new Futbolcu("Kane","Tottenham", 85, 75, 90);
        if (liste.indexOf(2) < 4)
            kullanıcı.kartListesi.add(obj2);
        else
            bilgisayar.kartListesi.add(obj2);

        Object obj3 = new Futbolcu("Neymar", "PSG", 100, 90, 95);
        if (liste.indexOf(3) < 4)
            kullanıcı.kartListesi.add(obj3);
        else
            bilgisayar.kartListesi.add(obj3);

        Object obj4 = new Futbolcu("Salah","Liverpool", 95, 95, 95);
        if (liste.indexOf(4) < 4)
            kullanıcı.kartListesi.add(obj4);
        else
            bilgisayar.kartListesi.add(obj4);

        Object obj5 = new Futbolcu("Ronaldo","Juventus", 100, 100, 95);
        if (liste.indexOf(5) < 4)
            kullanıcı.kartListesi.add(obj5);
        else
            bilgisayar.kartListesi.add(obj5);
        Object obj6 = new Futbolcu("Messi","Barcelona", 100, 95, 100);

        if (liste.indexOf(6) < 4)
            kullanıcı.kartListesi.add(obj6);
        else
            bilgisayar.kartListesi.add(obj6);

        Object obj7 = new Futbolcu("Coutinho","Barcelona",80, 85, 95);
        if (liste.indexOf(7) < 4)
            kullanıcı.kartListesi.add(obj7);
        else
            bilgisayar.kartListesi.add(obj7);

        Object obj8 = new Futbolcu("Sterling","M.City", 90, 90, 85);
        if (liste.indexOf(8) < 4)
            kullanıcı.kartListesi.add(obj8);
        else
            bilgisayar.kartListesi.add(obj8);

        for (int i = 0; i < 8; i++) {
            liste.set(i, i+1);
        }
        liste_kar(liste);
        System.out.println(liste);

        obj1 = new Basketbolcu("George", "Clippers", 90, 90, 90);
        if (liste.indexOf(1) < 4)
            kullanıcı.kartListesi.add(obj1);
        else
            bilgisayar.kartListesi.add(obj1);

        obj2 = new Basketbolcu("Harden","Rockets", 85, 75, 90);
        if (liste.indexOf(2) < 4)
            kullanıcı.kartListesi.add(obj2);
        else
            bilgisayar.kartListesi.add(obj2);

        obj3 = new Basketbolcu("Durant", "Nets", 100, 90, 95);
        if (liste.indexOf(3) < 4)
            kullanıcı.kartListesi.add(obj3);
        else
            bilgisayar.kartListesi.add(obj3);

        obj4 = new Basketbolcu("Leonard","Clippers", 95, 95, 95);
        if (liste.indexOf(4) < 4)
            kullanıcı.kartListesi.add(obj4);
        else
            bilgisayar.kartListesi.add(obj4);

        obj5 = new Basketbolcu("James","Lakers", 100, 100, 95);
        if (liste.indexOf(5) < 4)
            kullanıcı.kartListesi.add(obj5);
        else
            bilgisayar.kartListesi.add(obj5);
        obj6 = new Basketbolcu("Curry","GSW", 100, 95, 100);

        if (liste.indexOf(6) < 4)
            kullanıcı.kartListesi.add(obj6);
        else
            bilgisayar.kartListesi.add(obj6);

        obj7 = new Basketbolcu("Irving","Nets",80, 85, 95);
        if (liste.indexOf(7) < 4)
            kullanıcı.kartListesi.add(obj7);
        else
            bilgisayar.kartListesi.add(obj7);

        obj8 = new Basketbolcu("Doncic","Dallas", 90, 90, 85);
        if (liste.indexOf(8) < 4)
            kullanıcı.kartListesi.add(obj8);
        else
            bilgisayar.kartListesi.add(obj8);



    }
}
