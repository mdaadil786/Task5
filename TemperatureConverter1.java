

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
       public class TemperatureConverter1 {
        public static void main(String[] args) {
            JFrame frame = new JFrame("Temperature Converter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setSize(400, 250);
            frame.setLayout(new GridLayout(5, 2, 10, 10));

            JLabel inputLabel = new JLabel("Enter Temperature:");
            JTextField inputField = new JTextField();


            JLabel fromLabel = new JLabel("From:");
            String[] units = {"Celsius", "Fahrenheit", "Kelvin"};
            JComboBox<String> fromDropdown = new JComboBox<>(units);

            JLabel toLabel = new JLabel("To:");
            JComboBox<String> toDropdown = new JComboBox<>(units);

            JButton convertButton = new JButton("Convert");

            JLabel resultLabel = new JLabel("Result:");
            JTextField resultField = new JTextField();
            resultField.setEditable(false);

            frame.add(inputLabel);
            frame.add(inputField);
            frame.add(fromLabel);
            frame.add(fromDropdown);
            frame.add(toLabel);
            frame.add(toDropdown);
            frame.add(new JLabel());
            frame.add(convertButton);
            frame.add(resultLabel);
            frame.add(resultField);

            convertButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        double inputTemp = Double.parseDouble(inputField.getText());
                        String fromUnit = (String) fromDropdown.getSelectedItem();
                        String toUnit = (String) toDropdown.getSelectedItem();
                        double resultTemp = convertTemperature(inputTemp, fromUnit, toUnit);
                        resultField.setText(String.format("%.2f", resultTemp));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            frame.setVisible(true);
        }
        private static double convertTemperature(double temp, String from, String to) {
            if (from.equals(to)) {
                return temp;
            }

            double tempInCelsius;
            switch (from) {
                case "Fahrenheit":
                    tempInCelsius = (temp - 32) * 5 / 9;
                    break;
                case "Kelvin":
                    tempInCelsius = temp - 273.15;
                    break;
                default:
                    tempInCelsius = temp;
            }
            switch (to) {
                case "Fahrenheit":
                    return tempInCelsius * 9 / 5 + 32;
                case "Kelvin":
                    return tempInCelsius + 273.15;
                default:
                    return tempInCelsius;
            }
        }
    }

