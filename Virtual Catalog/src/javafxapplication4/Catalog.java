/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.lang.String;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TablePosition;
import javafx.util.Callback;

/**
 *
 * @author PCristofor
 */
public class Catalog extends Application {

    Scene scene;
    ChoiceBox cbox;
    BorderPane borderpane;
    Button seetari;
    private final TableView<PCatalog> table = new TableView<>();
    TableColumn<PCatalog, String> nrelev, elev, nota, data, setari, materie, absent;
    ObservableList<PCatalog> dataCatalog = FXCollections.observableArrayList();
    Conexiune c = new Conexiune();

    String clasa;
    String specializare;
    Stage stage;

    Catalog(String clasa, String specializare) {
        stage = new Stage();
        this.specializare = specializare;
        this.clasa = clasa;
        try {

            start(stage);
        } catch (Exception ex) {
            Logger.getLogger(Catalog.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("VTeaching");
        primaryStage.getIcons().add(new Image("file:icon-book.png"));
        cbox = new ChoiceBox();
        cbox.setItems(FXCollections.observableArrayList("Adauga Nota", "Prezent", "Absent"));

        seetari = new Button("editeaza");
        nrelev = new TableColumn("Numar");
        nrelev.setMinWidth(100);
        nrelev.setCellValueFactory(new PropertyValueFactory<PCatalog, String>("nrelev"));
        elev = new TableColumn("Nume");
        elev.setMinWidth(200);
        elev.setCellValueFactory(new PropertyValueFactory<PCatalog, String>("nume"));
        materie = new TableColumn(specializare);
        materie.setMinWidth(100);
        materie.setCellValueFactory(new PropertyValueFactory<PCatalog, String>("nota"));

        data = new TableColumn("DataNota");
        data.setMinWidth(100);
        data.setCellValueFactory(new PropertyValueFactory<PCatalog, String>("data"));
        absent = new TableColumn("Absent");
        absent.setMinWidth(100);
        absent.setCellValueFactory(new PropertyValueFactory<PCatalog, String>("absent"));
        setari = new TableColumn("Actiune");
        setari.setMinWidth(100);
        setari.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        //
        Callback<TableColumn<PCatalog, String>, TableCell<PCatalog, String>> cellFactory
                = //
                new Callback<TableColumn<PCatalog, String>, TableCell<PCatalog, String>>() {
            @Override
            public TableCell call(final TableColumn<PCatalog, String> param) {
                final TableCell<PCatalog, String> cell = new TableCell<PCatalog, String>() {

                    final Button btn = new Button("Setari");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {

                                try {
                                    primaryStage.close();

                                    try {
                                        TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
                                        int row = pos.getRow();
                                        PCatalog str = table.getItems().get(row);
                                        System.out.println(str.getNume());
                                        Editeaza edit = new Editeaza(str.getNume(), primaryStage, specializare);
                                    } catch (Exception ex) {
                                        Alert alert = new Alert(AlertType.WARNING);
                                        alert.setTitle("Warning Dialog");
                                        alert.setHeaderText("Selectati un elev!");

                                        alert.showAndWait();
                                    }

                                } catch (Exception ex) {
                                    Logger.getLogger(Catalog.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        setari.setCellFactory(cellFactory);
        table.setItems(catalog());
        table.getColumns().addAll(nrelev, elev, materie, data, absent, setari);

        borderpane = new BorderPane();
        borderpane.setCenter(table);

        scene = new Scene(borderpane, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public ObservableList<PCatalog> catalog() {

        /*
         select nota,materie.denumire,elev.nume,catalog.data
from ((elev inner join clasa on clasa.id_clasa=elev.clasa)inner join catalog on elev.idelev = catalog.id_elev) inner join  materie on catalog.id_materie = materie.id_materie
where nume_clasa = '9A' and materie.denumire='Romana'
         */
        String q1 = "select elev.nume from elev inner join clasa on elev.clasa=clasa.id_clasa where clasa.nume_clasa='" + clasa + "'";
        String query = "select nota,materie.denumire,elev.nume,catalog.data,catalog.prezenta\n"
                + "from ((elev inner join clasa on clasa.id_clasa=elev.clasa)inner join catalog on elev.idelev = catalog.id_elev) inner join  materie on catalog.id_materie = materie.id_materie\n"
                + "where nume_clasa ='" + clasa + "' and materie.denumire='" + specializare + "'" + "and elev.nume='" + clasa + "'";
        String nota = "";
        String data = "";
        String dataAbsent = "";
        ResultSet rs;
        ResultSet rs1 = c.getConexiune(q1);
        int i = 1;
        try {
            while (rs1.next()) {

                query = "select nota,materie.denumire,elev.nume,catalog.data,catalog.prezenta\n"
                        + "from ((elev inner join clasa on clasa.id_clasa=elev.clasa)inner join catalog on elev.idelev = catalog.id_elev) inner join  materie on catalog.id_materie = materie.id_materie\n"
                        + "where nume_clasa ='" + clasa + "' and materie.denumire='" + specializare + "'" + "and elev.nume='" + rs1.getString(1) + "' order by elev.idelev";
                rs = c.getConexiune(query);
                while (rs.next()) {

                    int iacob = rs.getInt(1);
                    if (!rs.wasNull()) {
                        nota = nota + rs.getInt(1) + System.lineSeparator();
                        //System.out.pritnln(nota) ;
                        data = data + rs.getDate(4) + System.lineSeparator();
                    } else {
                        //nota = nota + System.lineSeparator() ;
                        dataAbsent = dataAbsent + rs.getDate(4) + System.lineSeparator();
                    }

                }

                dataCatalog.add(new PCatalog(Integer.toString(i), rs1.getString(1), nota, data, dataAbsent, ""));
                i = i + 1;
                nota = "";
                data = "";
                dataAbsent = "";

            }
        } catch (SQLException ex) {
            Logger.getLogger(Orar.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataCatalog;
    }

}
