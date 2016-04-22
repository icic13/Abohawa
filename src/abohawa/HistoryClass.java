/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abohawa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author rana
 */
public class HistoryClass extends JFrame {

    public HistoryClass() {
        String[] columns = new String[]{
            "Date", "Country", "Temperature ℃", "Detail", "Pressure  hPa",
            "Humidity %"
        };
        String[] result;
        ArrayList<String> item = new ArrayList();
        try {
            FileReader read = new FileReader("data.txt");
            BufferedReader reader = new BufferedReader(read);
            String line = null;

            try {
                while ((line = reader.readLine()) != null) {
                    item.add(line);

                }
            } catch (IOException ex) {
                Logger.getLogger(Abohawa.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Abohawa.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[][] data = new String[item.size()][6];

        for (int i = 0; i < item.size(); i++) {
            result = item.get(i).split("#");
            String[] it = tokenizeData(result);

            for (int j = 0; j < 6; j++) {

                data[i][j] = it[j];

            }

        }

        //Table
        JTable table = new JTable(data, columns);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        this.add(new JScrollPane(table));
        this.setTitle("Wether history");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);

        this.setSize(800, 300);
        this.setVisible(true);

    }

    public String[] tokenizeData(String[] data) {

        String cityFieldText = data[8].toUpperCase(Locale.US)
                + ", " + data[19];

        DateFormat df = DateFormat.getDateTimeInstance();
        long value = Long.parseLong(data[0]);
        String updatedOn = df.format(new Date(value * 1000));

        String[] item = {updatedOn, cityFieldText, data[11], data[4].toUpperCase(Locale.US), data[13], data[14]};
        return item;

    }

}
