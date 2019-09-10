package exceptions;

public class CompanyNameAlreadyExistsException extends Exception {
    public CompanyNameAlreadyExistsException() {
        super("This Company name already exists");
    }

}
