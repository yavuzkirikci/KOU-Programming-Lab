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
import java.sql.ResultSet;
import java.sql.SQLException;

public class SanatciDuzenle {
    public AnchorPane anchor;
    public MenuButton ulkeMenu;
    public MenuButton sanatciMenu;
    String secilenUlke;
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

    private void ulkeleriGoster(){
        //SELECT * FROM `sanatcilar` ORDER BY `sanatcilar`.`sanatciAdi` ASC
        try {
            Main.st = Main.conn.createStatement();
            String sorgu = "select MIN(sanatciID) AS  sanatciID, sanatciUlkesi from sanatcilar group by sanatciUlkesi ASC";
            ResultSet rs = Main.st.executeQuery(sorgu);
            while (rs.next()){
                String ad = rs.getString("sanatciUlkesi");
                MenuItem menuItem = new MenuItem(ad);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        secilenUlke = menuItem.getText();
                        ulkeMenu.setText(menuItem.getText());
                        sanatcilariGoster();
                    }
                });
                ulkeMenu.getItems().add(menuItem);
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
                Main.duzenlenecekSanatciID = sanatciID;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void sanatcilariGoster(){
        //SELECT * FROM `sanatcilar` ORDER BY `sanatcilar`.`sanatciAdi` ASC
        try {
            sanatciMenu.getItems().clear();
            sanatciIDBul();
            Main.st = Main.conn.createStatement();
            String sorgu = String.format("select * from sanatcilar where sanatciUlkesi = '%s' order by sanatciAdi ASC", secilenUlke);
            ResultSet rs = Main.st.executeQuery(sorgu);
            while (rs.next()){
                String ad = rs.getString("sanatciAdi");
                MenuItem menuItem = new MenuItem(ad);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        secilenSanatci = menuItem.getText();
                        sanatciMenu.setText(secilenSanatci);
                        sanatciIDBul();
                    }
                });
                sanatciMenu.getItems().add(menuItem);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @FXML
    void initialize(){
        ulkeleriGoster();
    }

    public void duzenleClicked(MouseEvent mouseEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("sanatciDuzenle2.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iptalClicked(MouseEvent mouseEvent) {
        geriDon();
    }
}
