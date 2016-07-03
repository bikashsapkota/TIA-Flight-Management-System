
import com.mysql.jdbc.ResultSet;
import java.sql.*;
import java.sql.PreparedStatement;

/**
 * Created by rakesh-a on 7/2/2016.
 */

//This class contains the method for sql commands
public class MySqlConnector {

    private static final String driver = "com.mysql.jdbc.Driver";

    private static final String userName = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/" + "deerthon";

    public void executeInsertAndUpdateQurey(String statement) {
        try {


            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url, userName, password);//PreparedStatement prd=conn.prepareStatement("insert into student values (null,?)");
            Statement stmt = conn.createStatement();
            PreparedStatement prd = conn.prepareStatement(statement);

            prd.executeUpdate();
            conn.close();

            //System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public java.sql.ResultSet checkDataExists(String statement) {
        java.sql.ResultSet res=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, userName, password);//PreparedStatement prd=conn.prepareStatement("insert into student values (null,?)");
            Statement stmt = conn.createStatement();
            res = stmt.executeQuery(statement);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }
}