/**
 * File Name: ViewPerson.java
 * Author: Ryan Jones
 * Purpose: The purpose of this class is to view personal information of current user.
 * Due Date: 12/4/2023
 */

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewPerson extends JDialog{
    private JPanel recordViewPanel;
    private JLabel firstNameText;
    private JLabel lastNameText;
    private JLabel heightText;
    private JLabel ageText;
    private JLabel genderText;
    private JLabel sexText;
    private JLabel maritalStatusText;
    private JLabel insuranceCompanyText;
    private JButton backToMainButton;
    private JButton updateFirstNameButton;
    private JButton updateLastNameButton;
    private JButton updateHeightButton;
    private JButton updateAgeButton;
    private JButton updateGenderButton;
    private JButton updateSexButton;
    private JButton updateMaritalStatusButton;
    private JButton updateInsuranceCompanyButton;

    // constructor for the JFrame "owner" to display the "Register" dialog
    public ViewPerson(JFrame owner, int userID) throws SQLException {
        super(owner);
        SQL sql = new SQL();
        ResultSet resultSet;
        resultSet = sql.selectPersons(userID);
        String firstName = null;
        String lastName = null;
        double height = 0;
        int age = 0;
        String gender = null;
        String sex = null;
        String maritalStatus = null;
        String insuranceCompany =null;
        if (resultSet.next()) {
            firstName = resultSet.getString("FirstName");
            lastName = resultSet.getString("LastName");
            height = resultSet.getDouble("Height");
            age = resultSet.getInt("Age");
            gender =  resultSet.getString("Gender");
            sex = resultSet.getString("Sex");
            maritalStatus = resultSet.getString("MaritalStatus");
            insuranceCompany = resultSet.getString("InsuranceComp");
        }


        //Methods for system integration
        firstNameText.setText(firstName);
        lastNameText.setText(lastName);
        heightText.setText(String.valueOf(height));
        ageText.setText(String.valueOf(age));
        genderText.setText(gender);
        sexText.setText(sex);
        maritalStatusText.setText(maritalStatus);
        insuranceCompanyText.setText(insuranceCompany);

        setTitle("Personal Information");
        setContentPane(recordViewPanel);
        setMinimumSize(new Dimension(750, 500));
        setModal(true);
        // displays dialogue in the center of the frame
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        updateFirstNameButton.addActionListener(e -> {
            new UpdatePerson(owner, userID, "FirstName");
            dispose();
        });
        updateLastNameButton.addActionListener(e -> {
            new UpdatePerson(owner, userID, "LastName");
            dispose();
        });
        updateAgeButton.addActionListener(e -> {
            new UpdatePerson(owner, userID, "Age");
            dispose();
        });
        updateHeightButton.addActionListener(e -> {
            new UpdatePerson(owner, userID, "Height");
            dispose();
        });
        updateGenderButton.addActionListener(e -> {
            new UpdatePerson(owner, userID, "Gender");
            dispose();
        });
        updateSexButton.addActionListener(e -> {
            new UpdatePerson(owner, userID, "Sex");
            dispose();
        });
        updateMaritalStatusButton.addActionListener(e -> {
            new UpdatePerson(owner, userID, "MaritalStatus");
            dispose();
        });
        updateInsuranceCompanyButton.addActionListener(e -> {
            new UpdatePerson(owner, userID, "InsuranceCompany");
            dispose();
        });

        // implements an action listener for when the "back to main" button is clicked
        backToMainButton.addActionListener(e -> {
            dispose();
        });
        setVisible(true);
    }
}
