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
import java.util.logging.Handler;
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

    private JFrame frame;
    private JPanel weatherPanel;
    private JLabel cityLabel;
    private JLabel updatedText;
    private JLabel weatherIcon;
    private JLabel temperature;
    private JLabel detail;
    private JLabel cityInputLabel;
    private JTextField cityInputField;
    private JButton cityButton;

    private BoxLayout boxLayout;

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
                String cityname = cityInputField.getText();
                System.out.println("City name = " + cityname);
                System.out.println("Running action performed: " + e);

                FetchThread ft = new FetchThread(cityname);

                System.out.println(ft.getJSON());

                renderWeather(ft.getJSON());

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

    public void renderWeather(JSONObject json) {
        try {
            cityLabel.setText(json.getString("name").toUpperCase(Locale.US));
        } catch (Exception e) {
            System.out.println("Error setting text");
        }
    }
}
