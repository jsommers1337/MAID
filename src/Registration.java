/**
 * File Name: Registration.java
 * Author: Iye Kargbo
 * Purpose: The purpose of this program is to register a user account for RunApp.
 * Due Date: 11/15/2023
 */

import javax.swing.*;
import java.awt.*;
import java.util.regex.*;

public class Registration extends JDialog {

    // initializes the JPanel and the necessary text fields, password fields, and buttons
    private JPanel registrationPanel;
    private JTextField textFieldUsername;
    private JTextField textFieldEmail;
    private JPasswordField passwordFieldPassword;
    private JPasswordField passwordFieldConfirm;
    private JButton registerButton;
    private JButton cancelButton;

    // variable declaration
    public Account account;

    // constructor for the JFrame "owner" to display the "Register" dialog
    public Registration(JFrame owner) {
        super(owner);

        // sets the characteristics of the dialog
        setTitle("Register");
        setContentPane(registrationPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);

        // displays dialog in the center of the frame
        setLocationRelativeTo(owner);

        // disposes the dialog when a user initiates a close
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // implements an action listener for when the register button is clicked
        registerButton.addActionListener(e -> {
            // registers a user's account when the register button is clicked to call the registerAccount method
            registerAccount();
        });

        // implements an action listener for when the cancel button is clicked to direct the user to the login page
        cancelButton.addActionListener(e -> {
            dispose();
            new Login(null);
        });

        // shows the dialog
        setVisible(true);
    }

    // registerAccount method to register a user
    private void registerAccount() {

        // reads the fields and returns what is in them
        String username = textFieldUsername.getText();
        String email = textFieldEmail.getText();
        String password = String.valueOf(passwordFieldPassword.getPassword());
        String confirmPassword = String.valueOf(passwordFieldConfirm.getPassword());

        // checks and ensures that all fields are filled out before processing the registration
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            // displays an error message and allows the user to try again
            JOptionPane.showMessageDialog(this, "All fields are required",
                    "Please try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // notifies the user of an invalid the password
        if (!passwordValidation(password)) {
            // displays an error message with the required password specifications
            JOptionPane.showMessageDialog(this, """
                            Password MUST contain:\s
                            8 characters\s
                            At least one lowercase letter\s
                            At least one uppercase letter\s
                            At least one number\s
                            At least one special character from !@#$%^&+=""",
                    "Invalid Password", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // checks and ensures that the passwords match
        if (!password.equals(confirmPassword)) {
            // displays an error message and allows the user to try again
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Please try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // checks if the credentials already exist in the database and notifies the user
        if (SQL.credentialsExist(username, email)) {
            // displays an error message and asks the user to log in
            JOptionPane.showMessageDialog(this, "That username/email already exists", "Please try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // calls the storeAccount method to save account information to the database
        account = SQL.accountRegistration(username, email, password);

        // checks if an account was successfully created. If the account is null, then the registration failed
        if (account != null) {
            dispose();
            System.out.println(account.getUsername() + " has been successfully registered!");
            new Login(null);
        }

        // displays an error message if the account registration was not successful
        else {
            JOptionPane.showMessageDialog(this, "Account Registration Failed", "Please try again", JOptionPane.ERROR_MESSAGE);
        }
    }

    // method to validate password
    private boolean passwordValidation(String password) {

        // checks and ensures that the password meets the strength specifications and is therefore valid
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[!@#$%^&+=])" + "(?=\\S+$).{8,}$";

        // compiles the regular expression
        Pattern pattern = Pattern.compile(regex);

        // matches the input password to the regular expression
        Matcher matcher = pattern.matcher(password);

        // if the password does not match the regular expression, the password is not valid
        return matcher.matches();
    }

    // main method
    public static void main(String[] args) {
        // creates an instance of the registration class
        new Registration(null);
    }
}
