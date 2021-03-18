/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author PCristofor
 */
public class OrarElev {

    ArrayList<String> detaliielev;
    int idelev;
    BorderPane borderpane;
    private final TableView<POrar> table = new TableView<>();
    ObservableList<POrar> data = FXCollections.observableArrayList();
    TableColumn<POrar, String> ora;
    TableColumn<POrar, String> luni, marti, miercuri, joi, vineri;

    Scene scene;
    Conexiune c = new Conexiune();

    OrarElev(int idelev, ArrayList<String> detaliielev) {
        this.detaliielev = detaliielev;
        Stage stage = new Stage();
        this.idelev = idelev;
        try {
            start(stage);
        } catch (Exception ex) {
            Logger.getLogger(Orar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("VTeaching");
        primaryStage.getIcons().add(new Image("file:icon-book.png"));
        Image imagine = new Image("file:icon-book.png");
        primaryStage.setMaxWidth(600);
        borderpane = new BorderPane();

        //Table
        ora = new TableColumn("Ora");
        ora.setMinWidth(100);
        luni = new TableColumn("Luni");
        luni.setMinWidth(100);
        marti = new TableColumn("Marti");
        marti.setMinWidth(100);
        miercuri = new TableColumn("Miercuri");
        miercuri.setMinWidth(100);
        joi = new TableColumn("Joi");
        joi.setMinWidth(100);
        vineri = new TableColumn("Vineri");
        vineri.setMinWidth(100);

        borderpane.setStyle("-fx-background-color: #F0F8FF;");

        table.setEditable(false);
        ora.setCellValueFactory(new PropertyValueFactory("ora"));
        luni.setCellValueFactory(new PropertyValueFactory("luni"));
        marti.setCellValueFactory(new PropertyValueFactory("marti"));
        miercuri.setCellValueFactory(new PropertyValueFactory("miercuri"));
        joi.setCellValueFactory(new PropertyValueFactory("joi"));
        vineri.setCellValueFactory(new PropertyValueFactory("vineri"));

        table.setItems(orar());
        table.getColumns().addAll(ora, luni, marti, miercuri, joi, vineri);
        borderpane.setCenter(table);
        //Table

        scene = new Scene(borderpane, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private ObservableList<POrar> orar() {
        ArrayList<ProfesorOrar> dateorar = new ArrayList<>();
        ArrayList<String> clase = new ArrayList<>();
        ArrayList<String> orare = new ArrayList<>();
        int materie = 0;
        String mat = "";

        String[] oreziledb = new String[5];
        String[] oreorar = {"8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00"};

        String[] zile = {"LUNI", "MARTI", "MIERCURI", "JOI", "VINERI"};
        String[] ore = {"OPT", "NOUA", "ZECE", "UNSPREZECE", "DOISPREZECE", "TREISPREZECE", "PAISPREZECE", "CINCISPREZECE", "SAISPREZECE", "SAPTESPREZECE", "OPTSPREZECE", "NOUASPREZECE"};

        int i = 0, j = 0;
        for (String s : ore) {

            for (String s1 : zile) {

                String q = "select distinct materie.denumire\n"
                        + "from (((elev inner join clasa on ELEV.CLASA=CLASA.ID_CLASA) inner join orar on ORAR.ID_ORAR=CLASA.ORAR) inner join ore on ore.ID_ORE=orar." + s1 + ") inner join materie on MATERIE.ID_MATERIE=ORE." + s + "\n"
                        + "where id_clasa=" + detaliielev.get(3);

                ResultSet rsorar = c.getConexiune(q);
                try {
                    if (rsorar.next()) {
                        oreziledb[j] = rsorar.getString(1);
                        System.out.println("s: " + s + " s1: " + s1 + " " + rsorar.getString(1));
                        j++;

                    } else {
                        oreziledb[j] = "";
                        j++;
                    }

                } catch (SQLException ex) {

                    Logger.getLogger(Orar.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            data.add(new POrar(oreorar[i], oreziledb[0], oreziledb[1], oreziledb[2], oreziledb[3], oreziledb[4]));
            i++;
            j = 0;
        }
        return data;

    }
}
