package abohawa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Md. Rana Mahmud
 */
public class Abohawa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File file = new File("data.txt");
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Abohawa.class.getName()).log(Level.SEVERE, null, ex);
        }
        String result = null;
        WeatherTab myWeather = new WeatherTab();
        try {
            FileReader read = new FileReader("data.txt");
            BufferedReader reader = new BufferedReader(read);
            String line = null;
            result = line;
            try {
                while ((line = reader.readLine()) != null) {
                    result = line;

                }
            } catch (IOException ex) {
                Logger.getLogger(Abohawa.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Abohawa.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result != null) {
            String[] data = result.split("#");
            myWeather.renderFromFile(data);
        }

    }

}
