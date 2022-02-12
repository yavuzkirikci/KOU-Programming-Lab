import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Test test1 = new Test();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        ImageView kullanici_resim_1 = (ImageView) root.getScene().lookup("#kullanici_resim_1");
        ImageView kullanici_resim_2 = (ImageView) root.getScene().lookup("#kullanici_resim_2");
        ImageView kullanici_resim_3 = (ImageView) root.getScene().lookup("#kullanici_resim_3");
        ImageView kullanici_resim_4 = (ImageView) root.getScene().lookup("#kullanici_resim_4");
        ImageView kullanici_resim_5 = (ImageView) root.getScene().lookup("#kullanici_resim_5");
        ImageView kullanici_resim_6 = (ImageView) root.getScene().lookup("#kullanici_resim_6");
        ImageView kullanici_resim_7 = (ImageView) root.getScene().lookup("#kullanici_resim_7");
        ImageView kullanici_resim_8 = (ImageView) root.getScene().lookup("#kullanici_resim_8");


        Futbolcu f1 = (Futbolcu) Test.kullanıcı.kartListesi.get(0);
        Test.kullanici_kart_isimleri.add(f1.getFutbolcuAdi());
        Basketbolcu b1;
        String path = "images/"+f1.getFutbolcuAdi()+".png";
        kullanici_resim_1.setImage(new Image(new FileInputStream(path)));
        // , 171, 242, false, false
        f1 = (Futbolcu) Test.kullanıcı.kartListesi.get(1);
        path = "images/"+f1.getFutbolcuAdi()+".png";
        kullanici_resim_2.setImage(new Image(new FileInputStream(path), 150, 200, false, false));
        Test.kullanici_kart_isimleri.add(f1.getFutbolcuAdi());

        f1 = (Futbolcu) Test.kullanıcı.kartListesi.get(2);
        path = "images/"+f1.getFutbolcuAdi()+".png";
        kullanici_resim_3.setImage(new Image(new FileInputStream(path), 150, 200, false, false));
        Test.kullanici_kart_isimleri.add(f1.getFutbolcuAdi());

        f1 = (Futbolcu) Test.kullanıcı.kartListesi.get(3);
        path = "images/"+f1.getFutbolcuAdi()+".png";
        kullanici_resim_4.setImage(new Image(new FileInputStream(path),150, 200, false, false));
        Test.kullanici_kart_isimleri.add(f1.getFutbolcuAdi());

        b1 = (Basketbolcu) Test.kullanıcı.kartListesi.get(4);
        path = "images/"+b1.getBasketbolcuAdi()+".png";
        kullanici_resim_5.setImage(new Image(new FileInputStream(path),150, 200, false, false));
        Test.kullanici_kart_isimleri.add(b1.getBasketbolcuAdi());

        b1 = (Basketbolcu) Test.kullanıcı.kartListesi.get(5);
        path = "images/"+b1.getBasketbolcuAdi()+".png";
        kullanici_resim_6.setImage(new Image(new FileInputStream(path),150, 200, false, false));
        Test.kullanici_kart_isimleri.add(b1.getBasketbolcuAdi());

        b1 = (Basketbolcu) Test.kullanıcı.kartListesi.get(6);
        path = "images/"+b1.getBasketbolcuAdi()+".png";
        kullanici_resim_7.setImage(new Image(new FileInputStream(path),150, 200, false, false));
        Test.kullanici_kart_isimleri.add(b1.getBasketbolcuAdi());

        b1 = (Basketbolcu) Test.kullanıcı.kartListesi.get(7);
        path = "images/"+b1.getBasketbolcuAdi()+".png";
        kullanici_resim_8.setImage(new Image(new FileInputStream(path),150, 200, false, false));
        Test.kullanici_kart_isimleri.add(b1.getBasketbolcuAdi());

        f1 = (Futbolcu) Test.bilgisayar.kartListesi.get(0);
        Test.bilgisayar_kart_isimleri.add(f1.getFutbolcuAdi());

        f1 = (Futbolcu) Test.bilgisayar.kartListesi.get(1);
        Test.bilgisayar_kart_isimleri.add(f1.getFutbolcuAdi());

        f1 = (Futbolcu) Test.bilgisayar.kartListesi.get(2);
        Test.bilgisayar_kart_isimleri.add(f1.getFutbolcuAdi());

        f1 = (Futbolcu) Test.bilgisayar.kartListesi.get(3);
        Test.bilgisayar_kart_isimleri.add(f1.getFutbolcuAdi());

        b1 = (Basketbolcu) Test.bilgisayar.kartListesi.get(4);
        Test.bilgisayar_kart_isimleri.add(b1.getBasketbolcuAdi());

        b1 = (Basketbolcu) Test.bilgisayar.kartListesi.get(5);
        Test.bilgisayar_kart_isimleri.add(b1.getBasketbolcuAdi());

        b1 = (Basketbolcu) Test.bilgisayar.kartListesi.get(6);
        Test.bilgisayar_kart_isimleri.add(b1.getBasketbolcuAdi());

        b1 = (Basketbolcu) Test.bilgisayar.kartListesi.get(7);
        Test.bilgisayar_kart_isimleri.add(b1.getBasketbolcuAdi());

        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
