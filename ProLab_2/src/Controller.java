import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Controller {
    public Label kart1;
    public Label kart2;
    public ImageView bilgisayar_resim_1;
    public ImageView bilgisayar_resim_2;
    public ImageView bilgisayar_resim_3;
    public ImageView bilgisayar_resim_4;
    public Label bilgisayar_skor;
    public Label kullanici_skor;
    public Label pozisyon_label;
    public ImageView bilgisayar_resim_5;
    public ImageView bilgisayar_resim_6;
    public ImageView bilgisayar_resim_7;
    public ImageView bilgisayar_resim_8;
    public AnchorPane anchor_pane;
    ArrayList<String> sonPozisyonlar;

    public void initialize() throws FileNotFoundException {
        anchor_pane.setStyle("-fx-background-image: url(futbol_arkaplan.png);" +
                "-fx-background-size: 1360 720;");
        bilgisayar_resim_1.setImage(new Image(new FileInputStream("images/bos_futbolcu.png"),150, 200, false, false));
        bilgisayar_resim_2.setImage(new Image(new FileInputStream("images/bos_futbolcu.png"),150, 200, false, false));
        bilgisayar_resim_3.setImage(new Image(new FileInputStream("images/bos_futbolcu.png"),150, 200, false, false));
        bilgisayar_resim_4.setImage(new Image(new FileInputStream("images/bos_futbolcu.png"),150, 200, false, false));
        bilgisayar_resim_5.setImage(new Image(new FileInputStream("images/bos_basketbolcu.png"),150, 200, false, false));
        bilgisayar_resim_6.setImage(new Image(new FileInputStream("images/bos_basketbolcu.png"),150, 200, false, false));
        bilgisayar_resim_7.setImage(new Image(new FileInputStream("images/bos_basketbolcu.png"),150, 200, false, false));
        bilgisayar_resim_8.setImage(new Image(new FileInputStream("images/bos_basketbolcu.png"),150, 200, false, false));
        sonPozisyonlar = new ArrayList<>();
    }

    public void ResmiBuyut(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        ScaleTransition transition = new ScaleTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(imageView);
        transition.setToX(1.15);
        transition.setToY(1.15);
        transition.play();
    }

    public void ResmiKucult(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        ScaleTransition transition = new ScaleTransition();
        transition.setDuration(Duration.seconds(0.05));
        transition.setNode(imageView);
        transition.setToX(1);
        transition.setToY(1);
        transition.play();
    }

    public void bitisİslemleri(){
        if (Test.futbolcu_kart_sayisi == 0 && Test.basketbolcu_kart_sayisi == 0){
            System.out.println("Girdi");
            boolean kazanma_durumu = true, berabere = false;
            if (Test.kullanıcı.getSkor() < Test.bilgisayar.getSkor())
                kazanma_durumu = false;
            else if(Test.kullanıcı.getSkor() == Test.bilgisayar.getSkor())
                berabere = true;
            Alert mesaj = new Alert(Alert.AlertType.INFORMATION);
            mesaj.setTitle("SONUÇ");
            if(berabere)
                mesaj.setContentText("BERABERE !!!");
            else if(kazanma_durumu)
                mesaj.setContentText("KAZANDIN !!!");
            else
                mesaj.setContentText("MAĞLUBİYET !!!");
            mesaj.show();
            mesaj.setOnHiding(new EventHandler<DialogEvent>() {
                @Override
                public void handle(DialogEvent dialogEvent) {
                    Platform.exit();
                }
            });
        }
    }

    public void clicked(MouseEvent mouseEvent) throws InterruptedException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        String id = imageView.getId().toString().split("_", 3)[2];
        System.out.println();
        String oyuncu_adi = Test.kullanici_kart_isimleri.get(Integer.parseInt(id)-1);
        Object obj = Test.kullanıcı.kartSec(oyuncu_adi);

        if(Test.futbolcu_kart_sayisi != 0 && Test.futbolcu_modu){
            if(obj instanceof Basketbolcu)
                return;
        }
        else{
            if(obj instanceof Futbolcu)
                return;
        }
        String pozisyon = Test.PozisyonSec();

        if (Test.basketbolcu_kart_sayisi + Test.futbolcu_kart_sayisi == 1){
            boolean listede_var_mi;
            do{
                listede_var_mi = false;
                for (int i = 0; i < sonPozisyonlar.size(); i++) {
                    if(pozisyon == sonPozisyonlar.get(i)){
                        listede_var_mi = true;
                    }
                }
            }while(listede_var_mi);
        }

        pozisyon_label.setText(pozisyon);
        if(Test.futbolcu_kart_sayisi != 0 && Test.futbolcu_modu){
            System.out.println("Futbolcu modu");
            Futbolcu f = (Futbolcu) obj;
            Object obj2 = Test.bilgisayar.kartSec("Futbolcu");
            Futbolcu f2 = (Futbolcu) obj2;
            int puan1 = f.SporcuPuaniGoster(pozisyon);
            int puan2 = f2.SporcuPuaniGoster(pozisyon);
            if (puan1 != puan2)
                Test.futbolcu_kart_sayisi -= 1;
            System.out.println("Bilgisayarın seçtiği : " + f2.getFutbolcuAdi());
            ImageView imageView2;
            if (Test.bilgisayar_kart_isimleri.get(0).equals(f2.getFutbolcuAdi()))
                imageView2 = bilgisayar_resim_1;
            else if (Test.bilgisayar_kart_isimleri.get(1).equals(f2.getFutbolcuAdi()))
                imageView2 = bilgisayar_resim_2;
            else if (Test.bilgisayar_kart_isimleri.get(2).equals(f2.getFutbolcuAdi()))
                imageView2 = bilgisayar_resim_3;
            else
                imageView2 = bilgisayar_resim_4;

            double kart1_konumX = imageView.getX();
            double kart1_konumY = imageView.getY();
            double kart2_konumX = imageView2.getX();
            double kart2_konumY = imageView2.getY();
            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(0.3));
            double add_x2,add_y2;
            add_x2 = kart2.getLayoutX() - imageView2.getLayoutX();
            add_y2 = kart2.getLayoutY() - imageView2.getLayoutY();
            transition.setToX(add_x2);
            transition.setToY(add_y2);
            transition.setNode(imageView2);
            transition.play();

            TranslateTransition transition2 = new TranslateTransition();
            double add_x,add_y;
            add_x = kart1.getLayoutX() - imageView.getLayoutX();
            add_y = kart1.getLayoutY() - imageView.getLayoutY();
            transition2.setNode(imageView);
            transition2.setDuration(Duration.seconds(0.3));
            transition2.setToX(add_x);
            transition2.setToY(add_y);
            transition2.play();
            String path = "images/" + f2.getFutbolcuAdi() + ".png";
            System.out.println("Path : "+ path);
            try {
                imageView2.setImage(new Image(new FileInputStream(path),150, 200, false, false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            transition2.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int kullanici_puan = f.SporcuPuaniGoster(pozisyon);
                    int bilgisayar_puan = f2.SporcuPuaniGoster(pozisyon);
                    if (kullanici_puan > bilgisayar_puan){
                        Test.kullanıcı.setSkor(Test.kullanıcı.getSkor() + 10);
                        kullanici_skor.setText(Integer.toString(Test.kullanıcı.getSkor()));
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        imageView.setVisible(false);
                        imageView2.setVisible(false);
                    }
                    else if (kullanici_puan == bilgisayar_puan){
                        if (Test.basketbolcu_kart_sayisi + Test.futbolcu_kart_sayisi == 1){
                            // eğer son kalan kartlarda 3 özellik puanı da eşitse her iki oyuncuya puan eklenir.
                            // Program sonlanır.
                            if (f.getPenalti() == f2.getPenalti())
                                if (f.getKaleciyleKarsiKarsiya() == f2.getKaleciyleKarsiKarsiya())
                                    if (f.getSerbestVurus() == f2.getSerbestVurus()){
                                        Test.bilgisayar.setSkor(Test.bilgisayar.getSkor() + 10);
                                        bilgisayar_skor.setText(Integer.toString(Test.bilgisayar.getSkor()));
                                        Test.kullanıcı.setSkor(Test.kullanıcı.getSkor() + 10);
                                        kullanici_skor.setText(Integer.toString(Test.kullanıcı.getSkor()));
                                        Test.basketbolcu_kart_sayisi -= 1;
                                        Test.futbolcu_kart_sayisi -= 1;
                                        bitisİslemleri();
                                        return;
                                    }

                            sonPozisyonlar.add(pozisyon);
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        TranslateTransition transition3 = new TranslateTransition();
                        TranslateTransition transition4 = new TranslateTransition();
                        transition3.setToX(kart2_konumX);
                        transition3.setToY(kart2_konumY);
                        transition3.setNode(imageView2);
                        transition3.setDuration(Duration.seconds(1));
                        transition3.play();

                        transition4 = new TranslateTransition();
                        transition4.setToX(kart1_konumX);
                        transition4.setToY(kart1_konumY);
                        transition4.setNode(imageView);
                        transition4.setDuration(Duration.seconds(1));
                        transition4.play();

                        transition3.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                f.setKartKullanildiMi(false);
                                f2.setKartKullanildiMi(false);
                            }
                        });
                    }
                    else{
                        Test.bilgisayar.setSkor(Test.bilgisayar.getSkor() + 10);
                        bilgisayar_skor.setText(Integer.toString(Test.bilgisayar.getSkor()));
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        imageView.setVisible(false);
                        imageView2.setVisible(false);
                    }
                    bitisİslemleri();
                    anchor_pane.setStyle("-fx-background-image: url(basket_arkaplan.png);" +
                            "-fx-background-size: 1360 720;");
                }
            });

        }
        else{
            System.out.println("Basketbolcu modu");
            Basketbolcu b = (Basketbolcu) obj;
            Object obj2 = Test.bilgisayar.kartSec("Basketbolcu");
            Basketbolcu b2 = (Basketbolcu) obj2;
            int puan1 = b.SporcuPuaniGoster(pozisyon);
            int puan2 = b2.SporcuPuaniGoster(pozisyon);
            if (puan1 != puan2)
                Test.basketbolcu_kart_sayisi -= 1;
            System.out.println("Bilgisayarın seçtiği : " + b2.getBasketbolcuAdi());
            ImageView imageView2;
            if (Test.bilgisayar_kart_isimleri.get(4).equals(b2.getBasketbolcuAdi()))
                imageView2 = bilgisayar_resim_5;
            else if (Test.bilgisayar_kart_isimleri.get(5).equals(b2.getBasketbolcuAdi()))
                imageView2 = bilgisayar_resim_6;
            else if (Test.bilgisayar_kart_isimleri.get(6).equals(b2.getBasketbolcuAdi()))
                imageView2 = bilgisayar_resim_7;
            else
                imageView2 = bilgisayar_resim_8;

            double kart1_konumX = imageView.getX();
            double kart1_konumY = imageView.getY();
            double kart2_konumX = imageView2.getX();
            double kart2_konumY = imageView2.getY();
            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(0.3));
            double add_x2,add_y2;
            add_x2 = kart2.getLayoutX() - imageView2.getLayoutX();
            add_y2 = kart2.getLayoutY() - imageView2.getLayoutY();
            transition.setToX(add_x2);
            transition.setToY(add_y2);
            transition.setNode(imageView2);
            transition.play();

            TranslateTransition transition2 = new TranslateTransition();
            double add_x,add_y;
            add_x = kart1.getLayoutX() - imageView.getLayoutX();
            add_y = kart1.getLayoutY() - imageView.getLayoutY();
            transition2.setNode(imageView);
            transition2.setDuration(Duration.seconds(0.3));
            transition2.setToX(add_x);
            transition2.setToY(add_y);
            transition2.play();
            String path = "images/" + b2.getBasketbolcuAdi() + ".png";
            System.out.println("Path : "+ path);
            try {
                imageView2.setImage(new Image(new FileInputStream(path),150, 200, false, false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            transition2.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int kullanici_puan = b.SporcuPuaniGoster(pozisyon);
                    int bilgisayar_puan = b2.SporcuPuaniGoster(pozisyon);
                    if (kullanici_puan > bilgisayar_puan){
                        Test.kullanıcı.setSkor(Test.kullanıcı.getSkor() + 10);
                        kullanici_skor.setText(Integer.toString(Test.kullanıcı.getSkor()));
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        imageView.setVisible(false);
                        imageView2.setVisible(false);
                    }
                    else if (kullanici_puan == bilgisayar_puan){
                        if (Test.basketbolcu_kart_sayisi + Test.futbolcu_kart_sayisi == 1){

                            // eğer son kalan kartlarda 3 özellik puanı da eşitse her iki oyuncuya puan eklenir.
                            // Program sonlanır.
                            if (b.getIkilik() == b2.getIkilik())
                                if (b.getUcluk() == b2.getUcluk())
                                    if (b.getSerbestAtis() == b2.getSerbestAtis()){
                                        Test.bilgisayar.setSkor(Test.bilgisayar.getSkor() + 10);
                                        bilgisayar_skor.setText(Integer.toString(Test.bilgisayar.getSkor()));
                                        Test.kullanıcı.setSkor(Test.kullanıcı.getSkor() + 10);
                                        kullanici_skor.setText(Integer.toString(Test.kullanıcı.getSkor()));
                                        Test.basketbolcu_kart_sayisi -= 1;
                                        Test.futbolcu_kart_sayisi -= 1;
                                        bitisİslemleri();
                                        return;
                                    }

                            sonPozisyonlar.add(pozisyon);
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        TranslateTransition transition3 = new TranslateTransition();
                        TranslateTransition transition4 = new TranslateTransition();
                        transition3.setToX(kart2_konumX);
                        transition3.setToY(kart2_konumY);
                        transition3.setNode(imageView2);
                        transition3.setDuration(Duration.seconds(1));
                        transition3.play();

                        transition4 = new TranslateTransition();
                        transition4.setToX(kart1_konumX);
                        transition4.setToY(kart1_konumY);
                        transition4.setNode(imageView);
                        transition4.setDuration(Duration.seconds(1));
                        transition4.play();

                        transition3.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                b.setKartKullanildiMi(false);
                                b2.setKartKullanildiMi(false);
                            }
                        });
                    }
                    else{
                        Test.bilgisayar.setSkor(Test.bilgisayar.getSkor() + 10);
                        bilgisayar_skor.setText(Integer.toString(Test.bilgisayar.getSkor()));
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        imageView.setVisible(false);
                        imageView2.setVisible(false);
                    }
                    bitisİslemleri();
                    anchor_pane.setStyle("-fx-background-image: url(futbol_arkaplan.png);" +
                            "-fx-background-size: 1360 720;");
                }
            });
        }
        Test.futbolcu_modu = !Test.futbolcu_modu;

        System.out.println(Test.futbolcu_kart_sayisi + "  " + Test.basketbolcu_kart_sayisi);

    }
}
