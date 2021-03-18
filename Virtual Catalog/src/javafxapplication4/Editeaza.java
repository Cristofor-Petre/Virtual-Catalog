/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.awt.Panel;
import static java.lang.Boolean.FALSE;
import static java.lang.Compiler.disable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javax.swing.text.DateFormatter;

/**
 *
 * @author PCristofor
 */
public class Editeaza extends Application {

    Conexiune c = new Conexiune();
    PreparedStatement pst;
    String numeelev;
    String specializare;
    Stage stage;
    Button addnota, addabsenta, motivareAbsenta;
    TextField nota, absentatf;
    Label data, modeldata;
    BorderPane butonae_border;
    Panel p1;
    VBox butoane;
    Scene scene;
    MenuBar meniu;
    Menu tfls, help;
    MenuItem deconecteazate, about;
    Image imagine = new Image("file:icon-book.png");
    int idelev;

    public void start(Stage primaryStage) {
        primaryStage.setTitle("VTeaching");
        primaryStage.getIcons().add(new Image("file:icon-book.png"));
        primaryStage.setHeight(510);
        primaryStage.setWidth(310);
        stage.getIcons().add(imagine);
        butoane = new VBox();
        butoane.setPrefWidth(250);

        butonae_border = new BorderPane();
        butonae_border.setPrefHeight(primaryStage.getHeight());
        butonae_border.setPrefWidth(primaryStage.getWidth());
        butonae_border.setStyle("-fx-background-color: #F0F8FF;");
        nota = new TextField();

        absentatf = new TextField();
        

        absentatf.setPrefWidth(butoane.getPrefWidth());
        absentatf.setPromptText("yyyy-mm-dd");
        
        motivareAbsenta = new Button("Motivare Absenta");
        motivareAbsenta.setPrefWidth(butoane.getPrefWidth());
        
        data = new Label("" + java.time.LocalDate.now());
        nota.setPrefWidth(butoane.getPrefWidth());
        modeldata = new Label("ex: 2018-08-01");

        meniu = new MenuBar();
        meniu.setPrefWidth(primaryStage.getWidth());
        meniu.setStyle("-fx-background-color: #F0F8FF;");
        tfls = new Menu("VTeaching");
        help = new Menu("Help");

        deconecteazate = new MenuItem("Inchide");
        about = new MenuItem("About");
        meniu.getMenus().addAll(tfls, help);
        tfls.getItems().addAll(deconecteazate);
        help.getItems().addAll(about);

        addnota = new Button("Adauga Nota");
        addnota.setPrefWidth(butoane.getPrefWidth());
        addabsenta = new Button("Adauga Absenta");
        addabsenta.setPrefWidth(butoane.getPrefWidth());

        butoane.getChildren().addAll(nota, addnota, absentatf, motivareAbsenta,addabsenta);
        butoane.setAlignment(Pos.CENTER);
        butonae_border.setCenter(butoane);
        butonae_border.setTop(meniu);
        butonae_border.setMargin(butoane, new Insets(30, 30, 30, 30));
        butoane.setSpacing(15);
        scene = new Scene(butonae_border);
        deconecteazate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }

        });

        addnota.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                int idcatalog = 0;
                int idmaterie = 0;
                System.out.println(numeelev);
                String q0 = "select idelev from elev where nume='" + numeelev + "'";
                ResultSet rs3 = c.getConexiune(q0);
                try {
                    if (rs3.next()) {
                        idelev = rs3.getInt(1);
                        System.out.println(idelev);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Editeaza.class.getName()).log(Level.SEVERE, null, ex);
                }

                String q1 = "select distinct elev.clasa from elev\n"
                        + "where idelev =" + idelev;
                ResultSet rs = c.getConexiune(q1);
                String q2 = "select distinct id_materie from materie\n"
                        + "where denumire='" + specializare + "'";
                System.out.println(specializare);
                System.out.println(idelev);
                ResultSet rs1 = c.getConexiune(q2);
                try {
                    if (rs.next()) {
                        idcatalog = rs.getInt(1);
                    }

                    if (rs1.next()) {
                        idmaterie = rs1.getInt(1);
                    }

                    try {
                        int notaa = Integer.parseInt(nota.getText());
                        if (notaa > 10 || notaa <= 0) {
                            throw new NumberFormatException();
                        }
                        /*
                    String query = "Insert into student values (?, ?)" ;
                   PreparedStatement pst = con.prepareStatement(query) ;
                   pst.setInt(1, s.rollnumber);
                   pst.setString(2, s.sname);
                   pst.executeUpdate() ;
                         */

                        String query = "insert into catalog values(?,?,?,?,?,?)";

                        try {
                            pst = c.dmlConexiune(query);
                            System.out.println("idcatalog: " + idcatalog + "\nidelev: " + idelev);
                            pst.setInt(1, idcatalog);

                            pst.setInt(2, idelev);
                            pst.setInt(3, idmaterie);
                            pst.setInt(4, notaa);
                            String datal = data.getText();

                            pst.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
                            pst.setInt(6, 1);
                            pst.executeUpdate();
                            primaryStage.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Editeaza.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Warning Dialog");
                        alert.setHeaderText("Avertizare");
                        alert.setContentText("Introduceti o nota intre 1 si 10!");

                        alert.showAndWait();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Editeaza.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        addabsenta.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmare");
                alert.setHeaderText("Doriti sa continuati?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    int idcatalog = 0;
                    int idmaterie = 0;
                    String q0 = "select idelev from elev where nume='" + numeelev + "'";
                    ResultSet rs3 = c.getConexiune(q0);
                    try {
                        if (rs3.next()) {
                            idelev = rs3.getInt(1);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Editeaza.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String q1 = "select distinct id_catalogp from catalog\n"
                            + "where id_elev =" + idelev;
                    ResultSet rs = c.getConexiune(q1);
                    String q2 = "select distinct id_materie from materie\n"
                            + "where denumire='" + specializare + "'";
                    System.out.println(specializare);
                    System.out.println(idelev);
                    ResultSet rs1 = c.getConexiune(q2);
                    try {
                        if (rs.next()) {
                            idcatalog = rs.getInt(1);
                        }

                        if (rs1.next()) {
                            idmaterie = rs1.getInt(1);
                        }

                        /*
                    String query = "Insert into student values (?, ?)" ;
                   PreparedStatement pst = con.prepareStatement(query) ;
                   pst.setInt(1, s.rollnumber);
                   pst.setString(2, s.sname);
                   pst.executeUpdate() ;
                         */
                        String query = "insert into catalog values(?,?,?,?,?,?)";

                        try {
                            pst = c.dmlConexiune(query);
                            System.out.println("idcatalog: " + idcatalog + "\nidelev: " + idelev);
                            pst.setInt(1, idcatalog);

                            pst.setInt(2, idelev);
                            pst.setInt(3, idmaterie);
                            pst.setNull(4, java.sql.Types.INTEGER);
                            String datal = data.getText();

                            pst.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
                            pst.setInt(6, 0);
                            pst.executeUpdate();
                            primaryStage.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Editeaza.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(Editeaza.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    // ... user chose CANCEL or closed the dialog
                }

            }

        }
        );

        motivareAbsenta.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmare");
                alert.setHeaderText("Doriti sa continuati?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    int idcatalog = 0;
                    int idmaterie = 0;

                    String q0 = "select idelev from elev where nume='" + numeelev + "'";
                    ResultSet rs3 = c.getConexiune(q0);
                    try {
                        if (rs3.next()) {
                            idelev = rs3.getInt(1);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Editeaza.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String q1 = "select distinct id_catalogp from catalog\n"
                            + "where id_elev =" + idelev;
                    ResultSet rs = c.getConexiune(q1);
                    String q2 = "select distinct id_materie from materie\n"
                            + "where denumire='" + specializare + "'";
                    System.out.println(specializare);
                    System.out.println(idelev);
                    ResultSet rs1 = c.getConexiune(q2);
                    try {
                        if (rs.next()) {
                            idcatalog = rs.getInt(1);
                        }

                        if (rs1.next()) {
                            idmaterie = rs1.getInt(1);
                        }

                        try {

                            String query = "delete from catalog\n"
                                    + "where data = ?  and nota is null and id_elev=?";
                            String data = absentatf.getText();
                            if (!data.matches("\\d{4}-\\d{2}-\\d{2}")) {
                                throw new Exception();
                            }

                            pst = c.dmlConexiune(query);

                            pst.setDate(1, java.sql.Date.valueOf(data));
                            pst.setInt(2, idelev);

                            pst.executeUpdate();
                            primaryStage.close();

                        } catch (Exception ex) {
                            alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Warning Dialog");
                            alert.setHeaderText("Avertizare");
                            alert.setContentText("Introduceti o data valida!");

                            alert.showAndWait();

                        }

                        /*
                    String query = "Insert into student values (?, ?)" ;
                   PreparedStatement pst = con.prepareStatement(query) ;
                   pst.setInt(1, s.rollnumber);
                   pst.setString(2, s.sname);
                   pst.executeUpdate() ;
                         */
                    } catch (SQLException ex) {
                        Logger.getLogger(Editeaza.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    // ... user chose CANCEL or closed the dialog
                }

            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    Editeaza(String str, Stage catalog, String specializare) throws Exception {
        this.specializare = specializare;

        this.numeelev = str;
        stage = new Stage();
        start(stage);

    }

}
