/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abohawa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author rana
 */
public class RemoteFetch {

    
    
   

    private static final String OPEN_WEATHER_MAP_API
            = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String open_weather_maps_app_id = "ad3681a37dd849ec250e19c30afec01a";

    public static  JSONObject getJSON(String city) {
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("x-api-key",
                    open_weather_maps_app_id);

            StringBuffer json;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                json = new StringBuffer(1024);
                String tmp = "";
                while ((tmp = reader.readLine()) != null) {
                    json.append(tmp).append("\n");
                }
            }

            JSONObject data = new JSONObject(json.toString());

            if (data.getInt("cod") != 200) {
               JOptionPane.showMessageDialog(null,
                            "Information not found.",
                            "Not Found",
                            JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
      

            return data;

        } catch (Exception e) {
            System.out.println("Error");
            return null;
        }
    }
}
