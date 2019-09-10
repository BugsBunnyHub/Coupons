package facades;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import db.CompanyDBDAO;
import db.CouponsDBDAO;
import db.CustomerDBDAO;
import exceptions.CouponsNotFoundForCustomerException;
import exceptions.CustomerNotFoundException;
import exceptions.ReturnConnectionFailedException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CustomerFacade extends ClientFacade {
    private int customerID;

    public CustomerFacade(CustomerDBDAO customerDB, CompanyDBDAO companyDB, CouponsDBDAO couponDB, int customerID) {
        super(customerDB, companyDB, couponDB);
        this.customerID = customerID;
    }

    public CustomerFacade() {
        super();
    }

    public CustomerFacade(int customerID) {
        this.customerID = customerID;
    }

    public CustomerFacade(CustomerDBDAO customerDB, CompanyDBDAO companyDB, CouponsDBDAO couponDB) {
        super(customerDB, companyDB, couponDB);
    }

    public boolean login(String email, String password) throws SQLException, ReturnConnectionFailedException {
        return this.getCustomerDB().isCustomerExists(email, password);
    }

    public void purchaseCoupon(Coupon coupon) throws SQLException, ReturnConnectionFailedException, CouponsNotFoundForCustomerException {
        if (!this.getCustomerDB().isCouponAlreadyPurchased(this.customerID, coupon.getId()) &&
                coupon.getAmount() > 0 && new Date().before(coupon.getEndDate())
        ) {
            this.getCouponDB().addCouponPurchase(this.customerID, coupon.getId());
            int CouponAmount = coupon.getAmount() - 1;
            coupon.setAmount(CouponAmount);
            this.getCouponDB().updateCoupon(coupon);
        }

    }

    public List<Coupon> getCustomerCoupons() throws SQLException, ReturnConnectionFailedException {
        return this.getCustomerDB().getCustomerCoupons(this.customerID);
    }

    public ArrayList<Coupon> getCustomerCouponsByCategory(Category category)
            throws SQLException, ReturnConnectionFailedException {
        ArrayList<Coupon> allCoupons;
        allCoupons = this.getCouponDB().getAllCoupons();
        Iterator<Coupon> iter = allCoupons.listIterator();
        while (iter.hasNext()) {
            Coupon coupon = iter.next();

            if (this.customerID != coupon.getId() || category.ordinal() != coupon.getCategory().ordinal()) {
                iter.remove();
            }
        }
        return allCoupons;
    }

    public Customer getCustomerDetails() throws SQLException, ReturnConnectionFailedException, CustomerNotFoundException {
        return this.getCustomerDB().getOneCustomer(this.customerID);
    }

    public int getCustomerID() {
        return this.customerID;
    }

    public int getCustomerID(String email, String password) throws SQLException, ReturnConnectionFailedException {
        return this.getCustomerDB().getCustomerID(email, password);
    }


}
