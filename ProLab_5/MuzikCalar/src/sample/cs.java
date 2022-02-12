package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class cs {
    public AnchorPane anchor;

    public void sanatciClicked(MouseEvent mouseEvent) {
        Main.mode = "sanatci";
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("duzenlemeEkrani.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sarkiClicked(MouseEvent mouseEvent) {
        Main.mode = "sarki";
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("duzenlemeEkrani.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void albumClicked(MouseEvent mouseEvent) {
        Main.mode = "album";
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("duzenlemeEkrani.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cikisYap(MouseEvent mouseEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("login.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
