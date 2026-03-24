package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.mysql.cj.protocol.Resultset;

public class MySqlDbConnector {
    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/hr_db";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root1234";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Connected to MySQL database successfully.");
            } catch (java.sql.SQLException e) {
                System.err.println("Failed to connect to MySQL database: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("MySQL database connection closed.");
            } catch (java.sql.SQLException e) {
                System.err.println("Failed to close MySQL database connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    public static ResultSet executeQuery(String query) {
        try {
            Statement statement = getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (java.sql.SQLException e) {
            System.err.println("Failed to execute query: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // exe (insert,update,remove)
    public static int executeUpdate(String query) {
        try {
            Statement statement = getConnection().createStatement();
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.err.println("Failed to execute update: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) throws SQLException {
        connection = MySqlDbConnector.getConnection();
        ResultSet resultSet = MySqlDbConnector.executeQuery("SELECT * FROM employees");
        try {
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.err.println();
            }
        } catch (SQLException e) {
            System.err.println("Error processing ResultSet: " + e.getMessage());
            e.printStackTrace();
        } 

        MySqlDbConnector.closeConnection();
    }
}
