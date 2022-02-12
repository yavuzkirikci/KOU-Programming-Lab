package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DuzenlemeEkrani {
    public AnchorPane anchor;

    public void eklemeClicked(MouseEvent mouseEvent) {
        if(Main.mode.equals("sarki")){
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("sarkiEkleme.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (Main.mode.equals("sanatci")){
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("sanatciEkleme.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("albumEkleme.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void silClicked(MouseEvent mouseEvent) {
        if(Main.mode.equals("sanatci")){
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("sanatciSil.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (Main.mode.equals("sarki")){
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("sarkiSil.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("albumSil.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void duzenleClicked(MouseEvent mouseEvent) {
        if(Main.mode.equals("sarki")){
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("sarkiDuzenle.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(Main.mode.equals("album")){
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("albumDuzenle3.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("sanatciDuzenle.fxml"));
                anchor.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
