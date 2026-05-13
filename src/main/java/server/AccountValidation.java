package server;

import server.database.DatabaseManager;

import javax.swing.*;
import java.sql.ResultSet;

/**
 * Class for account validation
 */
public class AccountValidation {
    /**
     * Checks if the login information is valid
     * @param username Username we are checking
     * @param password Password we are checking
     * @return True if the login is valid, and false if not
     */
    public static boolean validLogin(String username, String password){
        String[] parameters = { username, password };
        ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM player WHERE username = ? AND password = ?", parameters);
        boolean loginValid = false;

        try{
            if (resultSet.next()){
                loginValid = true;
            }
        }
        catch (Exception e){
            System.out.println("Error with logging in!");
        }

        return loginValid;
    }

    /**
     * Checks if the given account exists
     * @param username Username we are checking for
     * @return True if an account with the given name is found, false if not
     */
    public static boolean accountExists(String username){
        String[] parameter = { username };
        ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM player WHERE username = ?", parameter);
        boolean exists = false;

        try{
            if (resultSet.next()){
                exists = true;
            }
        }
        catch (Exception e){
            System.out.println("Error with fetching account");
        }

        return exists;
    }

    /**
     * Sets the given user to be either logged in or not based on the given value
     * @param username Username we are setting to be logged in or not in the database
     * @param value The value of logged in we are setting
     */
    public static void setLoggedIn(String username, boolean value){
        String[] parameters = { username };
        int loggedInStatus = value? 1 : 0;
        DatabaseManager.executeUpdate("UPDATE player SET logged_in = " + loggedInStatus + " WHERE username = ?", parameters);
    }

    /**
     * Checks if the user isn't already logged in
     * @param username Username of the account we are checking
     * @return True if the user isn't already logged in, false if the user is logged in
     */
    public static boolean notAlreadyLoggedIn(String username){
        String[] parameters = { username };
        ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM player WHERE username = ?", parameters);

        boolean alreadyLoggedIn = false;

        try{
            if (resultSet.next()){
                alreadyLoggedIn = resultSet.getInt("logged_in") == 1;
            }
        }
        catch (Exception e){
            System.out.println("Error with logging in!");
        }

        return !alreadyLoggedIn;
    }

    /**
     * Checks if the registration is valid, it is if there is not a user with the given username in the database yet
     * @param username Username of the account we are trying to create
     * @param password Password of the account we are trying to create
     * @return True if the user has been created successfully, false othervise
     */
    public static boolean validRegistration(String username, String password){
        String[] parameters = { username };
        ResultSet resultSet = DatabaseManager.executeQuery("SELECT * FROM player WHERE username = ?", parameters);
        boolean registrationValid = true;

        try{
            if (resultSet.next()){
                System.out.println("1");
                registrationValid = false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if(registrationValid){
            String[] newAccountParameters = { username, password };
            DatabaseManager.executeUpdate("INSERT INTO player (username, password) VALUES(?, ?)", newAccountParameters);
        }

        return registrationValid;
    }
}