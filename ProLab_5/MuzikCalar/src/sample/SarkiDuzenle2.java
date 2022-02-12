package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SarkiDuzenle2 {

    public AnchorPane anchor;
    public TextField ad;
    public TextField tarih;
    public TextField sure;
    public TextField sayi;

    String baslangicAlbumu;

    @FXML
    void initialize(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from sarkilar where sarkiId = %d", Main.duzenlenecekSarkiID);
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                ad.setText(rs.getString("SarkiAdi"));
                tarih.setText(rs.getString("tarih"));
                sure.setText(rs.getString("sure"));
                sayi.setText(String.valueOf(rs.getInt("dinlenmeSayisi")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        baslangicAlbumu = Main.duzenlenecekAlbum;
    }

    private void update(){
        String sorgu = String.format("update sarkilar set sarkiAdi = '%s' , tarih = '%s' , sure = '%s' , dinlenmeSayisi = %d where sarkiId = %d",
                ad.getText(), tarih.getText(), sure.getText(), Integer.parseInt(sayi.getText()), Main.duzenlenecekSarkiID);
        try {
            Main.st = Main.conn.createStatement();
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
        return tarih.getText().trim().isEmpty() || ad.getText().trim().isEmpty()  || sure.getText().trim().isEmpty() || sayi.getText().trim().isEmpty();
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
