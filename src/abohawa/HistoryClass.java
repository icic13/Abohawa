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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author rana
 */
public class HistoryClass extends JFrame {

    public HistoryClass() {
        String[] columns = new String[]{
            "Date", "Country", "Temperature", "Detail", "Pressure",
            "Humidity"
        };
        String[] result;
        
        
        try {
            FileReader read = new FileReader("data.txt");
            BufferedReader reader = new BufferedReader(read);
            String line = null;

            try {
                while ((line = reader.readLine()) != null) {
                    result = line.split("#");
                      for(int i = 0; i<result.length;i++)
                          System.out.print(result[i]+" ");
                      System.out.println();
                }
            } catch (IOException ex) {
                Logger.getLogger(Abohawa.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Abohawa.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object[][] data = new Object[][]{
            {1, "John", 40.0, false},
            {2, "Rambo", 70.0, false},
            {3, "Zorro", 60.0, true},};

        //Table
       // JTable table = new JTable(data, columns);
        //this.add(new JScrollPane(table));
        this.setTitle("Wether history");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);

    }

}
