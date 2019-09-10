package exceptions;

public class CustomerEmailAlreadyExistsException extends Exception {
    public CustomerEmailAlreadyExistsException(){
        super("The Customer email is already used");
    }
}
