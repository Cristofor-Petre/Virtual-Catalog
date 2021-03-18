/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *
 * @author PCristofor
 */
public class Teme extends Application {

    ArrayList<String> detaliiprofesor;
    ArrayList<String> temedescr = new ArrayList<>();
    String clasa;
    Scene scene;
    Button adaugatema, deschidetema, salveazamodificari;
    Image imagine = new Image("file:icon-book.png");
    TextField temaopen;
    BorderPane borderpane;
    MenuBar meniu;
    Menu tfls, help;
    HBox butoanebot;
    MenuItem about, deconecteazate;
    TreeView<String> tree;
    TreeItem<String> treeitem;
    Conexiune c = new Conexiune();
    ArrayList<Integer> idteme = new ArrayList<>() ;

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
        adaugatema = new Button("Adauga Tema");
        deschidetema = new Button("Deschide Tema");
        butoanebot = new HBox(adaugatema, deschidetema);
        salveazamodificari = new Button("Salvreaza modificari");
        salveazamodificari.setPrefWidth(700);
        temaopen = new TextField();
        temaopen.setPrefSize(500, 500);

        butoanebot.setSpacing(5);
        treeitem = new TreeItem<String>("Selectati tema");
        getTeme();
        tree = new TreeView<String>(treeitem);

        VBox left = new VBox(tree, butoanebot);
        VBox right = new VBox(temaopen, salveazamodificari);

        borderpane = new BorderPane();
        borderpane.setLeft(left);
        borderpane.setTop(meniu);
        borderpane.setCenter(right);
        borderpane.setStyle("-fx-background-color: #F0F8FF;");

        scene = new Scene(borderpane, 900, 500);
        primaryStage.setScene(scene);

       

            adaugatema.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    try {
                        
                      new AdaugaTema(detaliiprofesor,clasa);
                      

                    } catch (Exception ex) {
                        Logger.getLogger(Profesor_Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });

       
        
       tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,
                    Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;

                salveazamodificari.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        PreparedStatement pst;
   
                        try {
                            //temaopen.setText(temedescr.get(Integer.parseInt(idtema[1]) - 1));
                            //String[] idtema = (selectedItem.getValue()).split(" ");
                            
                            String[] idt = (selectedItem.getValue()).split(" ");
                           System.out.println(idteme.get(Integer.parseInt(idt[1])-1)) ;
                            String query = "update tema\n" +
                                            "set descriere=?\n" +
                                            "where id_tema=?" ;
                            String desc=temaopen.getText();
                             pst = c.dmlConexiune(query) ;
                             pst.setString(1, desc);
                             pst.setInt(2, idteme.get(Integer.parseInt(idt[1])-1));
                            pst.executeUpdate() ;
                            

                        } catch (Exception ex) {
                            Logger.getLogger(Profesor_Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                });

            }

        });
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,
                    Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;

                deschidetema.setOnAction(new EventHandler<ActionEvent>() {
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
                                String[] idtema = (selectedItem.getValue()).split(" ");
                                
                                temaopen.setText(temedescr.get(Integer.parseInt(idtema[1]) - 1));
                                //System.out.println(Integer.parseInt(idtema[1]) - 1);
                            } catch (Exception ex) {
                                Logger.getLogger(Profesor_Main_Window.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                });
            }
        });

        primaryStage.show();
    }

    Teme(String clasa, ArrayList<String> detaliiprofesor) {
        Stage stage = new Stage();
        this.detaliiprofesor = detaliiprofesor;
        this.clasa = clasa;
        start(stage);

    }

    public void getTeme() {
        //treeitem.getChildren().add(item);

        ResultSet rs;
        TreeItem<String> item;
        System.out.println(clasa);
        String query = "select descriere,id_tema\n"
                + "from tema\n"
                + "where id_clasa = (select id_clasa from clasa where nume_clasa='" + clasa + "') order by id_tema";

        rs = c.getConexiune(query);
        int i = 1;
        try {

            while (rs.next()) {
                item = new TreeItem<String>("Tema " + i++);
                temedescr.add(rs.getString(1));
                System.out.println(rs.getString(1));
                treeitem.getChildren().add(item);
                idteme.add(rs.getInt(2)) ;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Teme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
