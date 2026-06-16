package DataBaseManagement;

import PropertyManager.LoadProperties;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseManager {

    private static final Logger LOGGER = Logger.getLogger(DataBaseManager.class.getName());
    protected static final String CONFIG_FILE_NAME = "config.properties";

    /**
     * Execute a SQL query with proper resource management
     * @param query The SQL query to execute
     * @return true if query executed successfully, false otherwise
     */
    public boolean triggerQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            LOGGER.log(Level.SEVERE, "Query cannot be null or empty");
            return false;
        }

        LoadProperties loadProperties = new LoadProperties();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load database properties
            String dbUrl = loadProperties.loadProperty(CONFIG_FILE_NAME, "db.url");
            String dbUsername = loadProperties.loadProperty(CONFIG_FILE_NAME, "db.username");
            String dbPassword = loadProperties.loadProperty(CONFIG_FILE_NAME, "db.password");

            if (dbUrl == null || dbUsername == null || dbPassword == null) {
                LOGGER.log(Level.SEVERE, "Database configuration is incomplete");
                return false;
            }

            // Establish connection
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            statement = connection.createStatement();

            // Execute query based on type
            if (query.trim().toLowerCase().startsWith("select")) {
                resultSet = statement.executeQuery(query);
                // For SELECT queries, return true if ResultSet is not null
                return resultSet != null;
            } else {
                // For INSERT, UPDATE, DELETE queries
                int rowsAffected = statement.executeUpdate(query);
                LOGGER.log(Level.INFO, "Query executed successfully. Rows affected: {0}", rowsAffected);
                return rowsAffected >= 0; // Return true even for 0 rows (valid scenario)
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL error executing query: " + query, e);
            return false;
        }  catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error executing query", e);
            return false;
        } finally {
            // Close resources in reverse order of creation
            closeQuietly(resultSet);
            closeQuietly(statement);
            closeQuietly(connection);
        }
    }

    /**
     * Close ResultSet quietly without throwing exceptions
     */
    private void closeQuietly(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing ResultSet", e);
            }
        }
    }

    /**
     * Close Statement quietly without throwing exceptions
     */
    private void closeQuietly(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing Statement", e);
            }
        }
    }

    /**
     * Close Connection quietly without throwing exceptions
     */
    private void closeQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing Connection", e);
            }
        }
    }
    /**
     * Ensures the table schema is correct for storing Base64 images
     */
    public void ensureTableSchema() {
        Connection connection = null;
        Statement statement = null;
        try {
            LoadProperties loadProperties = new LoadProperties();
            String dbUrl = loadProperties.loadProperty(CONFIG_FILE_NAME, "db.url");
            String dbUsername = loadProperties.loadProperty(CONFIG_FILE_NAME, "db.username");
            String dbPassword = loadProperties.loadProperty(CONFIG_FILE_NAME, "db.password");

            if (dbUrl != null && dbUsername != null && dbPassword != null) {
                connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                statement = connection.createStatement();
                
                // Alter table to modify generated_qr_code column to LONGTEXT (or MEDIUMTEXT) to hold Base64 string
                // MySQL syntax
                String alterQuery = "ALTER TABLE qrcode_generator MODIFY COLUMN generated_qr_code LONGTEXT";
                try {
                    statement.executeUpdate(alterQuery);
                    LOGGER.log(Level.INFO, "Table schema checked/updated successfully");
                } catch (SQLException e) {
                    // Ignore if it fails (might already be correct or permissions issue), but log it
                    LOGGER.log(Level.WARNING, "Could not alter table schema (might be unnecessary): " + e.getMessage());
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking table schema", e);
        } finally {
            closeQuietly(statement);
            closeQuietly(connection);
        }
    }
}

