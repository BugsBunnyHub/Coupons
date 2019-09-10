package exceptions;

public class CouponsNotFoundForCustomerException extends Exception {
    public CouponsNotFoundForCustomerException(){
        super("No coupons were found for this customer");

    }
}
