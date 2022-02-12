package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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

public class Sarkilar {
    public ScrollPane scroll;
    public Text sanatciText;
    public Text albumText;
    public Text dinlenenSarkiText;

    public AnchorPane anchor;

    int dinleninlenAlbumID, dinlenilenSanatciID, dinlenilenSarkiID;

    private void geriDon(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("normalPage.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
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

    @FXML
    void initialize() throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from sarkilar order by sarkiAdi ASC");
        ResultSet rs = Main.st.executeQuery(sorgu);
        VBox vbox = new VBox();
        while(rs.next()){
            HBox hBox = new HBox();
            String isim = rs.getString("sarkiAdi");
            int sarkiID = rs.getInt("sarkiId");
            int albumID = rs.getInt("albumID");
            int sanatciID = rs.getInt("sanatciID");
            String sanatciAdi = sanatciAdBul(sanatciID);
            String albumAdi = albumAdBul(albumID);
            Button button = new Button();
            button.setText("ÇAL");
            String ad = isim + " / " + albumAdi + " / " + sanatciAdi;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    dinlenilenSanatciID = sanatciID;
                    dinlenilenSarkiID = sarkiID;
                    dinleninlenAlbumID = albumID;
                    System.out.println(dinlenilenSarkiID + " " + dinleninlenAlbumID + " " + dinlenilenSanatciID);
                    dinlenenSarkiText.setText("Dinlenilen Şarkı : " + isim);
                    sanatciText.setText("Sanatçı : " + new String(sanatciAdi));
                    albumText.setText("Albüm : " + new String(albumAdi));
                }
            });
            hBox.getChildren().addAll(new Text(ad), button);
            vbox.getChildren().add(hBox);
        }
        scroll.setContent(vbox);
    }

    public String sarkiTuruCek(int albumID) throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from albumler where albumID = %d", albumID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        if (rs.next())
            return rs.getString("tür");
        return " ";
    }

    public int listeIDCek(String sarkiTuru) throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from listeler where tur = '%s' and kullaniciID = %d", sarkiTuru, Main.girisYapanKullanici);
        ResultSet rs = Main.st.executeQuery(sorgu);
        if (rs.next())
            return rs.getInt("listeID");
        return 0;
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

    public void sarkiEkle(MouseEvent mouseEvent) throws SQLException {

        String sarkiTuru = sarkiTuruCek(dinleninlenAlbumID);
        int listeID = listeIDCek(sarkiTuru);

        sarkiEkleH(listeID, dinlenilenSarkiID);
    }

    public void albumEkle(MouseEvent mouseEvent) throws SQLException {
        String sarkiTuru = sarkiTuruCek(dinleninlenAlbumID);
        int listeID = listeIDCek(sarkiTuru);

        ArrayList<Integer> ids = new ArrayList<>();
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from sarkilar where albumID = %d", dinleninlenAlbumID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        while(rs.next())
            ids.add(rs.getInt("sarkiId"));
        for (int i : ids) {
            sarkiEkleH(listeID, i);
        }
    }

    public void sanatciEkle(MouseEvent mouseEvent) throws SQLException {

        String sarkiTuru = sarkiTuruCek(dinleninlenAlbumID);
        int listeID = listeIDCek(sarkiTuru);

        ArrayList<Integer> ids = new ArrayList<>();
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from sarkilar where sanatciID = %d", dinlenilenSanatciID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        while(rs.next())
            ids.add(rs.getInt("sarkiId"));
        for (int i : ids) {
            sarkiEkleH(listeID, i);
        }

    }

    public void menuDon(MouseEvent mouseEvent) {
        geriDon();
    }
}
