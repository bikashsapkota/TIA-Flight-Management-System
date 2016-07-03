import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

/**
 * Created by rakesh-a on 7/3/2016.
 */

//This webservice sends the table data in JSON format in response to get request
public class SendJSONDataToMobile  extends HttpServlet{

        public void doGet (HttpServletRequest request,
                           HttpServletResponse response)
                throws ServletException, IOException
        {


            response.setContentType("application/json");
            PrintWriter out = response.getWriter();


            String table_name = request.getParameter("table_name");
            MySqlConnector sqlConnector=new MySqlConnector();
            ResultSet rs= sqlConnector.checkDataExists("Select * from "+table_name);
            try {
                JSONArray js = ResultSetToJSONConverter.convertToJSON(rs);
                out.println(js);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            out.close();
        }


}
