/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abohawa;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
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
import org.json.JSONException;
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

        cityButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String cityname = cityInputField.getText();
                System.out.println(cityname);
                updateWeatherData(cityname);

            }
        });
    }

    private void renderWeather(JSONObject json) {

        try {

            cityLabel.setText(json.getString("name").toUpperCase(Locale.US)
                    + ", "
                    + json.getJSONObject("sys").getString("country"));

    
        } catch (Exception e) {
        }
    }

    private void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(city);
                System.out.println("Main thread");

                if (json == null) {
                    System.out.println("Error");
                    new Thread() {
                        public void run() {

                            System.out.println("Child thread");
                        }
                    }.start();

                } else {

                    System.out.println(json);

                    new Thread() {
                        public void run() {
                            try {
                                String city = json.getString("name").toUpperCase();

                                System.out.println("Child thread");
                                System.out.println(city);
                                renderWeather(json);
                            } catch (JSONException ex) {
                                Logger.getLogger(WeatherTab.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }.start();

                }

            }
        }.start();
    }
}
