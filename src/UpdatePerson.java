/**
 * File Name: ViewPerson.java
 * Author: Ryan Jones
 * Purpose: The purpose of this class is view personal information of current user.
 * Due Date: 12/4/2023
 */
import javax.swing.*;
import java.awt.*;

public class UpdatePerson extends JDialog {
    private JPanel updatePersonPanel;
    private JButton confirmUpdateButton;
    private JButton cancelUpdateButton;
    private JTextField updateText;
    private JLabel updateField;

    public UpdatePerson(JFrame owner, int userID, String updateType) {
        super(owner);
        setTitle("Update Personal Information");
        setContentPane(updatePersonPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);
        // displays dialogue in the center of the frame
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        switch (updateType) {
            case "FirstName":
                updateFirstName(userID);
                break;
            case "LastName":
                updateLastName(userID);
                break;
            case "Age":
                updateAge(userID);
                break;
            case "Height":
                updateHeight(userID);
                break;
            case "Gender":
                updateGender(userID);
                break;
            case "Sex":
                updateSex(userID);
                break;
            case "MaritalStatus":
                updateMaritalStatus(userID);
                break;
            case "InsuranceCompany":
                updateInsuranceCompany(userID);
                break;
        }
        // implements an action listener for when the "back to main" button is clicked
        cancelUpdateButton.addActionListener(e -> {
            dispose();
        });
        setVisible(true);
    }

    public void updateFirstName(int userID) {
        updateField.setText("First Name:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String firstName = updateText.getText();
                SQL.updateFirstName(userID, firstName);
                dispose();
            }
        });
    }
    public void updateLastName(int userID) {
        updateField.setText("Last Name:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String lastName = updateText.getText();
                SQL.updateLastName(userID, lastName);
                dispose();
            }
        });
    }
    public void updateAge(int userID) {
        updateField.setText("Age:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String age = updateText.getText();
                SQL.updateAge(userID, Integer.parseInt(age));
                dispose();
            }
        });
    }
    public void updateHeight(int userID) {
        updateField.setText("Height:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String height = updateText.getText();
                SQL.updateHeight(userID, Double.parseDouble(height));
                dispose();
            }
        });
    }
    public void updateGender(int userID) {
        updateField.setText("Gender:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String gender = updateText.getText();
                SQL.updateGender(userID, gender);
                dispose();
            }
        });
    }
    public void updateSex(int userID) {
        updateField.setText("Sex:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String sex = updateText.getText();
                SQL.updateSex(userID, sex);
                dispose();
            }
        });
    }
    public void updateMaritalStatus(int userID) {
        updateField.setText("Marital Status:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String maritalStatus = updateText.getText();
                SQL.updateMaritalStatus(userID, maritalStatus);
                dispose();
            }
        });
    }
    public void updateInsuranceCompany(int userID) {
        updateField.setText("Insurance Company:");
        confirmUpdateButton.addActionListener(e -> {
            boolean isValid = checkErrors(updateText.getText());
            if (isValid) {
                String insuranceComp = updateText.getText();
                SQL.updateInsureComp(userID, insuranceComp);
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
