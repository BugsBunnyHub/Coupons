package exceptions;

public class ReturnConnectionFailedException extends Exception{
    public ReturnConnectionFailedException() {
        super("Failed to add connection to the pool");
    }

}
