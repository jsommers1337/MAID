/**
 * File Name: ViewAccount.java
 * Author: Ryan Jones
 * Purpose: The purpose of this program is to view a RunApp user's account info.
 * Due Date: 12/4/2023
 */

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAccount extends JDialog {
    // initializes the JPanel and the necessary text fields, password fields, and buttons
    private JPanel accountViewPanel;
    private JLabel usernameText;
    private JLabel emailText;
    private JLabel passwordText;
    private JButton backToMainButton;
    private JButton updateUsername;
    private JButton updateEmail;
    private JButton updatePassword;

        // constructor for the JFrame "owner" to display the "Register" dialog
    public ViewAccount(JFrame owner, int userID) throws SQLException {
        super(owner);
        SQL sql = new SQL();
        ResultSet resultSet;
        resultSet = sql.selectAccountInfo(userID);
        String userName = null;
        String email = null;
        String password = null;

        if (resultSet.next()) {
            userName = resultSet.getString("UserName");
            email = resultSet.getString("Email");
            password = resultSet.getString("Password");
        }

        //Methods for system integration
        usernameText.setText(userName);
        emailText.setText(email);
        passwordText.setText(password);


        setTitle("Account Information");
        setContentPane(accountViewPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);
        // displays dialogue in the center of the frame
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // implements an action listener for when the update username button is clicked
        updateUsername.addActionListener(e -> {
            new UpdateAccount(owner, userID,"Username");
            dispose();
        });
        // implements an action listener for when the update  email button is clicked
        updateEmail.addActionListener(e -> {
            new UpdateAccount(owner, userID,"Email");
            dispose();
        });
        // implements an action listener for when the update password button is clicked
        updatePassword.addActionListener(e -> {
            new UpdateAccount(owner, userID,"Password");
            dispose();
        });

        // implements an action listener for when the "back to main" button is clicked
        backToMainButton.addActionListener(e -> {
            dispose();
        });
        setVisible(true);
    }
}
