package yola.exception;

/**
 * Represents a custom exception used in the Yola chatbot.
 * <p>
 * This exception is thrown when the user input or command handling encounters an error specific to Yola chatbot.
 */
public class YolaException extends Exception {
    /**
     * Creates a YolaException with the specified error message.
     *
     * @param message the detail message describing the exception.
     */
    public YolaException(String message) {

        super(message);

    }
}
