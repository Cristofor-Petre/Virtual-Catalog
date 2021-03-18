/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.awt.Image;
import java.util.List;
import java.util.Observable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author valen
 */
public class Main extends Application {

    final Separator sepHor = new Separator();
    Insets insets = new Insets(10);
    Stage stage;
    Scene scene, scene_pane;
    HBox vbox_butoane, hbox_left;
    VBox vbox_right;
    Tab personale, scoala;
    TabPane pane_tab;
    BorderPane border_pane, personale_pane;
    Button adauga, sterge, deschide, save;
    GridPane grid;
    Label texte_inspirationale;
    Group root;
    MenuBar meniu;
    Menu tfls, help;
    MenuItem about, deconecteazate;
    TextArea text_area;

    ObservableList<String> elemente_lista = FXCollections.observableArrayList("c++", "java", "porc la gratar", "c++", "java", "porc la gratar", "c++", "java", "porc la gratar", "c++", "java", "porc la gratar", "c++", "java", "porc la gratar", "c++", "java", "porc la gratar", "c++", "java", "porc la gratar", "c++", "java", "porc la gratar", "c++", "java", "porc la gratar", "c++", "java", "porc la gratar", "c++", "java", "porc la gratar", "c++", "java", "porc la gratar");
    TreeItem<String> lista;

    Main() throws Exception {
        stage = new Stage();
        start(stage);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("VTeaching");

        lista = new TreeItem<String>("lista");
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String>("Message" + i);
            lista.getChildren().add(item);
        }
        TreeView<String> tree = new TreeView<String>(lista);
        texte_inspirationale = new Label("Try Fail Learn Succed");
        tree.setStyle("-fx-background-color: ;");
        tree.setStyle("-fx-background-insets: 0;");
        deschide = new Button("Deschide");
        save = new Button("Salveaza Modificari");
        text_area = new TextArea();
        text_area.prefHeightProperty().bind(primaryStage.widthProperty());
        meniu = new MenuBar();
        meniu.prefWidthProperty().bind(primaryStage.widthProperty());
        tfls = new Menu("VTeaching");

        help = new Menu("Help");
        about = new MenuItem("About");
        help.getItems().addAll(about);

        meniu.getMenus().addAll(tfls, help);

        adauga = new Button("+ Adauga");
        sterge = new Button("- Sterge");
        adauga.setMaxWidth(Double.MAX_VALUE);
        sterge.setMaxWidth(Double.MAX_VALUE);
        save.setMaxWidth(Double.MAX_VALUE);
        deschide.setMaxWidth(Double.MAX_VALUE);

        vbox_butoane = new HBox();
        vbox_butoane.setPadding(new Insets(0, 20, 10, 20));
        vbox_butoane.setSpacing(10);
        vbox_butoane.getChildren().addAll(adauga, sterge);

        hbox_left = new HBox();
        hbox_left.setPadding(new Insets(0, 20, 10, 20));
        hbox_left.setSpacing(5);
        hbox_left.getChildren().addAll(tree, deschide);

        vbox_right = new VBox();
        vbox_right.setPadding(new Insets(0, 20, 10, 20));
        vbox_right.setSpacing(5);
        vbox_right.getChildren().addAll(text_area, save);

        personale_pane = new BorderPane();
        personale_pane.setPadding(new Insets(30, 30, 30, 30));
        personale_pane.setLeft(hbox_left);
        personale_pane.setTop(vbox_butoane);
        personale_pane.setCenter(vbox_right);
        personale_pane.setBottom(texte_inspirationale);
        //personale_pane.setLeft(lista);
        personale_pane.setStyle("-fx-background-image: url(https://orig00.deviantart.net/8cd7/f/2012/055/d/3/texture_42_by_xnienke-d4qtsbi.jpg);");
        //personale_pane.setStyle("-fx-background-repeat: stretch;");

        root = new Group();
        pane_tab = new TabPane();
        border_pane = new BorderPane();
        scene = new Scene(root, 900, 550);

        personale = new Tab();
        personale.setText("Personale");
        personale.setContent(personale_pane);
        pane_tab.getTabs().add(personale);

        scoala = new Tab();
        scoala.setText("Scoala");
        pane_tab.getTabs().add(scoala);

        border_pane.prefHeightProperty().bind(scene.heightProperty());
        border_pane.prefWidthProperty().bind(scene.widthProperty());

        border_pane.setCenter(pane_tab);
        border_pane.setTop(meniu);
        //root.getChildren().add(border_pane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
