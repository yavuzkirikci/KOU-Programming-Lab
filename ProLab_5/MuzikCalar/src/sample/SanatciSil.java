package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SanatciSil {
    public AnchorPane anchor;
    public TextField ad;

    int sanatciID;
    int albumID;

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
            String sorgu = String.format("select * from sanatcilar where sanatciAdi = '%s'", ad.getText());
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

    private ArrayList<Integer> getIDS(){
        try {
            Main.st = Main.conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String sorgu = String.format("select * from albumsanatci where sanatciID = %d", sanatciID);
        ResultSet rs = null;
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            rs = Main.st.executeQuery(sorgu);
            while (rs.next()){
                ids.add(rs.getInt("albumID"));
            }

            return ids;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Boolean albumKontrol(ArrayList<Integer> ids) throws SQLException {
        String sorgu;
        System.out.println(ids);
        for (int i : ids) {
                sorgu = String.format("select * from albumsanatci where albumID = %d", i);
                ResultSet rs = Main.st.executeQuery(sorgu);
                if (!rs.next()){
                    System.out.println("ii");
                    sorgu = String.format("DELETE FROM albumler WHERE albumID = %d", i);
                    Main.st.executeUpdate(sorgu);
                }

        }
        return false;
    }

    private void sanatciSil(){
        try {
            ArrayList<Integer> ids = getIDS();
            String sorgu = " ";
            sorgu = String.format("DELETE FROM sarkilar WHERE sanatciID = %d", sanatciID);
            Main.st.executeUpdate(sorgu);
            sorgu = String.format("DELETE FROM albumsanatci WHERE sanatciID = %d", sanatciID);
            Main.st.executeUpdate(sorgu);
            albumKontrol(ids);
            sorgu = String.format("DELETE FROM sanatcilar WHERE sanatciID = %d", sanatciID);
            Main.st.executeUpdate(sorgu);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void silClicked(MouseEvent mouseEvent) {
        if (!sarkiciyiKontrolEt()){
            System.out.println("Girdiğiniz sanatçı sistemde bulunmamakta");
        }
        else{
            sanatciSil();
            geriDon();
        }
    }

    public void iptalClicked(MouseEvent mouseEvent) {
        geriDon();
    }
}
