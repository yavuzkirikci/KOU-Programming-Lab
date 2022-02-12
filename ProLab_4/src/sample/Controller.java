package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class Controller {

    private void oyunuYukle() throws IOException {
        Main.stage.setX(100);
        Main.stage.setY(0);
        Main.stage.setScene(Main.scene2);
        Main.karakterSec();
        Main.karakterleriGoster();
        Main.AltintimerAyarla();
        Main.MantartimerAyarla();
        Main.puanGuncelle();
        Main.sureleriGuncelle();
        Main.enKisaMesafeHesapla();
        Main.baslangicDurum = false;
    }

    public void gozlukluSecildi(MouseEvent mouseEvent) throws IOException {
        Main.karakter = "gozluklu";
        oyunuYukle();
    }

    public void tembelSecildi(MouseEvent mouseEvent) throws IOException {
        Main.karakter = "tembel";
        oyunuYukle();
    }
}
