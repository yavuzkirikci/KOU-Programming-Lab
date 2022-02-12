package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumEkleme {
    public TextField tarih;
    public TextField tur;
    public TextField sanatciAdi;
    public TextField albumAdi;
    public AnchorPane anchor;

    int sanatciID;
    int albumID;
    String secilenAlbum;

    private void geriDon(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean sanatciKontrol(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from sanatcilar where sanatciAdi = '%s'", sanatciAdi.getText());
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

    public void albumIDBul(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from albumler where albumAdi = '%s'", secilenAlbum);
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                albumID = rs.getInt("albumID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void ekleClicked(MouseEvent mouseEvent) {

        if(sanatciKontrol()){
            try {

                Main.st = Main.conn.createStatement();
                String sorgu =
                        String.format("INSERT INTO albumler (albumID, albumAdi, tarih, tür) VALUES (NULL, '%s', '%s', '%s')",
                                albumAdi.getText(),tarih.getText(), tur.getText());
                Main.st.executeUpdate(sorgu);
                secilenAlbum = albumAdi.getText();
                albumIDBul();
                sorgu =
                        String.format("INSERT INTO albumsanatci (id, albumID, sanatciID) VALUES (NULL, %d , %d)",
                                albumID, sanatciID);
                Main.st.executeUpdate(sorgu);


                System.out.println("i");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            geriDon();
        }
        else{
            System.out.println("Sistemde girdiğiniz isimde bir sanatçı bulunmamakta lütfen önce sanatçıyı ekleyin");
        }

    }

    public void iptalClicked(MouseEvent mouseEvent) {
        geriDon();
    }
}
