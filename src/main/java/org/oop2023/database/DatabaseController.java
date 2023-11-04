package org.oop2023.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseController {
    private static Connection connection;
    private static Statement statement;

    /**
     * Establish a connection to the database.
     */
    public static void start() {
        System.out.println("Establishing connection to SQLite...");
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:resources/db/dict.db");
            statement = connection.createStatement();
            System.out.println("Connection to SQLite has been established.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the connection to the database.
     */
    public static void stop() {
        try {
            statement.close();
            connection.close();
            System.out.println("Connection to SQLite has been closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the statement of the connection.
     * @return The statement of the connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Get the statement of the connection.
     * @return The statement of the connection
     */
    public static Statement getStatement() {
        return statement;
    }
}
