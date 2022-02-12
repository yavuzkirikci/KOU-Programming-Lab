package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TurTop10 {
    public AnchorPane anchor;
    public MenuButton menuButton;
    public MenuItem popItem;
    public MenuItem jazzItem;
    public MenuItem klasikItem;
    public ScrollPane scroll;
    public Text dinlenilenSarki;

    int dinlenilenSarkiID;
    int listeID;

    @FXML
    void initialize(){
        popItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    listeYukle("Pop");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        jazzItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    listeYukle("Jazz");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        klasikItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    listeYukle("Klasik");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
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

    String albumAdBul(int albumID) throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from albumler where albumID = %d", albumID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        String albumAdi = " ";
        if (rs.next())
            albumAdi = rs.getString("albumAdi");
        return albumAdi;
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

    public int listeIDCek(String sarkiTuru) throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from listeler where tur = '%s' and kullaniciID = %d", sarkiTuru, Main.girisYapanKullanici);
        ResultSet rs = Main.st.executeQuery(sorgu);
        if (rs.next())
            return rs.getInt("listeID");
        return 0;
    }

    void listeYukle(String type) throws SQLException {
        Main.st = Main.conn.createStatement();

        listeID = listeIDCek(type);
        ArrayList<Integer> ids = new ArrayList<>();
        String sorgu = String.format("select * from sarkilar where tur = '%s' order by dinlenmeSayisi DESC", type);
        ResultSet rs = Main.st.executeQuery(sorgu);
        String isim = " ";
        int sayi = 0;
        while(sayi != 10 && rs.next()){
            ids.add(rs.getInt("sarkiID"));
            sayi += 1;
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

            Button button = new Button();
            button.setText("ÇAL");
            String ad = isim + " / " + albumAdi + " / " + sanatciAdi;
            String isim2 = isim;
            int sarki = sarkiID;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    dinlenilenSarki.setText("Dinlenilen Şarkı : " + isim2);
                    dinlenilenSarkiID = sarki;
                }
            });
            hBox.getChildren().addAll(new Text(ad), button);
            vbox.getChildren().add(hBox);
        }

        scroll.setContent(vbox);
    }

    public void sarkiEkle(MouseEvent mouseEvent) throws SQLException {
        sarkiEkleH(listeID, dinlenilenSarkiID);
    }

    public void menuDon(MouseEvent mouseEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("normalPage.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
