/**
 * File Name: ViewHealthConditions.java
 * Author: Ryan Jones
 * Purpose: The purpose of this class is to view health conditions of current user.
 * Due Date: 12/4/2023
 */

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewHealthCondition extends JDialog {
    private JPanel recordViewPanel;
    private JLabel onsetText;
    private JLabel ailmentNameText;
    private JLabel isChronicText;
    private JLabel dateReportedText;
    private JLabel isMedicatedText;
    private JLabel medicineNameText;
    private JButton newConditionButton;
    private JButton backToMainButton;

    // constructor for the JFrame "owner" to display the "Register" dialog
    public ViewHealthCondition(JFrame owner, int userID) throws SQLException {
        super(owner);
        SQL sql = new SQL();
        ResultSet resultSet;
        resultSet = sql.selectHealthConditions(userID);
        Date onsetDate = null;
        String ailmentName = null;
        boolean isChronic = false;
        Date reportedDate = null;
        boolean isMedicated = false;
        String medicineName = null;
        while (resultSet.next()) {
            onsetDate = resultSet.getDate(2);
            ailmentName = resultSet.getString(3);
            isChronic = resultSet.getBoolean(4);
            reportedDate = resultSet.getDate(5);
            isMedicated =  resultSet.getBoolean(6);
            medicineName = resultSet.getString(7);
        }

        onsetText.setText(String.valueOf(onsetDate));
        ailmentNameText.setText(ailmentName);
        isChronicText.setText(String.valueOf(isChronic));
        dateReportedText.setText(String.valueOf(reportedDate));
        isMedicatedText.setText(String.valueOf(isMedicated));
        medicineNameText.setText(medicineName);

        setTitle("Health Records");
        setContentPane(recordViewPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);
        // displays dialogue in the center of the frame
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // implements an action listener for when the update button is clicked
        newConditionButton.addActionListener(e -> {
            // registers a user's account when the register button is clicked
            //ADD CODE FOR UPDATE METHOD
        });


        // implements an action listener for when the "back to main" button is clicked
        backToMainButton.addActionListener(e -> {
            dispose();
        });
        setVisible(true);
    }
}
