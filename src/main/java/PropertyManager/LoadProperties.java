package PropertyManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadProperties {

    private static final Logger LOGGER = Logger.getLogger(LoadProperties.class.getName());
    private static final String CONFIG_FILE_NAME = "config.properties";
    final int QR_CODE_WIDTH = 350;

    /**
     * Load a property from the specified properties file
     * @param propertiesFileName The name of the properties file
     * @param key The property key to retrieve
     * @return The property value, or null if not found or error occurs
     */
    public String loadProperty(String propertiesFileName, String key) {
        if (propertiesFileName == null || propertiesFileName.trim().isEmpty()) {
            LOGGER.log(Level.SEVERE, "Properties file name cannot be null or empty");
            return null;
        }
        if (key == null || key.trim().isEmpty()) {
            LOGGER.log(Level.SEVERE, "Property key cannot be null or empty");
            return null;
        }

        Properties properties = new Properties();

        // Use try-with-resources to automatically close InputStream
        try (InputStream inputStream = new FileInputStream(propertiesFileName)) {
            properties.load(inputStream);
            String value = properties.getProperty(key);

            if (value == null) {
                LOGGER.log(Level.WARNING, "Property key ''{0}'' not found in file ''{1}''",
                          new Object[]{key, propertiesFileName});
            }

            return value;
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Properties file not found: " + propertiesFileName, e);
            return null;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading properties file: " + propertiesFileName, e);
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error loading property", e);
            return null;
        }
    }
}
