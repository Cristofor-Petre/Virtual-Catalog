    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.awt.Panel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author PCristofor
 */
public class AdaugaTema extends Application {

    ArrayList<String> detaliiprofesor;
    String clasa;
    Conexiune c = new Conexiune();
    Button continua, cancel;
    Label temal, datapredarel;
    TextField tema, datapredare;
    BorderPane borderpane;
    VBox hbox = new VBox();
    Stage stage;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("VTeaching");
        primaryStage.getIcons().add(new Image("file:icon-book.png"));
        primaryStage.setHeight(300);
        primaryStage.setWidth(300);

        borderpane = new BorderPane();

        temal = new Label("Tema");
        datapredarel = new Label("Data Predare");
        continua = new Button("Continua");
        continua.setPrefWidth(primaryStage.getWidth());

        cancel = new Button("Cancel");
        cancel.setPrefWidth(primaryStage.getWidth());
        tema = new TextField();
        datapredare = new TextField();
        hbox.getChildren().addAll(temal, tema, datapredarel, datapredare, continua, cancel);
        hbox.setSpacing(15);
        hbox.setPadding(new Insets(10, 10, 10, 10));
        borderpane.setCenter(hbox);

        continua.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    updateTema();
                } catch (Exception ex) {
                    Logger.getLogger(Elev_Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                stage.close();
            }

        });
        Scene scene = new Scene(borderpane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    AdaugaTema(ArrayList<String> detaliiprofesor, String clasa) throws Exception {
        this.detaliiprofesor = detaliiprofesor;
        this.clasa = clasa;

        stage = new Stage();
        start(stage);

    }

    public void updateTema() throws SQLException {
        PreparedStatement pst;
        int id_materie = Integer.parseInt(detaliiprofesor.get(2));
        int id_clasa = 0;
        System.out.println(detaliiprofesor.get(2));

        String q2 = "select id_clasa from clasa where nume_clasa='" + clasa + "'";

        ResultSet rs2 = c.getConexiune(q2);

        try {

            if (rs2.next()) {
                id_clasa = rs2.getInt(1);
            }

            try {

                String query = "INSERT INTO TEMA VALUES(ID_TEMA.NEXTVAL,?,?,?,?,?)";

                String data = datapredare.getText();
                System.out.println(datapredare.getText());
                if (!data.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    throw new Exception();
                }

                System.out.println(id_materie + " " + id_clasa + " " + tema.getText() + " " + java.sql.Date.valueOf(java.time.LocalDate.now()) + " " + java.sql.Date.valueOf(data));
                pst = c.dmlConexiune(query);

                pst.setInt(1, id_materie);
                pst.setInt(2, id_clasa);
                pst.setString(3, tema.getText());
                pst.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
                pst.setDate(5, java.sql.Date.valueOf(data));

                pst.executeUpdate();
                stage.close();

            } catch (Exception ex) {
                Logger.getLogger(Editeaza.class.getName()).log(Level.SEVERE, null, ex);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Editeaza.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
