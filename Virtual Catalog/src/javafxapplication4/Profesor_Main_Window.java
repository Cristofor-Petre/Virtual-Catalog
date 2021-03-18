/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.awt.Graphics;

import java.awt.Panel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author valen
 */
public class Profesor_Main_Window extends Application {

    ArrayList<String> detaliiprofesor;
    int idprofesor;
    String specializare;
    Stage stage;
    Label numel, cnpl, specializarel, data_angajarel;
    Button orar, teme, catalog;
    BorderPane borderpane;
    Pane right;
    TreeItem<String> rootItem = new TreeItem<String>("Selectati clasa");

    Scene scene;
    Image imagine = new Image("file:icon-book.png");
    VBox vbox_right, vbox_left;
    HBox orartema;
    MenuBar meniu;
    Menu tfls, help;
    MenuItem about, deconecteazate;
    Image avatarread;
    ImageView avatar;
    FileInputStream input;
    Conexiune c = new Conexiune();

    public void start(Stage primaryStage) {
        primaryStage.setTitle("VTeaching");
        primaryStage.getIcons().add(new Image("file:icon-book.png"));
        Image imagine = new Image("file:icon-book.png");
        meniu = new MenuBar();
        meniu.prefWidthProperty().bind(primaryStage.widthProperty());
        tfls = new Menu("VTeaching");
        help = new Menu("Help");
        about = new MenuItem("About");
        deconecteazate = new MenuItem("deconecteaza-te");
        help.getItems().addAll(about);
        tfls.getItems().addAll(deconecteazate);
        meniu.getMenus().addAll(tfls, help);

        numel = new Label("Nume si Prenume: ");
        
        cnpl = new Label("CNP:");
        
        specializarel = new Label("Specializare: ");
        data_angajarel = new Label("Data angajare: ");
        orar = new Button("Orar");
        teme = new Button("Teme");
        catalog = new Button("Catalog");
        profil();
        orar.setPrefWidth(70);
        orar.setPrefHeight(40);
        teme.setPrefWidth(70);
        teme.setPrefHeight(40);
        orartema = new HBox(orar, teme, catalog);
        orartema.setSpacing(10);
        try {
            input = new FileInputStream("avatar.png");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Profesor_Main_Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        avatarread = new Image("file:avatar.png");
        avatar = new ImageView();
        avatar.setImage(avatarread);

        listaClase();

        TreeView<String> tree = new TreeView<String>(rootItem);

        vbox_right = new VBox(tree, orartema);
        vbox_right.setSpacing(5);
        vbox_left = new VBox(avatar, numel, cnpl, specializarel, data_angajarel);
        vbox_left.setSpacing(30);
       // vbox_left.setSpacing(5);
        vbox_right.setAlignment(Pos.CENTER);
        vbox_right.setPadding(new Insets(50));
        vbox_left.setPadding(new Insets(50));

        borderpane = new BorderPane();
        borderpane.setTop(meniu);
        borderpane.setLeft(vbox_left);
        borderpane.setRight(vbox_right);
        borderpane.setStyle("-fx-background-color: #F0F8FF;");
        scene = new Scene(borderpane, 900, 500);
        primaryStage.setScene(scene);
        deconecteazate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                primaryStage.close();
                try {
                    Login login = new Login();
                } catch (Exception ex) {
                    Logger.getLogger(Profesor_Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        orar.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    new Orar(idprofesor);
                } catch (Exception ex) {
                    Logger.getLogger(Profesor_Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        catalog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Avertizare");
                alert.setContentText("Selectati o clasa!");
                alert.showAndWait();

            }

        });
        teme.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Avertizare");
                alert.setContentText("Selectati o clasa!");
                alert.showAndWait();

            }

        });
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,
                    Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;

                catalog.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (selectedItem.getValue().equals(null)) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning Dialog");
                            alert.setHeaderText("Avertizare");
                            alert.setContentText("Selectati o clasa!");
                            alert.showAndWait();
                        } else {
                            try {

                                System.out.println("sdfsdfsdfs");

                                new Catalog(selectedItem.getValue(), specializare);
                            } catch (Exception ex) {
                                Logger.getLogger(Profesor_Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                });
                // System.out.println("Selected Text : " + selectedItem.getValue());
                // do what ever you want 
                teme.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            new Teme(selectedItem.getValue(), detaliiprofesor);
                        } catch (Exception ex) {
                            Logger.getLogger(Profesor_Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }

        });

        primaryStage.show();
    }

    Profesor_Main_Window(ArrayList<String> detaliiprofesor, int idprofesor) {
        stage = new Stage();
        this.idprofesor = idprofesor;
        this.detaliiprofesor = detaliiprofesor;
        start(stage);

    }

    private void listaClase() {

        ResultSet rs = c.getConexiune("select * from preda");
        ResultSet rs1;
        int id;

        try {

            while (rs.next()) {

                if (rs.getInt(2) == idprofesor) {

                    rs1 = c.getConexiune("select nume_clasa from clasa where id_clasa=" + rs.getInt(3));
                    rs1.next();

                    TreeItem<String> item = new TreeItem<String>(rs1.getString(1));

                    rootItem.getChildren().add(item);

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void profil() {
        ResultSet rs = c.getConexiune("select * from clasa");

        try {

            numel.setText("Nume si Prenume: " + detaliiprofesor.get(0));
            cnpl.setText("CNP: " + detaliiprofesor.get(1));
            ResultSet rs1 = c.getConexiune("select denumire from MATERIE where id_materie=" + detaliiprofesor.get(2));
            rs1.next();
            specializarel.setText("Specializare: " + rs1.getString(1));
            this.specializare = rs1.getString(1);

            data_angajarel.setText("Data angajare: " + detaliiprofesor.get(3));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void orarProfesor() {

    }

}
