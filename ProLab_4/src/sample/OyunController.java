package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class OyunController {

    public Canvas canvas;
    public AnchorPane anchor;
    public ImageView karakterImage;



    private int ID(int x, int y){
        if(x < 0 || y < 0 || x > 12 || y > 10)
            return -1;
        return (y * 13) + (x);
    }

    private void komsulariKontrolEt(){
        for (int i = 0; i < Main.kareler.size(); i++) {
            OyunKaresi kare = Main.kareler.get(i);
            if(!kare.getAvailable())
                continue;
            ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(ID(kare.getX() - 1, kare.getY()));
            a.add(ID(kare.getX(), kare.getY() - 1));
            a.add(ID(kare.getX() + 1, kare.getY()));
            a.add(ID(kare.getX(), kare.getY() + 1));
            for(int x : a){
                if (x == -1)
                    continue;
                if (Main.kareler.get(x).getAvailable())
                    kare.komsular.add(x);
            }
        }
    }

    private void yazdir(){
        for (int i = 0; i < Main.kareler.size(); i++) {
            System.out.println(Main.kareler.get(i).komsular);
        }
    }

    private void cizgiCiz(){
        for (int i = 0; i <= 11; i++) {
            Line line = new Line(0, i*60, 780, i*60);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(1);
            anchor.getChildren().add(line);
        }
        for (int i = 0; i <= 13; i++) {
            Line line = new Line(i*60, 0, i*60, 660);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(1);
            anchor.getChildren().add(line);
        }
    }


    private void haritaOku() throws FileNotFoundException {
        File file;

        file = new File(System.getProperty("user.dir") + "\\src\\harita.txt");

        Scanner scanner = new Scanner(file);

        int id = 0;

        while(scanner.hasNextLine()){
            String s = scanner.nextLine();
            if (s.charAt(0) != 'K') {
                String[] parts = s.split("");
                for(String part : parts){
                    OyunKaresi kare = new OyunKaresi();
                    if (part.equals("0")){
                        kare.setAvailable(false);
                        canvas.getGraphicsContext2D().setFill(Color.DARKGRAY);
                        canvas.getGraphicsContext2D().fillRect((  (id % 13) * 60), ( (id / 13) * 60 ), 60, 60);
                    }
                    else if(part.equals("1")){
                        kare.setAvailable(true);
                        canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
                        canvas.getGraphicsContext2D().fillRect((  (id % 13) * 60), ( (id / 13) * 60 ), 60, 60);
                    }
                    else{
                        continue;
                    }
                    kare.setId(id);
                    kare.setXY();
                    Main.kareler.add(kare);
                    id++;
                }
            }
            else{
                String karakter = s.split(",")[0].split(":")[1];
                String kapi = s.split(",")[1].split(":")[1];
                if(karakter.equals("Gargamel")){
                    Gargamel gargamel = new Gargamel();
                    gargamel.setLokasyon(new Lokasyon(Main.kapiDon(kapi).getX(), Main.kapiDon(kapi).getY()));
                    gargamel.setBaslangicKapisi(kapi);
                    gargamel.setImage(new ImageView("sample/gargamel.png"));
                    gargamel.setImageXY();
                    gargamel.getImage().setFitWidth(60);
                    gargamel.getImage().setFitHeight(60);
                    gargamel.getImage().setPreserveRatio(false);
                    Main.gargamels.add(gargamel);
                    anchor.getChildren().add(gargamel.getImage());
                }
                else{
                    Azman azman = new Azman();
                    azman.setLokasyon(new Lokasyon(Main.kapiDon(kapi).getX(), Main.kapiDon(kapi).getY()));
                    azman.setBaslangicKapisi(kapi);
                    azman.setImage(new ImageView("sample/azman.png"));
                    azman.setImageXY();
                    azman.setBaslangicKapisi(kapi);
                    azman.getImage().setFitWidth(60);
                    azman.getImage().setFitHeight(60);
                    azman.getImage().setPreserveRatio(false);
                    Main.azmans.add(azman);
                    anchor.getChildren().add(azman.getImage());
                }
            }
        }
    }


    public void initialize() throws FileNotFoundException {
        Main.kareler = new ArrayList<>();
        Main.gargamels = new ArrayList<>();
        Main.azmans = new ArrayList<>();
        haritaOku();
        komsulariKontrolEt();
        cizgiCiz();
    }

}
