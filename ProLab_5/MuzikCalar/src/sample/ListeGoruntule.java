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

public class ListeGoruntule {
    public AnchorPane anchor;
    public MenuButton menuButton;
    public MenuItem popItem;
    public MenuItem jazzItem;
    public MenuItem klasikItem;
    public ScrollPane scroll;
    public Text dinlenilenSarki;

    int listeID;

    private void listeIDBul(String type) throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from listeler where tur = '%s' and kullaniciID = %d", type, Main.girisYapanKullanici);
        ResultSet rs = Main.st.executeQuery(sorgu);
        if (rs.next())
            listeID = rs.getInt("listeID");
    }

    private void geriDon(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("normalPage.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int listeIDCek(String sarkiTuru) throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from listeler where tur = '%s' and kullaniciID = %d", sarkiTuru, Main.girisYapanKullanici);
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

        int listeID = listeIDCek(type);
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

            Button button = new Button();
            button.setText("ÇAL");
            String ad = isim + " / " + albumAdi + " / " + sanatciAdi;
            String isim2 = isim;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    dinlenilenSarki.setText("Dinlenilen Şarkı : " + isim2);
                }
            });
            int sarki = sarkiID;
            Button button2 = new Button();
            button2.setText("SİL");
            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        Main.st = Main.conn.createStatement();
                        String sorgu = String.format("DELETE FROM listesarki WHERE listeID = %d and sarkiID = %d", listeID, sarki);
                        Main.st.executeUpdate(sorgu);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        listeYukle(type);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });

            hBox.getChildren().addAll(new Text(ad), button, button2);
            vbox.getChildren().add(hBox);
        }

        scroll.setContent(vbox);
    }

    void sarkilariYukle(String type) throws SQLException {
        listeYukle(type);
    }

    @FXML
    void initialize(){
        popItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                menuButton.setText("Pop");
                try {
                    sarkilariYukle("Pop");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    listeIDBul("Pop");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        jazzItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                menuButton.setText("Jazz");
                try {
                    sarkilariYukle("Jazz");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    listeIDBul("Jazz");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        klasikItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                menuButton.setText("Klasik");
                try {
                    sarkilariYukle("Klasik");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    listeIDBul("Klasik");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public void menuDon(MouseEvent mouseEvent) {
        geriDon();
    }
}
