package javafxapplication4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexiune {

    private Statement st;
    private ResultSet rs;
    private Connection con;
    private PreparedStatement pst;

    public ResultSet getConexiune(String query) {
        try {

            Class.forName("oracle.jdbc.OracleDriver");
            /*
            String url = "jdbc:odbc:host_name:port:database_name";
            Connection con = DriverManager.getConnection(url, login, passwd);
             */
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "student", "student");
            //PreparedStatement st = con.prepareStatement(query) ; // Prepared Statement
            // st.setInt(1, rollnumber);
            // st.setString(2, sname);
            //int count = st.executeUpdate() ; //DDL, DML, DQL
            //System.out.println(count + " row/s affected") ;
            st = con.createStatement();
            rs = st.executeQuery(query);
            // rs.next() ;

            //st.close();
            //con.close() ;
            //main=new Elev_Main_Window();
            // primaryStage.hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public PreparedStatement dmlConexiune(String query) throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "student", "student");
            pst = con.prepareStatement(query);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexiune.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pst;

    }

}
