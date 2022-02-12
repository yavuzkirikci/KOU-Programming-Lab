package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumDuzenle2 {
    public AnchorPane anchor;
    public TextField ad;
    public TextField tarih;
    public TextField tur;

    @FXML
    void initialize(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from albumler where albumID = %d", Main.duzenlenecekAlbumID);
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                ad.setText(rs.getString("albumAdi"));
                tarih.setText(rs.getString("tarih"));
                tur.setText(rs.getString("tür"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void update(){
        String sorgu = String.format("update albumler set albumAdi = '%s' , tarih = '%s' , tür = '%s' where albumID = %d",
                ad.getText(), tarih.getText(), tur.getText(), Main.duzenlenecekAlbumID);
        try {
            Main.st = Main.conn.createStatement();
            Main.st.executeUpdate(sorgu);
            sorgu = String.format("update sarkilar set tur = '%s' , tarih = '%s' where albumID = %d",
                    tur.getText(), tarih.getText(), Main.duzenlenecekAlbumID);
            Main.st.executeUpdate(sorgu);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void geriDon(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean bosAlanVarMi(){
        return tarih.getText().trim().isEmpty() || ad.getText().trim().isEmpty()  || tur.getText().trim().isEmpty();
    }

    public void ekleClicked(MouseEvent mouseEvent) {
        if (bosAlanVarMi()){
            System.out.println("Lütfen boş alanları doldurun");
        }
        else{
            update();
            geriDon();
        }

    }

    public void iptalClicked(MouseEvent mouseEvent) {
        geriDon();
    }
}
