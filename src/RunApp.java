/**
 * File Name: RunApp.java
 * Author: Ryan Jones
 * Purpose: The purpose of this class is to run the application.
 * Due Date: 12/4/2023
 */
import javax.swing.*;

public class RunApp {
    public static void main(String[] args) {
        // creates an instance of the application
        while (true) {
            new LandingPage(null);
            if (LandingPage.getStatusCode() == 1) {
                int userID = LandingPage.getUserID();
                new MainPage(null, userID);
            }
            System.exit(0);
        }
    }
}
