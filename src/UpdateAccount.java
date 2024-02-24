/**
 * File Name: UpdateAccount.java
 * Author: Ryan Jones
 * Purpose: The purpose of this class is to handle Account update requests for current user.
 * Due Date: 12/4/2023
 */

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UpdateAccount extends JDialog {
    private JPanel updateAccountPanel;
    private JButton confirmUpdateButton;
    private JButton cancelUpdateButton;
    private JTextField updateText;
    private JLabel updateField;

    // constructor for the JFrame "owner" to display the "Register" dialog
    public UpdateAccount(JFrame owner, int userID, String updateType) {
        super(owner);
        setTitle("Update Account Information");
        setContentPane(updateAccountPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);
        // displays dialogue in the center of the frame
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        switch (updateType) {
            case "Username":
                updateUsername(userID);
                break;
            case "Email":
                updateEmail(userID);
                break;
            case "Password":
                updatePassword(userID);
                break;
        }
        // implements an action listener for when the "back to main" button is clicked
        cancelUpdateButton.addActionListener(e -> {
            dispose();
        });
        setVisible(true);
    }

    public void updateUsername(int userID) {
        updateField.setText("Username:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String userName = updateText.getText();
                SQL.updateUserName(userID, userName);
                dispose();
            }
        });
    }

    public void updateEmail(int userID) {
        updateField.setText("Email:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String email = updateText.getText();
                SQL.updateEmail(userID, email);
                dispose();
            }
        });
    }

    public void updatePassword(int userID) {
        updateField.setText("Password:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String password = updateText.getText();
                SQL.updatePassword(userID, password);
                dispose();
            }
        });
    }

    private boolean checkErrors(String updateText) {
        if (updateText.isEmpty()) {
            // displays an error message and allows the user to try again
            JOptionPane.showMessageDialog(this, "Field cannot be blank",
                    "Please try again", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
