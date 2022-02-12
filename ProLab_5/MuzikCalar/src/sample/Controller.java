package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {

    public AnchorPane anchor;
    public TextField kullanici;
    public PasswordField sifre;

    String adminEmail = "admin";
    String adminSifre = "admin";

    enum Yetki{
        ADMIN,
        PREMIUM,
        NORMAL,
        HATA
    }

    private Yetki girisKontrol(){
        try {
            Main.st = Main.conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (sifre.getText().equals(adminSifre) && kullanici.getText().equals(adminEmail))
            return Yetki.ADMIN;
        String sorgu = String.format("select * from kullanicilar where email = '%s' and sifre = '%s'", kullanici.getText(), sifre.getText());
        ResultSet rs = null;
        try {
            rs = Main.st.executeQuery(sorgu);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (rs.next()){
                String abonelik = rs.getString("abonelikTuru");
                Main.girisYapanKullanici = rs.getInt("kullaniciID");
                if (abonelik.equals("Premium"))
                    return Yetki.PREMIUM;
                return Yetki.NORMAL;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Yetki.HATA;
    }

    public void girisYap(MouseEvent mouseEvent) {
        Yetki yetki = girisKontrol();
        if (yetki == Yetki.HATA){
            System.out.println("email ve şifre uyuşmuyor.");
        }
        else if(yetki == Yetki.ADMIN){
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("adminPage.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (yetki == Yetki.PREMIUM){
            System.out.println("Premium hesaba giriş yapıldı");
            Main.premiumMode = true;
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("normalPage.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            Main.premiumMode = false;
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("normalPage.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
