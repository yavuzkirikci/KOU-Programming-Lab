package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class SanatciEkleme {
    public TextField ulke;
    public TextField ad;
    @FXML
    private AnchorPane anchor;

    private void geriDon(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ekleClicked() {
        String sorgu =
                String.format("INSERT INTO sanatcilar (sanatciID, sanatciAdi, sanatciUlkesi) VALUES (NULL, '%s', '%s')",
                        ad.getText(), ulke.getText());
        try {
            Main.st = Main.conn.createStatement();
            Main.st.executeUpdate(sorgu);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        geriDon();

    }

    public void iptalClicked(MouseEvent mouseEvent) {
        geriDon();
    }
}
