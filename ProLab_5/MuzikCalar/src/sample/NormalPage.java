package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NormalPage {
    public AnchorPane anchor;
    public Text abonelikText;

    @FXML
    void initialize() throws SQLException {
        if (Main.premiumMode){
            String sorgu = String.format("select * from odemeler where kullaniciID = %d", Main.girisYapanKullanici);
            ResultSet rs = Main.st.executeQuery(sorgu);
            if (rs.next()){
                if (rs.getInt("odendiMi") == 1)
                    abonelikText.setText("Abonelik : Premium (Ödendi)");
                else
                    abonelikText.setText("Abonelik : Premium (Ödenmedi)");
            }
        }
        else{
            abonelikText.setText("Abonelik : Normal");
        }
    }

    private void sayfaYukle(String sayfa){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(sayfa));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void kullaniciTakipEt(MouseEvent mouseEvent) {
        sayfaYukle("kullaniciTakip.fxml");
    }

    public void calmaListesiDinle(MouseEvent mouseEvent) {
        sayfaYukle("listeGoruntule.fxml");
    }

    public void muzikDinle(MouseEvent mouseEvent) {
        sayfaYukle("sarkilar.fxml");
    }

    public void cikisYap(MouseEvent mouseEvent) {
        sayfaYukle("login.fxml");
    }

    public void top10Clicked(MouseEvent mouseEvent) {
        sayfaYukle("top10Page.fxml");
    }
}
