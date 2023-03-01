package com.example.gameless;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        //Define database credentials and URL
        String databaseName = "fbla";
        String databaseUser = "root";
        String databasePassword = "fblaboratory";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            //Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Connect to the database using the credentials and URL
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            //Print the stack trace and cause of any exceptions
            e.printStackTrace();
            e.getCause();
        }

        // Return the database connection object
        return databaseLink;
    }
}
