package exception;

public class ValidationException extends RuntimeException {
    public ValidationException() {
        super("Validation failed");
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}