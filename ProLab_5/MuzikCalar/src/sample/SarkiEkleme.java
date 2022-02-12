package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SarkiEkleme {
    public TextField album;
    public TextField tarih;
    public TextField ad;
    public TextField tur;
    public TextField sure;
    public TextField sayi;
    @FXML
    TextField sanatci;
    @FXML
    private AnchorPane anchor;

    int sanatciID;
    int albumID;
    String turAdi;

    private void geriDon(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean sarkiciyiKontrolEt(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from sanatcilar where sanatciAdi = '%s'", sanatci.getText());
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                sanatciID = rs.getInt("sanatciID");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private Boolean albumuKontrolEt(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from albumler where albumAdi = '%s'", album.getText());
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                turAdi = rs.getString("tür");
                albumID = rs.getInt("albumID");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private Boolean turKontrol(){
        return tur.getText().equals(turAdi);
    }

    private Boolean bosAlanVarMi(){
        return tarih.getText().trim().isEmpty() || ad.getText().trim().isEmpty()  || sure.getText().trim().isEmpty()
                || sayi.getText().trim().isEmpty() ;
    }

    public void ekleClicked(MouseEvent mouseEvent) {
        sanatciID = -1;
        if(!sarkiciyiKontrolEt()){
            System.out.println("Lütfen Sisteme Önce Sanatciyi Ekleyin");
        }
        else if(!albumuKontrolEt()){
            System.out.println("Seçtiğiniz sanatçının böyle bir albümü bulunmamakta, lütfen önce albümü ekleyin");
        }
        else if(!turKontrol()){
            System.out.println("Seçtiğiniz albüm farklı tür şarkılar içermektedir.");
        }
        else if(bosAlanVarMi()){
            System.out.println("Lütfen boş bıraktığınız alanları doldurun");
        }
        else{
            System.out.println("İşlem başarılı");
            // INSERT INTO `sarkilar` (`sarkiId`, `sarkiAdi`, `tarih`, `sanatciID`, `albumID`, `tur`, `sure`, `dinlenmeSayisi`) VALUES (NULL, 'A', 'b', '10', '10', 'd', 'f', '12');
            String sorgu =
                    String.format("INSERT INTO sarkilar (sarkiId, sarkiAdi, tarih, sanatciID, albumID, tur, sure, dinlenmeSayisi) VALUES (NULL, '%s', '%s', %d, %d, '%s', '%s', %d)",
                            ad.getText(), tarih.getText(), sanatciID, albumID, tur.getText(), sure.getText(), Integer.parseInt(sayi.getText()));
            try {
                Main.st = Main.conn.createStatement();
                Main.st.executeUpdate(sorgu);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            geriDon();


        }
    }


    public void iptalClicked(MouseEvent mouseEvent) {
        geriDon();
    }
}
