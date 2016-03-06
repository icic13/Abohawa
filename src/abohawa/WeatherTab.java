/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abohawa;

import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
        cityLabel = new JLabel();
        updatedText = new JLabel();
        weatherIcon = new JLabel();
        temperature = new JLabel();
        detail = new JLabel();
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

                updateWeatherData(cityname);

            }
        });
    }

    private void renderWeather(JSONObject json) {

        try {
            String cityFieldText = json.getString("name").toUpperCase(Locale.US)
                    + ", " + json.getJSONObject("sys").getString("country");

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            String temp = main.getDouble("temp") + " ℃";

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt") * 1000));

            String detailText = details.getString("description").toUpperCase(Locale.US)
                    + "\n" + " Humidity: " + main.get("humidity") + "%"
                    + "\n" + " Pressure: " + main.get("pressure") + " hPa";
            int id = details.getInt("id");
            cityLabel.setText(cityFieldText);
            updatedText.setText("Last update " + updatedOn);

            temperature.setText(temp);
            detail.setText(detailText);
            setWeatherIcon(id);
        } catch (JSONException ex) {
            Logger.getLogger(WeatherTab.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void updateWeatherData(final String city) {
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(city);
                System.out.println("Update  thread");

                if (json == null) {
                    System.out.println("Error  updating");

                } else {

                    System.out.println(json);

                    new Thread() {
                        public void run() {
                            renderWeather(json);

                        }
                    }.start();

                }

            }
        }.start();
    }

    private void setWeatherIcon(int id) {
        String icon = "";
        if (id >= 200 && id <= 232) {
            icon = "11";
        } else if (id >= 300 && id <= 321) {
            icon = "09";
        } else if (id >= 500 && id <= 504) {
            icon = "10";
        } else if (id == 511) {
            icon = "13";
        } else if (id >= 520 && id <= 521 || id == 531) {
            icon = "09";
        } else if (id >= 600 && id <= 622) {
            icon = "13";
        } else if (id >= 700 && id <= 781) {
            icon = "50";
        } else if (id == 800) {
            icon = "01";
        } else if (id == 801) {
            icon = "02";
        } else if (id == 802) {
            icon = "03";
        } else if (id == 803) {
            icon = "04";
        } else if (id == 804) {
            icon = "04";
        }

        try {
            URL url = new URL("http://openweathermap.org/img/w/" + icon + "d.png");
            Image image = ImageIO.read(url);
            weatherIcon.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}
