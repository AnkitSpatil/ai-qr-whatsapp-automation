package utilities;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputManager {

    private static final Logger LOGGER = Logger.getLogger(InputManager.class.getName());
    private final Scanner scanner;

    /**
     * Constructor to initialize Scanner
     */
    public InputManager() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Get string input from user with validation
     * @param promptMessage The message to display to the user
     * @return User input as String, or default value if input is empty
     */
    public String getStringInput(String promptMessage) {
        // Check if promptMessage is null
        if (promptMessage == null || promptMessage.trim().isEmpty()) {
            promptMessage = "Please enter the input: ";
        }

        try {
            System.out.println(promptMessage);

            if (!scanner.hasNextLine()) {
                LOGGER.log(Level.WARNING, "No input available from scanner");
                return "https://devfolios.online";
            }

            String inputFromUser = scanner.nextLine();

            // Validate input is not empty
            if (inputFromUser == null || inputFromUser.trim().isEmpty()) {
                String defaultValue = "https://devfolios.online";
                LOGGER.log(Level.INFO, "Input was empty, defaulting to: {0}", defaultValue);
                System.out.println("Input was empty, defaulting to: " + defaultValue);
                return defaultValue;
            }

            return inputFromUser.trim();
        } catch (IllegalStateException e) {
            LOGGER.log(Level.SEVERE, "Scanner is closed", e);
            return "https://devfolios.online";
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading user input", e);
            return "https://devfolios.online";
        }
    }

    /**
     * Get integer input from user with validation
     * @param promptMessage The message to display to the user
     * @return User input as integer, or -1 if invalid
     */
    public int getIntInput(String promptMessage) {
        if (promptMessage == null || promptMessage.trim().isEmpty()) {
            promptMessage = "Please enter a number: ";
        }

        try {
            System.out.println(promptMessage);

            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                LOGGER.log(Level.WARNING, "Invalid integer input");
                scanner.nextLine(); // Clear the invalid input
                return -1;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading integer input", e);
            return -1;
        }
    }

    /**
     * Close the scanner to release resources
     */
    public void close() {
        try {
            if (scanner != null) {
                scanner.close();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error closing scanner", e);
        }
    }
}
