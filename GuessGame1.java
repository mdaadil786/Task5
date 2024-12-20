
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessGame1 {
    public static void main(String[] args) {
            JFrame frame = new JFrame("Guess the Number Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new GridLayout(5, 1, 10, 10));
            Random random = new Random();
            final int[] randomNumber = {random.nextInt(100) + 1};
            JLabel instructionLabel = new JLabel("Guess a number between 1 and 100:", SwingConstants.CENTER);
            JTextField guessField = new JTextField();
            JButton guessButton = new JButton("Submit Guess");
            JLabel resultLabel = new JLabel("Make your guess!", SwingConstants.CENTER);
            JButton restartButton = new JButton("Restart Game");
            restartButton.setEnabled(false);
            frame.add(instructionLabel);
            frame.add(guessField);
            frame.add(guessButton);
            frame.add(resultLabel);
            frame.add(restartButton);

            guessButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int guess = Integer.parseInt(guessField.getText());
                        if (guess < 1 || guess > 100) {
                            resultLabel.setText("Please enter a number between 1 and 100.");
                        } else if (guess < randomNumber[0]) {
                            resultLabel.setText("Too low! Try again.");
                        } else if (guess > randomNumber[0]) {
                            resultLabel.setText("Too high! Try again.");
                        } else {
                            resultLabel.setText("Congratulations! You guessed the number.");
                            guessButton.setEnabled(false);
                            restartButton.setEnabled(true);
                        }
                    } catch (NumberFormatException ex) {
                        resultLabel.setText("Invalid input. Enter a number between 1 and 100.");
                    }
                }
            });

            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    randomNumber[0] = random.nextInt(100) + 1;
                    guessField.setText("");
                    resultLabel.setText("Make your guess!");
                    guessButton.setEnabled(true);
                    restartButton.setEnabled(false);
                }
            });
            frame.setVisible(true);
        }
    }

