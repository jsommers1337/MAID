/**
 * File Name: Login.java
 * Author: Iye Kargbo
 * Purpose: The purpose of this program is to log into a user account for RunApp.
 * Due Date: 11/22/2023
 */

import javax.swing.*;
import java.awt.*;

public class Login extends JDialog {

    // initializes the JPanel and the necessary text field, password field, and buttons
    private JPanel loginPanel;
    private JTextField textFieldEmail;
    private JPasswordField passwordFieldPassword;
    private JButton logInButton;
    private JButton signUpButton;

    // variable declaration
    public Account account;
    public int userID;

    // constructor for the JFrame "owner" to display the "Log In" dialog
    public Login(JFrame owner) {
        super(owner);

        // sets the characteristics of the dialog
        setTitle("Log In");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);

        // displays dialog in the center of the frame
        setLocationRelativeTo(owner);

        // disposes the dialog when a user initiates a close
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // implements an action listener for when the login button is clicked
        logInButton.addActionListener(e -> {

            // reads the fields and returns what is in them
            String emailOrUsername = textFieldEmail.getText();
            String password = String.valueOf(passwordFieldPassword.getPassword());

            // checks and ensures that all fields are filled out before processing the registration
            if (emailOrUsername.isEmpty() || password.isEmpty()) {
                // displays an error message and allows the user to try again
                JOptionPane.showMessageDialog(this, "All fields are required",
                        "Please try again", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // logs a user in when the login button is clicked to call the processLogin method
            account = SQL.accountLogin(emailOrUsername, password);

            if (account != null) {
                dispose();
                System.out.println(account.getUsername() + " has been successfully logged in!");
                int userID = SQL.getUserID(emailOrUsername, password);
                LandingPage.setUserID(userID);
            }
            // displays an error message if the account login was not successful
            else {
                JOptionPane.showMessageDialog(Login.this, "Invalid email/username or password", "Please try again", JOptionPane.ERROR_MESSAGE);
            }
        });

        // implements an action listener for when the sign-up button is clicked to direct the user to the registration page
        signUpButton.addActionListener(e -> {
            dispose();
            new Registration(null);
        });

        // shows the dialog
        setVisible(true);
    }
}
