
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 *
 * @author valen
 */
public class Login extends Application {

    Button login;
    GridPane pane;
    Scene scene;
    TextField username;
    PasswordField password;
    Label usernamel, passwordl;
    Text welcome;
    HBox hbox1, hbox2;
    Carnet main;
    Menu menu;
    MenuItem itemmenua, itemmenub;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("VTeaching");
        primaryStage.getIcons().add(new Image("file:icon-book.png"));
        login = new Button("Conecteaza-te");
        //login.setStyle("-fx-background-color: #FFA500;");

        menu = new Menu("Menu");
        username = new TextField();
        
        password = new PasswordField();
        usernamel = new Label("CNP:");
        passwordl = new Label("Parola:");
        itemmenua = new MenuItem("Elev");
        itemmenub = new MenuItem("Profesor");

        welcome = new Text("Bun venit!");
        welcome.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        scene = new Scene(pane, 300, 275);

        pane.add(welcome, 0, 0, 2, 1);
        pane.add(usernamel, 0, 1);
        pane.add(username, 1, 1);
        pane.add(passwordl, 0, 2);
        pane.add(password, 1, 2);
        pane.setStyle("-fx-background-color: #F0F8FF;");
        hbox1 = new HBox(10);
        hbox2 = new HBox(10);
        hbox1.setAlignment(Pos.BOTTOM_RIGHT);
        hbox1.getChildren().add(login);
        hbox2.setAlignment(Pos.BOTTOM_LEFT);

        pane.add(login, 1, 4);

        primaryStage.setScene(scene);

        login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int gasit = 0;
                ArrayList<String> detalielev = new ArrayList<>();
                Conexiune c = new Conexiune();
                ResultSet rs = c.getConexiune("select * from elev");

                try {
                    while (rs.next()) {
                        if (rs.getString(3).equals(username.getText()) && password.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
                            
                            if(!password.getText().equals("")){

                            if (password.getText().substring(2, 4).equals(rs.getString(3).substring(1, 3)) && password.getText().substring(5, 7).equals(rs.getString(3).substring(3, 5)) && password.getText().substring(8).equals(rs.getString(3).substring(5, 7))) {
                                int i = 1;
                                //System.out.println("caca");
                                detalielev.add(rs.getString(2));
                                detalielev.add(rs.getString(3));
                                detalielev.add("" + rs.getInt(1));
                                detalielev.add("" + rs.getInt(5));
                                //System.out.println(detaliiprofesor);
                                new Elev_Main_Window(detalielev, rs.getInt(1));
                                primaryStage.close();
                                gasit = 1;
                            }
                            }

                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                ArrayList<String> detaliiprofesor = new ArrayList<>();
                Conexiune c1 = new Conexiune();
                ResultSet rs1 = c.getConexiune("select * from profesor");

                try {
                    while (rs1.next()) {
                        if (rs1.getString(3).equals(username.getText()) && password.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
                            
                            
                         
                            if (password.getText().substring(2, 4).equals(rs1.getString(3).substring(1, 3)) && password.getText().substring(5, 7).equals(rs1.getString(3).substring(3, 5)) && password.getText().substring(8).equals(rs1.getString(3).substring(5, 7))) {
                                int i = 1;
                                //System.out.println("caca");
                                detaliiprofesor.add(rs1.getString(2));
                                detaliiprofesor.add(rs1.getString(3));
                                detaliiprofesor.add(""+rs1.getInt(7));
                                detaliiprofesor.add("" + rs1.getDate(5));
                                System.out.println(detaliiprofesor);
                                new Profesor_Main_Window(detaliiprofesor, rs1.getInt(1));
                                primaryStage.close();
                                gasit = 1;

                            }
                         }

                        }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
