import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class submitMAID extends JDialog {
    private JPanel submitMAIDPanel;
    private JTextField symptomsText;
    private JButton submitButton;
    private JButton returnToMenuButton;
    public submitMAID(JFrame owner, int userID) {
        super(owner);
        setTitle("MAID Service");
        setContentPane(submitMAIDPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);
        // displays dialogue in the center of the frame
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // implements an action listener for when the update button is clicked
        submitButton.addActionListener(e -> {
            String symptoms = symptomsText.getText();
            boolean isValid = checkErrors(symptoms);
            if (isValid) {
                String chatGPTResponse = chatGPT(userID, symptoms);
                new MAIDResponse(owner, chatGPTResponse);
            } else {
                JOptionPane.showMessageDialog(submitMAID.this, "Could Not Parse Symptoms", "Please try again", JOptionPane.ERROR_MESSAGE);
            }
        });
        // implements an action listener for when the "back to main" button is clicked
        returnToMenuButton.addActionListener(e -> {
            dispose();
        });
        setVisible(true);
    }

    private boolean checkErrors(String symptomText) {
        if (symptomText.isEmpty()) {
            // displays an error message and allows the user to try again
            JOptionPane.showMessageDialog(this, "Field cannot be blank",
                    "Please try again", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static String chatGPT(int userID, String symptoms) {
        try {
            String apiKey = "Ysk-3ayuSJM2CGfTulejYQWZT3BlbkFJLcEJvqyWtzgxcmSynr6p";
            String endpoint = "https://api.openai.com/v1/engines/davinci-codex/completions";

            // Set up the HTTP connection
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            String healthConditions = retrieveHealthConditions(userID);
            String prompt = String.format("An individual has a history of the following health conditions (%s) " +
                            "and current symptoms: %s. Provide possible diagnosis and causes.", healthConditions, symptoms);

            String requestBody = "{ \"messages\": [{ \"role\": \"system\", \"content\": \"You are a " +
                    "helpful assistant.\" }, { \"role\": \"user\", \"content\": \"" + prompt + "\" }] }";

            // Send the request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            } finally {
                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Operation Failed. Please Try Again.";
    }

    public static String retrieveHealthConditions(int userID) {
        String healthConditions = null;
        SQL sql = new SQL();
        ResultSet resultSet;
        resultSet = sql.selectHealthConditions(userID);
        String ailmentName = null;
        try {
            while (resultSet.next()) {
                ailmentName = resultSet.getString("AilmentName");
                healthConditions = ailmentName + ", " + healthConditions;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return healthConditions;
    }
}

