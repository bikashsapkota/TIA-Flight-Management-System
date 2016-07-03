import com.sun.org.apache.regexp.internal.RE;
import org.json.JSONArray;

import java.sql.ResultSet;

/**
 * Created by rakesh-a on 7/3/2016.
 */


//THis class is used to convert resultset to JSON ARRAY
public class SendJsonData {


    public static void main(String[] args) {
        MySqlConnector sqlConnector=new MySqlConnector();
        ResultSet rs= sqlConnector.checkDataExists("Select * from int_arrivals");
        JSONArray jsonArray=new JSONArray();
        try {
            jsonArray = ResultSetToJSONConverter.convertToJSON(rs);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(jsonArray);

    }

}
