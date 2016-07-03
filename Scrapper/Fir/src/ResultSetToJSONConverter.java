import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.ResultSet;
/**
 * Created by rakesh-a on 7/2/2016.
 */
public class ResultSetToJSONConverter {

    /**
     * Utility for converting ResultSets into some Output formats
     * @author Xawroz
     */
        /**
         * Convert a result set into a JSON Array
         * @param resultSet
         * @return a JSONArray
         * @throws Exception
         */
        public static JSONArray convertToJSON(ResultSet resultSet)
                throws Exception {
            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                int total_rows = resultSet.getMetaData().getColumnCount();
                JSONObject obj = new JSONObject();
                for (int i = 0; i < total_rows; i++) {
                    obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                            .toLowerCase(), resultSet.getObject(i + 1));

                }
                jsonArray.put(obj);
            }
            return jsonArray;
        }
}
