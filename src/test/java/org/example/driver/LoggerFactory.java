package org.example.driver;

import org.apache.log4j.Logger;

public class LoggerFactory {

    private static LoggerFactory instance;
    private static Logger logger;

    /**
     * Private constructor to prevent multiple instances of this class
     * from being created, making it a singleton class.
     */
    private LoggerFactory() {
        // Create an instance of the logger
        logger = Logger.getLogger(LoggerFactory.class);
    }

    /**
     * Method to retrieve the single instance of this class.
     *
     * @return The single instance of the class.
     */
    public static LoggerFactory getInstance() {
        // Check if an instance of this class exists
        if (instance == null) {
            // Create a new instance of the class if it doesn't exist
            instance = new LoggerFactory();
        }
        return instance;
    }

    /**
     * Method to log an error message.
     *
     * @param message The error message to be logged.
     */
    public void error(String message) {
        // Check if the logger instance is not null
        if (logger != null) {
            // Log the error message
            logger.error(message);
        }
    }

    /**
     * Method to log a warning message.
     *
     * @param message The warning message to be logged.
     */
    public void warn(String message) {
        // Check if the logger instance is not null
        if (logger != null) {
            // Log the warning message
            logger.warn(message);
        }
    }

    /**
     * Method to log an info message.
     *
     * @param message The info message to be logged.
     */
    public void info(String message) {
        // Check if the logger instance is not null
        if (logger != null) {
            // Log the info message
            logger.info(message);
        }
    }

    /**
     * Method to log a debug message.
     *
     * @param message The debug message to be logged.
     */
    public void debug(String message) {
        // Check if the logger instance is not null
        if (logger != null) {
            // Log the debug message
            logger.debug(message);
        }
    }
}