/**
 * File Name: LandingPage.java
 * Author: Ryan Jones
 * Purpose: The purpose of this program is create an initial landing page upon startup.
 * Due Date: 12/4/2023
 */
import javax.swing.*;
import java.awt.*;

public class LandingPage extends JDialog {
    private JPanel landingPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton exitButton;
    public static int userID = 0;
    private static int statusCode = 0;


    public LandingPage(JFrame owner) {
        super(owner);

        // sets the characteristics of the dialog
        setTitle("Landing Page");
        setContentPane(landingPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);

        // displays dialog in the center of the frame
        setLocationRelativeTo(owner);

        // disposes the dialog when a user initiates a close
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loginButton.addActionListener(e -> {
            // takes user to view account info when button is clicked
            new Login(null);
            dispose();
            statusCode = 1;
        });
        registerButton.addActionListener(e -> {
            new Registration(owner);
            statusCode = 1;
        });
        exitButton.addActionListener(e -> {
            // terminates application when exit button is clicked
            dispose();
        });

        // shows the dialog
        setVisible(true);
    }

    public static int getUserID() {
        return userID;
    }
    public static void setUserID(int userID) {
       LandingPage.userID = userID;
    }
    public static int getStatusCode(){
        return statusCode;
    }
}
