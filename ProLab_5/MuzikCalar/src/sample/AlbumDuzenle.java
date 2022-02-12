package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumDuzenle {
    public AnchorPane anchor;
    public MenuButton sanatciMenu;
    public MenuButton albumMenu;

    String secilenSanatci;
    int sanatciID;
    String secilenAlbum;
    int albumID;
    String secilenSarki;
    int sarkiID;

    private void geriDon(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sanatcilariGoster(){
        //SELECT * FROM `sanatcilar` ORDER BY `sanatcilar`.`sanatciAdi` ASC
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = "select * from sanatcilar order by sanatciAdi ASC";
            ResultSet rs = Main.st.executeQuery(sorgu);
            int index = 1;
            while (rs.next()){
                String ad = rs.getString("sanatciAdi");
                MenuItem menuItem = new MenuItem(ad);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        secilenSanatci = menuItem.getText();
                        sanatciMenu.setText(menuItem.getText());
                        albumleriGoster();
                        Main.duzenelenecekSanatci = secilenSanatci;
                    }
                });
                sanatciMenu.getItems().add(menuItem);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void sanatciIDBul(){
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from sanatcilar where sanatciAdi = '%s'", secilenSanatci);
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                sanatciID = rs.getInt("sanatciID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void albumleriGoster(){
        //SELECT * FROM `sanatcilar` ORDER BY `sanatcilar`.`sanatciAdi` ASC
        try {
            albumMenu.getItems().clear();
            sanatciIDBul();
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from albumSanatci where sanatciID = %d", sanatciID);
            ResultSet rs = Main.st.executeQuery(sorgu);
            ArrayList<Integer> ids = new ArrayList<>();
            while (rs.next()){
                ids.add(rs.getInt("albumID"));
            }
            if (rs.next()){
                int i = rs.getInt(1);
            }
            for (int i : ids) {
                sorgu = String.format("select * from albumler where albumID = %d", i);
                rs = Main.st.executeQuery(sorgu);
                String ad = " ";
                if (rs.next())
                    ad = rs.getString("albumAdi");
                MenuItem menuItem = new MenuItem(ad);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        secilenAlbum = menuItem.getText();
                        albumMenu.setText(secilenAlbum);
                        albumIDBul();
                        Main.duzenlenecekAlbumID = albumID;
                        Main.duzenlenecekAlbum = secilenAlbum;
                    }
                });
                albumMenu.getItems().add(menuItem);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    @FXML
    void initialize(){
        sanatcilariGoster();
    }

    public void duzenleClicked(MouseEvent mouseEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("albumDuzenle2.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iptalClicked(MouseEvent mouseEvent) {
        geriDon();
    }
}
