package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SarkiSil {
    public AnchorPane anchor;
    public TextField album;
    public TextField sanatci;
    public TextField sarki;

    int sanatciID;
    String turAdi;
    int albumID;
    int sarkiID;

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
                albumID = rs.getInt("albumID");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private Boolean sarkiKontrol(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from sarkilar where sanatciID = %d and albumID = '%d' and sarkiAdi = '%s'",
                    sanatciID, albumID, sarki.getText());
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                sarkiID = rs.getInt("sarkiId");
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public void silClicked(MouseEvent mouseEvent) {
        if (!sarkiciyiKontrolEt()){
            System.out.println("Girdi??iniz sanat???? sistemde bulunmamakta l??tfen ge??erli bir ??ark??c?? girin");
        }
        else if(!albumuKontrolEt()){
            System.out.println("Sanat????n??n b??yle bir alb??m?? bulunmamakta");
        }
        else if(!sarkiKontrol()){
            System.out.println("Girdi??iniz alb??mde b??yle bir ??ark?? bulunmamakta");
        }
        else{
            try {
                Main.st = Main.conn.createStatement();
                String sorgu = String.format("DELETE FROM sarkilar WHERE sarkiId = %d", sarkiID);
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
