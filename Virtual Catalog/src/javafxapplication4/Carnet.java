/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author valen
 */
public class Carnet extends Application {

    Scene scene;
    ChoiceBox cbox;
    BorderPane borderpane;
    private final TableView<String> table = new TableView<>();
    TableColumn nrelev, elev, note, absente, setari;

    Carnet() throws Exception {
        Stage stage = new Stage();
        start(stage);

    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("VTeaching");
        primaryStage.getIcons().add(new Image("file:icon-book.png"));
        cbox = new ChoiceBox();
        cbox.setItems(FXCollections.observableArrayList("Adauga Nota", "Prezent", "Absent"));

        nrelev = new TableColumn("Numar");
        nrelev.setMinWidth(100);
        elev = new TableColumn("Nume Elev");
        elev.setMinWidth(100);
        note = new TableColumn("Note");
        note.setMinWidth(100);
        absente = new TableColumn("Absente");
        absente.setMinWidth(100);
        setari = new TableColumn("Actiune");
        setari.setMinWidth(100);

        table.getColumns().addAll(nrelev, elev, note, absente, setari);

        borderpane = new BorderPane();
        borderpane.setCenter(table);

        scene = new Scene(borderpane, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
