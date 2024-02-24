/**
 * File Name: SQL.java
 * Author: Ryan Jones
 * Purpose: The purpose of this program is to handle MAID SQL syntax.
 * Due Date: 11/22/2023
 */
import java.sql.*;

public class SQL {

    // declares final variables for database connection
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    static final String USERNAME = "root";
    static final String PASSWORD = "3T@s7OL!0r2U";

    // more variable declaration
    public static boolean usernameExists;
    public static boolean emailExists;

    // this method creates a mysql database called MAID
    public static void createDatabase() {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // sql query to create the MAID database
            String sql = "CREATE DATABASE IF NOT EXISTS MAID2";
            statement.executeUpdate(sql);

            // closes the statement and connection
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // this method creates the Accounts table in the MAID database
    public static void createTables() {
        // calls the createDatabase method to create the MAID database
        createDatabase();
        createAccountsTable();
        createPersonsTable();
        createHealthConditionsTable();

    }
    public static void createAccountsTable() {
        try{
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            // sql query to create the accounts table
            String query = "CREATE TABLE IF NOT EXISTS Accounts (UserID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "Username VARCHAR(255) NOT NULL UNIQUE, " +
                    "Email VARCHAR(255) NOT NULL UNIQUE, " +
                    "Password VARCHAR(255) NOT NULL)";
            statement.executeUpdate(query);

            // closes the statement and connection
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createPersonsTable() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String sql = "USE MAID2";
            statement.executeUpdate(sql);
            String query = "CREATE TABLE IF NOT EXISTS Persons" +
                    "(UserID INTEGER not NULL," +
                    "FirstName VARCHAR(255) not NULL," +
                    "LastName VARCHAR(255) not NULL," +
                    "Height DOUBLE not NULL," +
                    "Age INTEGER not NULL," +
                    "Gender VARCHAR(255) not NULL," +
                    "Sex VARCHAR(255) not NULL," +
                    "MaritalStatus VARCHAR(255) not NULL," +
                    "InsureComp VARCHAR(255) not NULL," +
                    "PRIMARY KEY ( UserID ))";
            statement.executeUpdate(query);

            // closes the statement and connection
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createHealthConditionsTable() {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "CREATE TABLE IF NOT EXISTS HealthConditions" +
                    "(UserID INTEGER not NULL," +
                    "OnsetDate DATETIME not NULL," +
                    "AilmentName VARCHAR(255) not NULL," +
                    "IsChronic BOOLEAN not NULL," +
                    "ReportedDate DATETIME not NULL," +
                    "IsMedicated BOOLEAN not NULL," +
                    "MedicineName VARCHAR(255) not NULL," +
                    "PRIMARY KEY ( UserID ))";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // this method stores the account information provided by the user during registration to the Accounts table
    public static Account accountRegistration(String username, String email, String password) {
        // returns null if the method fails to add the account information to the database
        Account account = null;

        // calls the createTables method to create the Accounts table
        createTables();

        // the code below connects to the mysql database and adds the account information to the database
        try{
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // creates a PreparedStatement object to store sql statements
            PreparedStatement preparedStatement;

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            if (!credentialsExist(username, email)) {
                // sql query to add account information to the table
                String query = "INSERT INTO Accounts (Username, Email, Password)" + "values (?, ?, ?)";

                // variables to insert into the query
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                // executes the sql query and inserts a row into the accounts table
                int addedRows = preparedStatement.executeUpdate();

                // completes the account object if the row is successfully added
                if (addedRows > 0) {
                    account = new Account(username, email, password);
                    account.setUsername(username);
                    account.setEmail(email);
                    account.setPassword(password);
                }
            }

            // closes the connection
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // returns the account object if the account information was successfully added to the database
        return account;
    }

    // this method ensures that a username/email already exist in the database
    public static boolean credentialsExist(String username, String email) {
        // calls the createTables method to create the Accounts table
        createTables();

        try{
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // creates a PreparedStatement object to store sql statements
            PreparedStatement preparedStatement;

            // creates a ResultSet object
            ResultSet resultSet;

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            // sql query to check if a username has already been added to the table
            String usernameQuery = "SELECT * FROM Accounts WHERE Username = ?";

            // variable to insert into the query
            preparedStatement = connection.prepareStatement(usernameQuery);
            preparedStatement.setString(1, username);

            // executes the sql query and returns the ResultSet object generated by the query
            resultSet = preparedStatement.executeQuery();

            // sets the usernameExists boolean to true if the username already exists in the database
            usernameExists = resultSet.next();

            // sql query to check if an email has already been added to the table
            String emailQuery = "SELECT * FROM Accounts WHERE Email = ?";

            // variable to insert into the query
            preparedStatement = connection.prepareStatement(emailQuery);
            preparedStatement.setString(1, email);

            // executes the sql query and returns the ResultSet object generated by the query
            resultSet = preparedStatement.executeQuery();

            // sets the emailExists boolean to true if the email already exists in the database
            emailExists = resultSet.next();

            // closes the connection
            preparedStatement.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernameExists || emailExists;
    }

    public static Account accountLogin(String emailOrUsername, String password) {
        Account account = null;

        // calls the createTables method to create the Accounts table
        createTables();

        try{
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            // sql query to check for username and password in the table
            String query = "SELECT * FROM Accounts WHERE Email=? and Password=? OR Username=? and Password=?";

            // creates a PreparedStatement object to store sql statements
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // variables to insert into the query
            preparedStatement.setString(1, emailOrUsername);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, emailOrUsername);
            preparedStatement.setString(4, password);

            // executes the sql query and returns the ResultSet object generated by the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // if the user is found in the database, an account object is created and filled with data from the database
            if (resultSet.next()) {
                account = new Account(emailOrUsername, emailOrUsername, password);
                account.username = resultSet.getString("username");
                account.email = resultSet.getString("email");
                account.password = resultSet.getString("password");
            }
            resultSet.close();

            // closes the connection
            preparedStatement.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    public static int getUserID(String emailOrUsername, String password) {
        try{
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            // sql query to check for username and password in the table
            String query = "SELECT * FROM Accounts WHERE Email=? and Password=? OR Username=? and Password=?";

            // creates a PreparedStatement object to store sql statements
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // variables to insert into the query
            preparedStatement.setString(1, emailOrUsername);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, emailOrUsername);
            preparedStatement.setString(4, password);

            // executes the sql query and returns the ResultSet object generated by the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // if the user is found in the database, an account object is created and filled with data from the database
            if (resultSet.next()) {
                int userID = resultSet.getInt(1);

                // closes the connection
                preparedStatement.close();
                statement.close();
                connection.close();
                return userID;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public ResultSet selectAccountInfo(int userID) {
        System.out.println("UserID: " + userID);
        ResultSet queryResults = null;
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "SELECT * FROM Accounts WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            queryResults = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryResults;
    }
    public static void updateUserName(int userID, String userName) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID2 database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Accounts SET UserName = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEmail(int userID, String email) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Accounts SET Email = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updatePassword(int userID, String password) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Accounts SET Password = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet selectPersons(int userID) {
        ResultSet queryResults = null;
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "SELECT * FROM Persons WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            queryResults = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryResults;
    }
    public static void updateFirstName(int userID, String firstName) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Persons SET FirstName = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateLastName(int userID, String lastName) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Persons SET LastName = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lastName);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateHeight(int userID, double height) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Persons SET Height = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, height);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateAge(int userID, int age) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Persons SET Age = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, age);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateGender(int userID, String gender) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Persons SET Gender = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gender);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateSex(int userID, String sex) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Persons SET Sex = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, sex);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateMaritalStatus(int userID, String maritalStatus) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Persons SET MaritalStatus = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, maritalStatus);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateInsureComp(int userID, String InsureComp) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "UPDATE Persons SET InsureComp = ? WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, InsureComp);
            preparedStatement.setInt(2, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertHealthConditions(int userID, String firstName, String lastName, double height, int age,
                                    String gender, String sex, String maritalStatus, String insureComp) {
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "INSERT INTO HealthConditions values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setDouble(4, height);
            preparedStatement.setInt(5, age);
            preparedStatement.setString(6, gender);
            preparedStatement.setString(7, sex);
            preparedStatement.setString(8, maritalStatus);
            preparedStatement.setString(9, insureComp);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet selectHealthConditions(int userID) {
        ResultSet queryResults = null;
        try {
            // creates a connection with the database
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // creates a statement object to send the sql statements to the database
            Statement statement = connection.createStatement();

            // uses the MAID database to create the Accounts table
            String sql = "USE MAID2";
            statement.executeUpdate(sql);

            String query = "SELECT * FROM HealthConditions WHERE UserID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            queryResults = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryResults;
    }
}
