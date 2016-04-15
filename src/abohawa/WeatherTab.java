/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abohawa;

import com.google.gson.Gson;
import java.awt.Component;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
    private final JTextArea cityLabel;
    private final JTextArea updatedText;
    private final JLabel weatherIcon;
    private final JTextArea temperature;
    private final JTextArea detail;
    private final JLabel cityInputLabel;
    private final JButton cityButton;
    private JTextField cityInputField;
    private JSONObject weather;
    private final BoxLayout boxLayout;

    WeatherTab() {
        //Declaration
        cityButton = new JButton("Ok");
        cityInputLabel = new JLabel("Enter City Name:");
        cityInputField = new JTextField(20);
        cityLabel = new JTextArea(1, 20);
        updatedText = new JTextArea(3, 20);
        weatherIcon = new JLabel();
        temperature = new JTextArea(1, 20);
        detail = new JTextArea(3, 20);
        weatherPanel = new JPanel();
        frame = new JFrame("অাবহাওয়া");

        //Box Layout
        boxLayout = new BoxLayout(weatherPanel, BoxLayout.Y_AXIS);
        weatherPanel.setLayout(boxLayout);
        weatherPanel.setBorder(new EmptyBorder(new Insets(10, 10, 60, 10)));

        cityInputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cityButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Adding items to panel
        weatherPanel.add(cityInputLabel);
        weatherPanel.add(cityInputField);
        weatherPanel.add(cityButton);
        weatherPanel.add(cityLabel);
        weatherPanel.add(temperature);
        weatherPanel.add(detail);
        weatherPanel.add(updatedText);
        weatherPanel.add(weatherIcon);
        detail.setEditable(false);
        cityLabel.setEditable(false);
        temperature.setEditable(false);
        updatedText.setEditable(false);

        frame.add(weatherPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Action Listener
        cityButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String cityName = cityInputField.getText();

                int length = cityName.length();
                if (length == 0) {
                    cityButton.setEnabled(false);
                    JOptionPane.showMessageDialog(null,
                            "Please enter city name.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    cityButton.setEnabled(true);

                } else if (cityName.replaceAll("\\s", "").length() == 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a city name.", "City name", JOptionPane.ERROR_MESSAGE);
                } else {
                    cityName = cityName.replaceAll("\\s", "");

                    updateWeatherData(cityName);

                }

            }
        });
    }

    //rendering the weather
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
            updatedText.setText("Last updated \n" + updatedOn);

            temperature.setText(temp);

            detail.setText(detailText);
            setWeatherIcon(id);
        } catch (JSONException ex) {
            Logger.getLogger(WeatherTab.class.getName()).log(Level.SEVERE, null, ex);
        }

        cityButton.setEnabled(true);

    }

    private void updateWeatherData(final String city) {
        Gson gson = new Gson();
        cityButton.setEnabled(false);

        new Thread() {
            public void run() {

                final JSONObject json = RemoteFetch.getJSON(city);

                if (json == null) {

                    JOptionPane.showMessageDialog(null,
                            "Check Your Internet Connection.",
                            "No Internet",
                            JOptionPane.ERROR_MESSAGE);
                    cityButton.setEnabled(true);

                } else {

                    new Thread() {
                        public void run() {

                            String weatherString = json.toString();
                            WeatherData cityData = gson.fromJson(weatherString, WeatherData.class);
                            renderWeather(json);
                            System.out.println(cityData.main);

                        }
                    }.start();

                }

            }
        }.start();

    }

    //Weather Icon
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
            weatherIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        } catch (Exception e) {
            // System.out.println("Error");
        }
    }
}
