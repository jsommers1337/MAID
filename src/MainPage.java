/**
 * File Name: Login.java
 * Author: Iye Kargbo
 * Purpose: This program is the main page for RunApp.
 * Due Date: 11/22/2023
 */

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainPage extends JDialog {
    private JPanel mainPanel;
    private JButton accountButton;
    private JButton personButton;
    private JButton conditionButton;
    private JButton maidButton;
    private JButton logoutButton;

    public MainPage(JFrame owner, int userID) {
        super(owner);
        // sets the characteristics of the dialog
        setTitle("Main Page");
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);

        // displays dialog in the center of the frame
        setLocationRelativeTo(owner);

        // disposes the dialog when a user initiates a close
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        accountButton.addActionListener(e -> {
            // takes user to view account info when button is clicked
            try {
                new ViewAccount(owner, userID);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        personButton.addActionListener(e -> {
            // takes user to view personal info when button is clicked
            try {
                new ViewPerson (owner, userID);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        conditionButton.addActionListener(e -> {
            // takes user to view health conditions when conditions button is clicked
            try {
                new ViewHealthCondition(owner, userID);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        maidButton.addActionListener(e -> {
            // takes user to view submit MAID request
             new submitMAID(owner, userID);
        });
        logoutButton.addActionListener(e -> {
            // logs out user from account when the logout button is clicked
            dispose();
        });

        // shows the dialog
        setVisible(true);
    }
}
