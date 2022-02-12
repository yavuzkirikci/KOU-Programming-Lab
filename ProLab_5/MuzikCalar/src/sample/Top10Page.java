package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class Top10Page {
    public AnchorPane anchor;

    private void sayfaYukle(String sayfa){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(sayfa));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void turTop10(MouseEvent mouseEvent) {
        sayfaYukle("turTop10.fxml");
    }

    public void ulkeTop10(MouseEvent mouseEvent) {
        sayfaYukle("ulkeTop10.fxml");

    }

    public void genelTop10(MouseEvent mouseEvent) {
        sayfaYukle("genelTop10.fxml");
    }

    public void geriDon(MouseEvent mouseEvent) {
        sayfaYukle("normalPage.fxml");
    }
}
