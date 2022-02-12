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
import java.util.logging.SocketHandler;

public class UlkeTop10 {
    public AnchorPane anchor;
    public MenuButton menuButton;
    public Text dinlenilenSarki;
    public ScrollPane scroll;

    @FXML
    void initialize(){
        ulkeleriGoster();

    }

    int listeID, dinlenilenSarkiID;

    private void ulkeleriGoster(){
        //SELECT * FROM `sanatcilar` ORDER BY `sanatcilar`.`sanatciAdi` ASC
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = "select MIN(sanatciID) AS  sanatciID, sanatciUlkesi from sanatcilar group by sanatciUlkesi ASC";
            ResultSet rs = Main.st.executeQuery(sorgu);
            while (rs.next()){
                String ad = rs.getString("sanatciUlkesi");
                MenuItem menuItem = new MenuItem(ad);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        menuButton.setText(ad);
                        try {
                            listeYukle();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
                menuButton.getItems().add(menuItem);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    Boolean sanatciKontrol(int sanatciID) throws SQLException {
        Main.st = Main.conn.createStatement();
        ArrayList<Integer> ids = new ArrayList<>();
        String sorgu = String.format("select * from sanatcilar where sanatciID = %d", sanatciID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        if (rs.next()){
            return rs.getString("sanatciUlkesi").equals(menuButton.getText());
        }
        return false;
    }

    void listeYukle() throws SQLException {
        Main.st = Main.conn.createStatement();

        ArrayList<Integer> ids = new ArrayList<>();
        String sorgu = String.format("select * from sarkilar order by dinlenmeSayisi DESC");
        ResultSet rs = Main.st.executeQuery(sorgu);
        String isim = " ";
        int sayi = 0;
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

            if (!sanatciKontrol(sanatciID))
                continue;
            sayi += 1;
            if (sayi == 11)
                break;

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
        String tur = " ";
        String sorgu = String.format("select * from sarkilar where sarkiId = %d", dinlenilenSarkiID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        if (rs.next())
            tur = rs.getString("tur");
        listeID = listeIDCek(tur);
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
