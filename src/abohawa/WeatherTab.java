/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abohawa;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.json.JSONObject;

/**
 *
 * @author rana
 */
public class WeatherTab {

    private final JFrame frame;
    private final JPanel weatherPanel;
    private final JLabel cityLabel;
    private final JLabel updatedText;
    private final JLabel weatherIcon;
    private final JLabel temperature;
    private final JLabel detail;
    private final JLabel cityInputLabel;
    private JTextField cityInputField;
    private final JButton cityButton;
    private JSONObject weather;

    private final BoxLayout boxLayout;

    WeatherTab() {

        cityButton = new JButton("ok");

        cityInputLabel = new JLabel("শহরের নাম:");
        cityInputField = new JTextField(12);
        cityLabel = new JLabel("City Label");
        updatedText = new JLabel("UPdated Text");
        weatherIcon = new JLabel("Weather Icon");
        temperature = new JLabel("Temperature");
        detail = new JLabel("Detail");
        weatherPanel = new JPanel();
        boxLayout = new BoxLayout(weatherPanel, BoxLayout.Y_AXIS);
        weatherPanel.setLayout(boxLayout);
        weatherPanel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));
        weatherPanel.add(cityInputLabel);
        weatherPanel.add(cityInputField);

        cityButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cityname = cityInputField.getText();
                    Fetch ft = new Fetch(cityname);

                    weather = ft.getJSON();

                    System.out.println(weather);
                    renderWeather();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WeatherTab.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        weatherPanel.add(cityButton);
        weatherPanel.add(cityLabel);
        weatherPanel.add(updatedText);
        weatherPanel.add(weatherIcon);
        weatherPanel.add(temperature);
        weatherPanel.add(detail);
        frame = new JFrame("অাবহাওয়া");
        frame.add(weatherPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void renderWeather() {
        try {

            cityLabel.setText("Sylhet");

            //cityLabel.setText(weather.getString("name").toUpperCase(Locale.US));
        } catch (Exception e) {
            System.out.println("Error setting text");
        }
    }

    class Fetch implements Runnable {

        Thread t;
        private String cityName;
        JSONObject json;

        public Fetch(String city) throws InterruptedException {

            cityName = city;
            t = new Thread(this, "Get Data Thread");

            t.start();

        }

        @Override
        public void run() {

            System.out.println("run()");
            try {
                System.out.println("Fetcing Start");
                json = RemoteFetch.getJSON(cityName);
                System.out.println(json);
                renderWeather();

            } catch (Exception e) {
                System.out.println("Error fetching");
            }

        }

        JSONObject getJSON() {
            return json;
        }
    }
}
