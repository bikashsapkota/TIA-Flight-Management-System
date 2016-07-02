import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by rakesh-a on 7/2/2016.
 */
public class JsoupUse extends Thread{
    public static void main(String[] args) {
        JsoupUse jsoupUse=new JsoupUse();
        jsoupUse.start();
    }

    public void run()
    {

        while (true) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://localhost/tianepal/tia.html").get();

                //String title = doc.getElementById()
                Elements internationalArrivals=doc.select("#tab-1");
                Elements internationalDepartures=doc.select("#tab-2");
                Elements DomesticArrivals=doc.select("#tab-3");
                Elements DomesticDepartures=doc.select("#tab-4");
                int count=0;
                String tablename="";

                System.out.println("international arrivals");
                System.out.println("Airlined\tSTA\tETA\tFlight\tFlight\tOright\tStatus");
                tablename="int_arrivals";
                for (Element table : internationalArrivals.select("table")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");

                        if(count!=0) {
                            try {
                                post(tds, tablename);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                count=0;
                tablename="int_departures";
                System.out.println("intenational departures");
                System.out.println("Airlined\tSTA\tETA\tFlight\tFlight\tOright\tStatus");
                for (Element table : internationalDepartures.select("table")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
                        if(count!=0) {
                            try {
                                post(tds, tablename);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        count++;

                    }
                }
                count=0;
                tablename="dom_arrivals";
                System.out.println("domestic arrivals");
                System.out.println("Airlined\tSTA\tETA\tFlight\tFlight\tOright\tStatus");
                for (Element table : DomesticArrivals.select("table")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");

                            if(count!=0) {
                                try {
                                    post(tds, tablename);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }                        }
                        count++;


                }
                count=0;
                tablename="dom_departures";
                System.out.println("domestic departures");
                System.out.println("Airlined\tSTA\tETA\tFlight\tFlight\tOright\tStatus");
                for (Element table : DomesticDepartures.select("table")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");

                            if(count!=0) {
                                try {
                                    post(tds, tablename);
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        count++;

                    }
                }


                Thread.sleep(100000);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void post(Elements airlinesData,String tableName) throws Exception
    {

        String data = "type="+tableName+"&arrivals="+airlinesData.get(0).text()+"&sta="+airlinesData.get(1).text()+"&eta="+airlinesData.get(2);
        data=data+"&flight="+airlinesData.get(3).text()+"&origin="+airlinesData.get(4).text()+"&status="+airlinesData.get(5).text();

        URL url = new URL("http://192.168.5.44/myphpapp/deerthon/webservice.php?");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
        wr.close();
        rd.close();
    }


}
