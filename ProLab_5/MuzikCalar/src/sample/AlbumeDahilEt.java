package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumeDahilEt {
    public TextField album;
    public TextField sanatci;
    public AnchorPane anchor;

    int albumID;
    int sanatciID;

    public void albumIDBul(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from albumler where albumAdi = '%s'", album.getText());
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                albumID = rs.getInt("albumID");
            }
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

    public void sanatciIDBul(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from sanatcilar where sanatciAdi = '%s'", sanatci.getText());
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                sanatciID = rs.getInt("sanatciID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Boolean albumVarMi() throws SQLException {
        String sorgu;
        sorgu = String.format("select * from albumler where albumAdi = '%s'", album.getText());
        ResultSet rs = Main.st.executeQuery(sorgu);
        if (rs.next()){
            albumIDBul();
            return true;
        }
        return false;
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

    private Boolean sanatciAlbumdeVarMi() throws SQLException {
        Main.st = Main.conn.createStatement();
        String sorgu = String.format("select * from albumsanatci where sanatciID = %d and albumID = %d", sanatciID, albumID);
        ResultSet rs = Main.st.executeQuery(sorgu);
        return rs.next();
    }


    public void dahilEt(MouseEvent mouseEvent) throws SQLException {
        if (!albumVarMi()){
            System.out.println("Sistemde girdi??iniz isimde bir alb??m bulunmamakta");
        }
        else if (!sarkiciyiKontrolEt()){
            System.out.println("Sistemde girdi??iniz isimde bir sanat???? bulunmamakta");
        }
        else if (sanatciAlbumdeVarMi()){
            System.out.println("Sanat???? zaten bu alb??mde bulunmakta");
        }
        else{
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("insert into albumsanatci(id, albumID, sanatciID) values (NULL, %d, %d)", albumID, sanatciID);
            Main.st.executeUpdate(sorgu);
            geriDon();
        }
    }

    public void iptal(MouseEvent mouseEvent) {
        geriDon();
    }
}
