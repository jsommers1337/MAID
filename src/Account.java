/**
 * File Name: Account.java
 * Author: Iye Kargbo
 * Purpose: The purpose of this program is to initialize the class, Account, which stores the account information.
 * Due Date: 11/15/2023
 */

public class Account {
    // variable declaration
    protected String username;
    protected String email;
    protected String password;

    // constructor
    public Account(){
    }

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // get methods
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // set methods
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
