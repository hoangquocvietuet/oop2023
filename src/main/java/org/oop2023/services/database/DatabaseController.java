package org.oop2023.services.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseController {
    private static Connection connection;
    private static Statement statement;

    /**
     * Establish a connection to the database.
     */
    public static void start() throws RuntimeException {
        System.out.println("Establishing connection to SQLite...");
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/org/oop2023/db/dict.db");
            statement = connection.createStatement();
            System.out.println("Connection to SQLite has been established.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish connection to SQLite.");
        }
    }

    /**
     * Close the connection to the database.
     */
    public static void stop() throws RuntimeException {
        try {
            statement.close();
            connection.close();
            System.out.println("Connection to SQLite has been closed.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to close connection to SQLite.");
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
