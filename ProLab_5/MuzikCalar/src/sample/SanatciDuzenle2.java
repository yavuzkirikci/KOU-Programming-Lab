package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SanatciDuzenle2 {
    public AnchorPane anchor;
    public TextField ad;
    public TextField ulke;


    @FXML
    void initialize(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from sanatcilar where sanatciID = %d", Main.duzenlenecekSanatciID);
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                ad.setText(rs.getString("sanatciAdi"));
                ulke.setText(rs.getString("sanatciUlkesi"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void update(){
        String sorgu = String.format("update sanatcilar set sanatciAdi = '%s' , sanatciUlkesi = '%s' where sanatciID = %d",
                ad.getText(), ulke.getText(), Main.duzenlenecekSanatciID);
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
        return ad.getText().trim().isEmpty()  || ulke.getText().trim().isEmpty();
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
