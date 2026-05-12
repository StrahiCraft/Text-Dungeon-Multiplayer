package server.database;

import java.sql.*;

/**
 * Class for managing the database
 */
public class DatabaseManager {
    /**
     * Username of the account for the database
     */
    private static final String username = "master";
    /**
     * Password of the account for the database
     */
    private static final String password = "password";
    /**
     * URL of the database
     */
    private static final String url = "jdbc:mysql://localhost:3306/online_minesweeper";

    /**
     * The established connection to the database
     */
    private static Connection connection;

    /**
     * Tries to connect to the database using the given username, password and url.
     * Should be called from the server on startup.
     */
    public static boolean connect(){
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Disconnects the database from the program.
     * Call when closing down the server
     */
    public static void disconnect(){
        try{
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Executes the given query with no parameters
     * @param query Query to be executed
     * @return The result set from the executed query
     */
    public static ResultSet executeQuery(String query){
        return executeQuery(query, new String[0]);
    }

    /**
     * Executes the given query with the given parameters
     * @param query Query to be executed
     * @param parameters Parameters used in the query
     * @return The result set from the executed query
     */
    public static ResultSet executeQuery(String query, String[] parameters){
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            for (int i = 0; i < parameters.length; i++){
                statement.setString(i + 1, parameters[i]);
            }
            resultSet = statement.executeQuery();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Executes an update to the database based on the given query with no parameters
     * @param query Query used for the update
     * @return True if the query was executed successfully, false if not
     */
    public static boolean executeUpdate(String query){
        return executeUpdate(query, new String[0]);
    }

    /**
     * Executes an update to the database based on the given query with the given parameters
     * @param query Query used for the update
     * @param parameters Parameters used in the query
     * @return True if the query was executed successfully, false if not
     */
    public static boolean executeUpdate(String query, String[] parameters){
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < parameters.length; i++){
                statement.setString(i + 1, parameters[i]);
            }
            statement.executeUpdate();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}