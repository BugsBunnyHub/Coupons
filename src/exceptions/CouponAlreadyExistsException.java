package exceptions;

public class CouponAlreadyExistsException extends Exception {
    public CouponAlreadyExistsException() {
        super("This coupon name already exists");
    }

}
