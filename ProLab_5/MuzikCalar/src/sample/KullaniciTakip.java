package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KullaniciTakip {
    public AnchorPane anchor;
    public TextField ad;
    public MenuButton menuButton;
    public MenuItem popItem;
    public MenuItem jazzItem;
    public MenuItem klasikItem;
    public ScrollPane scroll;

    int kullaniciID;
    int listeID;
    String tur;

    void listeYukleH(){
        menuButton.setText(tur);
        try {
            listeYukle(tur);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    @FXML
    void initialize(){
        popItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (!kullaniciVarMi()){
                        System.out.println("Sistemde girdiğiniz isimde Premium bir kullanıcı bulunmamaktadır");
                    }
                    else{
                        tur = "Pop";
                        listeYukleH();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        jazzItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (!kullaniciVarMi()){
                        System.out.println("Sistemde girdiğiniz isimde Premium bir kullanıcı bulunmamaktadır");
                    }
                    else{
                        tur = "Jazz";
                        listeYukleH();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        klasikItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (!kullaniciVarMi()){
                        System.out.println("Sistemde girdiğiniz isimde Premium bir kullanıcı bulunmamaktadır");
                    }
                    else{
                        tur = "Klasik";
                        listeYukleH();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public int listeIDCek(String sarkiTuru, int kullaniciID) throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from listeler where tur = '%s' and kullaniciID = %d", tur, kullaniciID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        if (rs.next())
            return rs.getInt("listeID");
        return 0;
    }

    String albumAdBul(int albumID) throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from albumler where albumID = %d", albumID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        String albumAdi = " ";
        if (rs.next())
            albumAdi = rs.getString("albumAdi");
        return albumAdi;
    }

    public void sarkiEkleH(int listeID, int id) throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from listesarki where listeID = '%d' and sarkiID = %d", listeID, id);
        ResultSet rs = Main.st.executeQuery(sorgu);
        if (!rs.next()){
            Main.st = Main.conn.createStatement();
            sorgu = String.format("insert into listesarki(id, listeID, sarkiID) values (NULL, %d, %d)", listeID, id);
            Main.st.executeUpdate(sorgu);
        }
    }

    String sanatciAdBul(int sanatciID) throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from sanatcilar where sanatciID = %d", sanatciID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        String sanatciAdi = " ";
        if (rs.next())
            sanatciAdi = rs.getString("sanatciAdi");
        return sanatciAdi;
    }

    void listeYukle(String type) throws SQLException {
        Main.st = Main.conn.createStatement();

        listeID = listeIDCek(type, kullaniciID);

        ArrayList<Integer> ids = new ArrayList<>();
        String sorgu = String.format("select * from listesarki where listeID = %d", listeID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        String isim = " ";
        while(rs.next()){
            ids.add(rs.getInt("sarkiID"));
        }
        VBox vbox = new VBox();
        for (int i: ids) {
            HBox hBox = new HBox();
            sorgu = String.format("select * from sarkilar where sarkiId = %d", i);
            rs = Main.st.executeQuery(sorgu);
            int sarkiID = 0, albumID = 0, sanatciID = 0;
            if (rs.next()){
                isim = rs.getString("sarkiAdi");
                sarkiID = rs.getInt("sarkiId");
                albumID = rs.getInt("albumID");
                sanatciID = rs.getInt("sanatciID");
            }

            String sanatciAdi = sanatciAdBul(sanatciID);
            String albumAdi = albumAdBul(albumID);

            String ad = isim + " / " + albumAdi + " / " + sanatciAdi;

            Button button = new Button();
            button.setText("Ekle");
            String isim2 = isim;
            int sarki = sarkiID;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int listeID2 = 0;
                    try {
                        listeID2 = listeIDCek(tur, Main.girisYapanKullanici);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        sarkiEkleH(listeID2, sarki);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

            hBox.getChildren().addAll(new Text(ad), button);
            vbox.getChildren().add(hBox);
        }

        scroll.setContent(vbox);
    }

    public void takipEt(MouseEvent mouseEvent) throws SQLException {
        if (!kullaniciVarMi()){
            System.out.println("Sistemde girdiğiniz isimde Premium bir kullanıcı bulunmamaktadır");
        }
        else{
            // şu anki kullanıcının liste ID'si
            int listeID2 = listeIDCek(tur, Main.girisYapanKullanici);
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from listesarki where listeID = %d", listeID);
            ResultSet rs = Main.st.executeQuery(sorgu);
            while(rs.next()){
                int sarkiID = rs.getInt("sarkiID");

                sarkiEkleH(listeID2, sarkiID);
            }
            GeriDon();
        }
    }

    private Boolean kullaniciVarMi() throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from kullanicilar where kullanıcıAdı = '%s' and abonelikTuru = 'Premium'", ad.getText());
        ResultSet rs = Main.st.executeQuery(sorgu);
        if (rs.next()){
            kullaniciID = rs.getInt("kullanıcıID");
            return true;
        }
        return false;
    }

    public void GeriDon() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("normalPage.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
