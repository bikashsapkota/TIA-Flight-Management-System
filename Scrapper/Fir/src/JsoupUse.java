import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.ResultSet;


/**
 * Created by rakesh-a on 7/2/2016.
 */
public class JsoupUse extends Thread {


    public static void main(String[] args) {
        JsoupUse jsoupUse=new JsoupUse();
        jsoupUse.start();
    }
    public void run() {

        while (true) {
            Document doc = null;
            MySqlConnector mySqlConnector = new MySqlConnector();

            try {
                //Jsoup library is used for scrapping the html table in the website
                doc = Jsoup.connect("http://localhost/tianepal/tia.html").get();

                //http://www.tiairport.com.np/flight/information
                //

                //Four tabs are used to display the flight information
                Elements internationalArrivals = doc.select("#tab-1");
                Elements internationalDepartures = doc.select("#tab-2");
                Elements DomesticArrivals = doc.select("#tab-3");
                Elements DomesticDepartures = doc.select("#tab-4");

                String tablename = "";//Stores the tablename in which walue is to be updated




                //collect data for international arrivals from table and if the change occours in status notification is sent
                tablename = "int_arrivals";
                tableDataFilter(internationalArrivals, tablename, mySqlConnector);

                //collect data for international departures from table and if the change occours in status notification is sent
                tablename = "int_departures";
                tableDataFilter(internationalDepartures, tablename, mySqlConnector);

                //collect data for domestic arrivals from table and if the change occours in status notification is sent
                tablename = "dom_arrivals";
                tableDataFilter(DomesticArrivals, tablename, mySqlConnector);

                //collect data for domestic departures from table and if the change occours in status notification is sent
                tablename = "dom_departures";
                tableDataFilter(DomesticDepartures, tablename, mySqlConnector);

                //Sleep for 10 seconds
                Thread.sleep(10000);

                        //If new data is availabe the old data is deleted and new data is inserted
                         for (Element table : internationalArrivals.select("table")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
                        if(tds.size()>=3)
                        {

                            mySqlConnector.executeInsertAndUpdateQurey("Delete from int_arrivals");
                            mySqlConnector.executeInsertAndUpdateQurey("Delete from int_departures");
                            mySqlConnector.executeInsertAndUpdateQurey("Delete from dom_arrivals");
                            mySqlConnector.executeInsertAndUpdateQurey("Delete from dom_departures");

                            break;
                        }
                    }
                }

                //collect data for international arrivals from table and store into the database
                tablename = "int_arrivals";
                tableDataFilter(internationalArrivals, tablename, mySqlConnector);

                //collect data for international departures from table and store into the database
                tablename = "int_departures";
                tableDataFilter(internationalDepartures, tablename, mySqlConnector);

                //collect data for domestic arrivals from table and store into the database
                tablename = "dom_arrivals";
                tableDataFilter(DomesticArrivals, tablename, mySqlConnector);

                //collect data for domestic departures from table and store into the database
                tablename = "dom_departures";
                tableDataFilter(DomesticDepartures, tablename, mySqlConnector);

                Thread.sleep(300000);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
//This function breaks the large Elements collection to smaller and send to data sql handler
    public void tableDataFilter(Elements dataElements, String tablename, MySqlConnector mySqlConnector) {
        int count = 0;
        for (Element table : dataElements.select("table")) {
            for (Element row : table.select("tr")) {
                Elements tds = row.select("td");

                if (count != 0) {
                    try {
                        dataSqlHandler(tds, tablename, mySqlConnector);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                count++;

            }
        }
    }

//This function handles the sql operations
    public void dataSqlHandler(Elements airlinesData, String tableName, MySqlConnector mySqlConnector) {

            try {
                if (mySqlConnector.checkDataExists("select * from " + tableName + " where flight='" + airlinesData.get(3).text() + "';").next()==true) {
                    if (tableName == "int_arrivals" || tableName == "dom_arrivals") {

                        String query = "select * from " + tableName + " where flight='" + airlinesData.get(3).text() + "';";
                        //If data has been changed then the notification is sent to facebook
                        if (checkDataChange(query, airlinesData) == true) {
                            String fbNotificationUrl = "http://myphpapp-hajurkoaaja.rhcloud.com/index.php?description="+ airlinesData.get(5).text() + "&plane=\"" + airlinesData.get(3).text() + "\""+"&delay=test&key=1122";
                            SendGetRequest.callURL(fbNotificationUrl);
                        }
                        ;
                    } else {
                        //if data has been changed then notification is sent to facebook
                        String query = "select * from " + tableName + " where flight='" + airlinesData.get(3).text() + "';";
                        if (checkDataChange(query, airlinesData) == true) {
                            String fbNotificationUrl = "http://myphpapp-hajurkoaaja.rhcloud.com/index.php?description="+ airlinesData.get(5).text()+ "&plane=\"" + airlinesData.get(3).text() + "\""+"&delay=test&key=1122";
                            SendGetRequest.callURL(fbNotificationUrl);
                        }
                    }
                }
                else  {
                    //Insert data into the table
                    String query = "Insert into " + tableName + " values ('" + airlinesData.get(0).text() + "','" + airlinesData.get(1).text() + "','" + airlinesData.get(2).text();
                    query = query + "','" + airlinesData.get(3).text() + "','" + airlinesData.get(4).text() + "','" + airlinesData.get(5).text() + "');";
                    mySqlConnector.executeInsertAndUpdateQurey(query);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }



    }

    //This function check if the current data is different than earlier stored data
    public boolean checkDataChange(String statement,Elements dataelements)
    {

        Boolean change=false;
        MySqlConnector mySqlConnector=new MySqlConnector();
        ResultSet res=mySqlConnector.checkDataExists(statement);
        try {
            int columnsNumber = res.getMetaData().getColumnCount();

            while (res.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = res.getString(i);
                    if(columnValue.compareTo(dataelements.get(i-1).text().toString())!=0)
                    {
                        change=true;

                    }

                }

            }
            System.out.println();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return change;

    }

}




