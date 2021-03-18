/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author PCristofor
 */
public class CatalogElev {

    ArrayList<String> detaliielev;
    ChoiceBox cbox;
    BorderPane borderpane;
    private final TableView<PCatalog> table = new TableView<>();
    TableColumn<PCatalog, String> nrelev, elev, nota, data, setari, materie, absent;
    ObservableList<PCatalog> dataCatalog = FXCollections.observableArrayList();
    Conexiune c = new Conexiune();

    String clasa;
    String specializare;
    Stage stage;

    CatalogElev(ArrayList<String> detaliielev) {
        stage = new Stage();
        this.detaliielev = detaliielev;
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

        nrelev = new TableColumn("Materie");
        nrelev.setMinWidth(100);
        nrelev.setCellValueFactory(new PropertyValueFactory<PCatalog, String>("nrelev"));
        elev = new TableColumn("Note");
        elev.setMinWidth(200);
        elev.setCellValueFactory(new PropertyValueFactory<PCatalog, String>("nume"));
        data = new TableColumn("Data");
        data.setMinWidth(100);
        data.setCellValueFactory(new PropertyValueFactory<PCatalog, String>("nota"));
        absent = new TableColumn("Absent");
        absent.setMinWidth(100);
        absent.setCellValueFactory(new PropertyValueFactory<PCatalog, String>("data"));

        table.setItems(catalog());
        table.getColumns().addAll(nrelev, elev, data, absent);

        borderpane = new BorderPane();
        borderpane.setCenter(table);

        Scene scene = new Scene(borderpane, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public ObservableList<PCatalog> catalog() {

        String nota = "";
        String data = "";
        String dataAbsent = "";
        ArrayList<String> materii = new ArrayList<>();
        String query = "select distinct materie.denumire\n"
                + "from ((elev inner join clasa on clasa.id_clasa=elev.clasa)inner join catalog on elev.idelev = catalog.id_elev) inner join  materie on catalog.id_materie = materie.id_materie\n"
                + "where idelev =" + detaliielev.get(2);

        ResultSet rs = c.getConexiune(query);

        ResultSet rs1;
        try {
            while (rs.next()) {
                materii.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Orar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            for (String s : materii) {
                String q1 = "select nota,elev.nume,catalog.data\n"
                        + "from ((elev inner join clasa on clasa.id_clasa=elev.clasa)inner join catalog on elev.idelev = catalog.id_elev) inner join  materie on catalog.id_materie = materie.id_materie\n"
                        + "where idelev= " + detaliielev.get(2) + " and materie.denumire='" + s + "'";
                rs1 = c.getConexiune(q1);
                while (rs1.next()) {
                    int iacob = rs1.getInt(1);
                    if (!rs1.wasNull()) {
                        nota = nota + rs1.getInt(1) + System.lineSeparator();
                        System.out.println(nota);
                        data = data + rs1.getDate(3) + System.lineSeparator();
                    } else {
                        // nota = nota + System.lineSeparator() ;
                        System.out.println("fgdfgdfg");
                        dataAbsent = dataAbsent + rs1.getDate(3) + System.lineSeparator();
                    }
                }
                dataCatalog.add(new PCatalog(s, nota, data, dataAbsent));
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
