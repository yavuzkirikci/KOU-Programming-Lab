package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {
    public static Statement st = null;
    public static Connection conn = null;
    public static String mode = null;
    public static int duzenlenecekSarkiID = 0;
    public static int duzenlenecekSanatciID = 0;
    public static int duzenlenecekAlbumID = 0;
    public static String duzenlenecekAlbum = null;
    public static String duzenelenecekSanatci = null;
    public static int girisYapanKullanici = 0;
    public static Boolean premiumMode = false;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    private static void connectDatabase(){
        String url = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "database";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Can not Found...");
        }

        try {
            conn = DriverManager.getConnection(url,"root","");
        } catch (SQLException throwables) {
            System.out.println("Connection Failed...");
        }
    }

    public static void main(String[] args) {
        connectDatabase();/*
        try {
            st = conn.createStatement();
            String sorgu = "select * from kullanicilar";
            ResultSet rs = st.executeQuery(sorgu);
            int index = 1;
            while (rs.next()){
                int a = rs.getInt(2);
                System.out.println(a);
                System.out.println();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

        launch(args);
    }
}
