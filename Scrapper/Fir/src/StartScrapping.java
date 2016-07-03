import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by rakesh-a on 7/3/2016.
 */

//This service starts jsoupUSe thread
public class StartScrapping extends HttpServlet{
    public void doGet (HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        JsoupUse jsoupUse = new JsoupUse();
        jsoupUse.start();
    }

}
