package exceptions;

public class CompanyEmailAlreadyExistsException extends Exception {
    public CompanyEmailAlreadyExistsException() {
        super("This Company email already used");
    }


}
