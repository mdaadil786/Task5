

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScrapingWithGUI{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(WebScraperGUI::new);
    }
}

class WebScraperGUI {
    private final JFrame frame;
    private final JTextField urlField;
    private final JTextArea resultArea;
    private final JButton scrapeButton;

    public WebScraperGUI() {
        // Set up the frame
        frame = new JFrame("Web Scraper (Without JSoup)");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // URL input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel urlLabel = new JLabel("Enter URL: ");
        urlField = new JTextField("https://example.com");
        scrapeButton = new JButton("Scrape");
        inputPanel.add(urlLabel, BorderLayout.WEST);
        inputPanel.add(urlField, BorderLayout.CENTER);
        inputPanel.add(scrapeButton, BorderLayout.EAST);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Set up button action
        scrapeButton.addActionListener(new ScrapeAction());

        // Make the frame visible
        frame.setVisible(true);
    }

    private class ScrapeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String url = urlField.getText().trim();

            // Validate URL
            if (url.isEmpty() || (!url.startsWith("http://") && !url.startsWith("https://"))) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid URL starting with http:// or https://", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                resultArea.setText("Scraping website... Please wait.\n");
                String html = fetchHTML(url);
                String extractedData = extractHeadlines(html);
                resultArea.setText(extractedData);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error while scraping: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private String fetchHTML(String urlString) throws Exception {
            StringBuilder content = new StringBuilder();

            // Open a connection to the URL
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Read the response
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            return content.toString();
        }

        private String extractHeadlines(String html) {
            // Regex to find headings (e.g., <h1>, <h2>, <h3>)
            Pattern pattern = Pattern.compile("<h[1-3]>(.*?)</h[1-3]>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(html);

            StringBuilder result = new StringBuilder("Extracted Headlines:\n\n");
            while (matcher.find()) {
                result.append("- ").append(matcher.group(1).trim()).append("\n");
            }

            if (result.toString().equals("Extracted Headlines:\n\n")) {
                result.append("No headlines found.");
            }
            return result.toString();
        }
    }
}
