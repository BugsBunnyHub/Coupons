package exceptions;

public class FailedLoginException extends Exception {
    public FailedLoginException() {
        super("Login failed");
    }
}
