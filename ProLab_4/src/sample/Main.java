package sample;

import com.sun.prism.impl.shape.MarlinRasterizer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main extends Application {

    public static Canvas canvas;
    public static AnchorPane anchor;
    public static Stage stage;
    public static GozlukluSirin gozlukluSirin;
    public static TembelSirin tembelSirin;
    public static FXMLLoader fxmlLoader;
    public Scene scene1;
    public static Scene scene2;
    public Parent root;
    public Parent root2;
    public static ImageView karakterImage;
    public static String karakter = "";
    public static ArrayList<OyunKaresi> kareler;
    public static long elapsedTime;
    public static long elapsedTime2;
    public static long startTime;
    public static long startTime2;
    public static int randSec;
    public static int randSec2;
    public static boolean gorunurMod;
    public static boolean gorunurMod2;
    public static boolean gizliMod;
    public static boolean gizliMod2;
    public static Random random;
    public static Timer timer;
    public static Timer timer2;
    public static TimerTask timerTask;
    public static TimerTask timerTask2;
    public static ArrayList<Altin> altinlar;
    public static ArrayList<Mantar> mantar;
    public static ArrayList<Azman> azmans;
    public static ArrayList<Gargamel> gargamels;
    public static Lokasyon AKapisi;
    public static Lokasyon BKapisi;
    public static Lokasyon CKapisi;
    public static Lokasyon DKapisi;
    public static Text puanText;
    public static Text altinSure;
    public static Text mantarSure;
    public static Set<Integer> boyaliKareler;
    public static boolean baslangicDurum;
    public static int playerID;


    public static Lokasyon kapiDon(String kapiIsim){
        return switch (kapiIsim) {
            case "A" -> AKapisi;
            case "B" -> BKapisi;
            case "C" -> CKapisi;
            default -> DKapisi;
        };
    }

    public static void toplanabilirOlustur(String type){
        Random random = new Random();
        int x = random.nextInt(13);
        int y = random.nextInt(11);
        while(!lokasyonKontrol(x, y)){
            x = random.nextInt(13);
            y = random.nextInt(11);
        }
        ImageView imageView = new ImageView();
        imageView.setX(x * 60);
        imageView.setY(y * 60);
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        if (type.equals("altin")){
            imageView.setImage(new Image("sample/gold.png"));
            altinlar.add(new Altin(5, 5, 10));
            altinlar.get(altinlar.size() - 1).setImage(imageView);
        }
        else{
            imageView.setImage(new Image("sample/mantar.png"));
            mantar.add(new Mantar(50, 7, 20));
            mantar.get(0).setImage(imageView);
        }
            
        
        anchor.getChildren().add(imageView);
    }

    private static boolean lokasyonKarsilastir(Lokasyon a, Lokasyon b){
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    private static boolean lokasyonKontrol(int x,int y){
        Lokasyon lokasyon = new Lokasyon(x, y);
        if (lokasyonKarsilastir(lokasyon, AKapisi) || lokasyonKarsilastir(lokasyon, BKapisi) ||
                lokasyonKarsilastir(lokasyon, DKapisi) || lokasyonKarsilastir(lokasyon, CKapisi)
        )
            return  false;
        for (Altin altin: altinlar) {
            if (altin.getX() == lokasyon.getX() && altin.getY()== lokasyon.getY())
                return false;
        }
        for (Mantar mantar_: mantar) {
            if (mantar_.getX() == lokasyon.getX() && mantar_.getY()== lokasyon.getY())
                return false;
        }
        if (!kareler.get(lokasyon.getID()).getAvailable()){
            return false;
        }
        if (Main.karakter.equals("gozluklu")){
            if (x == Main.gozlukluSirin.getLokasyon().getX() && y == Main.gozlukluSirin.getLokasyon().getY())
                return false;
        }
        if (Main.karakter.equals("tembel")){
            if (x == Main.tembelSirin.getLokasyon().getX() && y == Main.tembelSirin.getLokasyon().getY())
                return false;
        }
        return true;
    }

    public static void altinlariSakla(){
        for (Altin altin:
             altinlar) {
            altin.setGecildiMi(true);
            altin.getImage().setVisible(false);
        }
    }

    public static void altinlariGoster(){
        for (Altin altin:
                altinlar) {
            altin.setGecildiMi(false);
            altin.getImage().setVisible(true);
        }
    }

    public static ArrayList<Integer> yerDegistir(){
        int x = random.nextInt(13);
        int y = random.nextInt(11);
        while(!lokasyonKontrol(x, y)){
            x = random.nextInt(13);
            y = random.nextInt(11);
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(x);
        arrayList.add(y);
        return arrayList;
    }

    public static void altinlarinYeriniDegistir(){
        for (Altin altin:
                altinlar) {
            ArrayList<Integer> arrayList = yerDegistir();
            altin.getImage().setX(arrayList.get(0) * 60);
            altin.getImage().setY(arrayList.get(1) * 60);
            altin.updateXY();
        }
    }
    
    public static void mantarGoster(boolean a){
        mantar.get(0).setGecildiMi(!a);
        mantar.get(0).getImage().setVisible(a);
    }

    public static void mantarYerDegistir(){
        ArrayList<Integer> arrayList = yerDegistir();
        mantar.get(0).getImage().setX(arrayList.get(0) * 60);
        mantar.get(0).getImage().setY(arrayList.get(1) * 60);
        mantar.get(0).updateXY();
    }

    public static void sureleriGuncelle(){
        if (gorunurMod)
            altinSure.setText(elapsedTime / 1000 + "");
        else
            altinSure.setText("");
        if (gorunurMod2)
            mantarSure.setText(elapsedTime2 / 1000 + "");
        else
            mantarSure.setText("");
    }

    public static void AltintimerAyarla(){
        timer = new Timer();
        startTime = System.currentTimeMillis();
        gorunurMod = true;
        for (int i = 0; i < 5; i++) {
            toplanabilirOlustur("altin");
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                elapsedTime = (new Date()).getTime() - startTime;
                Main.sureleriGuncelle();
                if(gorunurMod && (elapsedTime) > (5 * 1000)){
                    altinlariSakla();
                    altinlarinYeriniDegistir();

                    startTime = (new Date()).getTime();
                    gizliMod = true;
                    gorunurMod = false;
                    randSec = random.nextInt(11);
                }
                else if (gizliMod && (elapsedTime) > (randSec*1000)){
                    altinlariGoster();
                    startTime = (new Date()).getTime();
                    gorunurMod = true;
                    gizliMod = false;
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask, 10, 100);


    }

    public static void MantartimerAyarla(){
        timer2 = new Timer();
        startTime2 = System.currentTimeMillis();
        gorunurMod2 = true;
        toplanabilirOlustur("mantar");
        timerTask2 = new TimerTask() {
            @Override
            public void run() {
                elapsedTime2 = (new Date()).getTime() - startTime2;
                if(gorunurMod2 && (elapsedTime2) > (7 * 1000)){
                    mantarGoster(false);
                    mantarYerDegistir();

                    startTime2 = (new Date()).getTime();
                    gizliMod2 = true;
                    gorunurMod2 = false;
                    randSec2 = random.nextInt(21);
                }
                else if (gizliMod2 && (elapsedTime2) > (randSec2*1000)){
                    mantarGoster(true);
                    startTime2 = (new Date()).getTime();
                    gorunurMod2 = true;
                    gizliMod2 = false;
                }
            }
        };

        timer2.scheduleAtFixedRate(timerTask2, 10, 100);


    }

    public static void oyunSonlandir(){
        timer.cancel();
        timer2.cancel();
        Platform.exit();
    }

    public static void oyunBittiMi(){
        if (karakter.equals("gozluklu")){
            if (gozlukluSirin.getCan() <= 0){
                System.out.println("Oyunu kaybettin");
                oyunSonlandir();
            }
        }
        else{
            if (tembelSirin.getCan() <= 0){
                System.out.println("Oyunu kaybettin");
                oyunSonlandir();
            }
        }
    }

    public static void karakterSec(){
        if (Main.karakter.equals("gozluklu")){
            Main.gozlukluSirin = new GozlukluSirin(1, "Gozluklu Sirin", "gozluklu", 6, 5, 20);
            Main.karakterImage.setImage(new Image("sample/gozluklu_sirin.png"));
        }
        else{
            Main.tembelSirin = new TembelSirin(1, "Tembel Sirin", "tembel", 6, 5, 20);
            Main.karakterImage.setImage(new Image("sample/tembel_sirin.png"));
        }
    }

    public static void karakterleriGoster(){
        //if(karakterImage == null)
        //    return;
        if (Main.karakter.equals("gozluklu")){
            karakterImage.setLayoutX(Main.gozlukluSirin.getLokasyon().getX() * 60);
            karakterImage.setLayoutY(Main.gozlukluSirin.getLokasyon().getY() * 60);
        }
        else{
            karakterImage.setLayoutX(Main.tembelSirin.getLokasyon().getX() * 60);
            karakterImage.setLayoutY(Main.tembelSirin.getLokasyon().getY() * 60);
        }
    }

    public static void puanGuncelle(){
        if (karakter.equals("gozluklu"))
            puanText.setText(gozlukluSirin.getCan() + "");
        else
            puanText.setText(tembelSirin.getCan() + "");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        baslangicDurum = true;
        boyaliKareler = new HashSet<>();
        AKapisi = new Lokasyon(3, 0);
        BKapisi = new Lokasyon(10, 0);
        CKapisi = new Lokasyon(0, 5);
        DKapisi = new Lokasyon(3, 10);
        fxmlLoader = new FXMLLoader();
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root2 = FXMLLoader.load(getClass().getResource("oyun.fxml"));
        scene1 = new Scene(root, 640, 480);
        scene2 = new Scene(root2, 1080, 660);
        canvas = (Canvas) scene2.lookup("#canvas");
        karakterImage = (ImageView) scene2.lookup("#karakterImage");
        anchor = (AnchorPane) scene2.lookup("#anchor");
        puanText = (Text) scene2.lookup("#puanText");
        altinSure = (Text) scene2.lookup("#altinSure");
        mantarSure = (Text) scene2.lookup("#mantarSure");
        altinlar = new ArrayList<>();
        mantar = new ArrayList<>();
        random = new Random();


        stage = primaryStage;
        stage.setTitle("Hello World");
        stage.setScene(scene1);
        stage.show();

        scene2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String type = "";
                if (keyEvent.getCode() == KeyCode.LEFT){
                    type = "left";
                }
                else if (keyEvent.getCode() == KeyCode.UP){
                    type = "up";
                }
                else if (keyEvent.getCode() == KeyCode.DOWN){
                    type = "down";
                }
                else
                    type = "right";
                if (Main.karakter.equals("gozluklu")){
                    Main.gozlukluSirin.hareketEt(type);
                }
                else{
                    Main.tembelSirin.hareketEt(type);
                }
                karakterleriGoster();
            }
        });

        //canvas = (Canvas) root.getScene().lookup("#canvas");
        //PixelWriter pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
        //haritaOku();
    }

    public static void dijikstra(int dusmanKonumID, int playerID){
        ArrayList<Integer> mesafeler = new ArrayList<>();
        ArrayList<Boolean> ziyaretEdildiMi = new ArrayList<>();
        for (int i = 0; i < kareler.size(); i++) {
            mesafeler.add(Integer.MAX_VALUE);
            ziyaretEdildiMi.add(false);
        }
        mesafeler.set(dusmanKonumID, 0);

        for (int i = 0; i < kareler.size(); i++) {
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < kareler.size(); j++) {
                if (!ziyaretEdildiMi.get(j) && mesafeler.get(j) < min){
                    min = mesafeler.get(j);
                    minIndex = j;
                }
            }
            if (minIndex == -1)
                break;
            ziyaretEdildiMi.set(minIndex, true);

            for(int komsu : kareler.get(minIndex).komsular){
                if(mesafeler.get(komsu) > mesafeler.get(minIndex) + 1){
                    mesafeler.set(komsu, mesafeler.get(minIndex) + 1);
                    kareler.get(komsu).birOnceki = kareler.get(minIndex);
                }
            }


        }

    }

    public static void kareleriBoya(Color color){
        for(int id : boyaliKareler){
            canvas.getGraphicsContext2D().setFill(color);
            canvas.getGraphicsContext2D().fillRect((  (id % 13) * 60), ( (id / 13) * 60 ), 60, 60);
        }
        if (color == Color.WHITESMOKE)
            boyaliKareler.clear();
    }

    public static void kareBoya(Color color, int id){
        canvas.getGraphicsContext2D().setFill(color);
        canvas.getGraphicsContext2D().fillRect((  (id % 13) * 60), ( (id / 13) * 60 ), 60, 60);
    }

    public static void gargamelHareket(ArrayList<Integer> kisaYollar, Gargamel g, int j){
        g.setPos(kisaYollar.get(j));
        boyaliKareler.remove(kisaYollar.get(j+1));
        if (karakter.equals("gozluklu")){
            if (g.getLokasyon().getID() == gozlukluSirin.getLokasyon().getID()){
                String kapi = g.getBaslangicKapisi();
                g.setLokasyon(new Lokasyon(Main.kapiDon(kapi).getX(), Main.kapiDon(kapi).getY()));
                gozlukluSirin.getHit("Gargamel");
                g.setImageXY();
                oyunBittiMi();
            }

        }
        else{
            if (g.getLokasyon().getID() == tembelSirin.getLokasyon().getID()){
                String kapi = g.getBaslangicKapisi();
                g.setLokasyon(new Lokasyon(Main.kapiDon(kapi).getX(), Main.kapiDon(kapi).getY()));
                g.setImageXY();
                tembelSirin.getHit("Gargamel");
                oyunBittiMi();
            }
        }
    }

    public static void playerIDGuncelle(){
        if(karakter.equals("gozluklu")){
            playerID = gozlukluSirin.getLokasyon().getID();
        }
        else
            playerID = tembelSirin.getLokasyon().getID();
    }

    public static void enKisaMesafeHesapla(){

        kareleriBoya(Color.WHITESMOKE);

        playerIDGuncelle();


        for(Gargamel g : gargamels){
            int dusmanId = g.getLokasyon().getID();

            playerIDGuncelle();

            dijikstra(dusmanId, playerID);

            ArrayList<Integer> kisaYollar = new ArrayList<>();

            int i = playerID;
            boyaliKareler.add(i);
            kisaYollar.add(i);
            while (i != dusmanId){
                i = kareler.get(i).birOnceki.getId();
                boyaliKareler.add(i);
                kisaYollar.add(i);
            }

            if (baslangicDurum)
                continue;

            boolean geriDonulduMu = false;

            int j = kisaYollar.size() - 2;

            gargamelHareket(kisaYollar, g, j);
            playerIDGuncelle();
            if (g.getLokasyon().getID() != kapiDon(g.getBaslangicKapisi()).getID()){
                j--;
                gargamelHareket(kisaYollar, g, j);
                playerIDGuncelle();
            }


        }
        for (Azman a : azmans){
            int dusmanId = a.getLokasyon().getID();

            playerIDGuncelle();

            dijikstra(dusmanId, playerID);
            ArrayList<Integer> kisaYollar = new ArrayList<>();

            int i = playerID;
            boyaliKareler.add(i);
            kisaYollar.add(i);
            while (i != dusmanId){
                i = kareler.get(i).birOnceki.getId();
                kisaYollar.add(i);
                boyaliKareler.add(i);
            }

            if (baslangicDurum)
                continue;

            boolean dongudenCik = false;

            for (int j = kisaYollar.size() - 2; j > kisaYollar.size() - 3; j--) {
                // GARGAMEL KONTROL
                for (Gargamel g : gargamels){
                    if (g.getLokasyon().getID() == kisaYollar.get(j))
                        dongudenCik = true;
                }
                if (dongudenCik)
                    break;

                a.setPos(kisaYollar.get(j));
                boyaliKareler.remove(kisaYollar.get(j+1));
                if (karakter.equals("gozluklu")){
                    if (a.getLokasyon().getX() == gozlukluSirin.getLokasyon().getX() &&
                            a.getLokasyon().getY() == gozlukluSirin.getLokasyon().getY()){
                        String kapi = a.getBaslangicKapisi();
                        a.setLokasyon(new Lokasyon(Main.kapiDon(kapi).getX(), Main.kapiDon(kapi).getY()));
                        a.setImageXY();
                        gozlukluSirin.getHit("Azman");
                        oyunBittiMi();
                        break;
                    }

                }
                else{
                    if (a.getLokasyon().getX() == tembelSirin.getLokasyon().getX() &&
                            a.getLokasyon().getY() == tembelSirin.getLokasyon().getY()){
                        String kapi = a.getBaslangicKapisi();
                        a.setLokasyon(new Lokasyon(Main.kapiDon(kapi).getX(), Main.kapiDon(kapi).getY()));
                        a.setImageXY();
                        tembelSirin.getHit("Azman");
                        oyunBittiMi();
                        break;
                    }
                }

            }


        }
        Main.puanGuncelle();
        kareleriBoya(Color.TURQUOISE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
