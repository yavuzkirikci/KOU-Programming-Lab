package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumdenCikar {
    public AnchorPane anchor;
    public TextField album;
    public TextField sanatci;

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

    public void cikar(MouseEvent mouseEvent) throws SQLException {
        if (!albumVarMi()){
            System.out.println("Girdiğiniz albüm sistemde bulunmamakta");
        }
        else if (!sarkiciyiKontrolEt()){
            System.out.println("Girdiğiniz sanatçı sistemde bulunmamakta");
        }
        else if (!sanatciAlbumdeVarMi()){
            System.out.println("Girdiğiniz sanatçı zaten bu albümde bulunmamakta");
        }
        else{
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("DELETE FROM albumsanatci WHERE albumID = %d and sanatciID = %d", albumID, sanatciID);
            Main.st.executeUpdate(sorgu);
            geriDon();
        }
    }

    public void iptal(MouseEvent mouseEvent) {
        geriDon();
    }
}
