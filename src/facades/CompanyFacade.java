package facades;

import exceptions.CouponAlreadyExistsException;
import exceptions.ReturnConnectionFailedException;

import beans.Coupon;
import db.CompanyDBDAO;
import db.CouponsDBDAO;
import db.CustomerDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyFacade extends ClientFacade {
    private int companyID;


    public CompanyFacade(CustomerDBDAO customerDB, CompanyDBDAO companyDB, CouponsDBDAO couponDB, int companyID) {
        super(customerDB, companyDB, couponDB);
        this.companyID = companyID;
    }

    public CompanyFacade() {
        super();
    }

    public CompanyFacade(int companyID){
        this.companyID = companyID;
    }

    public CompanyFacade(CustomerDBDAO customerDB, CompanyDBDAO companyDB, CouponsDBDAO couponDB) {
        super(customerDB, companyDB, couponDB);
    }

    public boolean login(String email, String password) throws SQLException, ReturnConnectionFailedException {
        return this.getCompanyDB().isCompanyExists(email, password);
    }

    public void addCoupon(Coupon coupon) throws SQLException, ReturnConnectionFailedException, CouponAlreadyExistsException {
        if (this.getCouponDB().isCouponNameExists(coupon.getTitle()))
            throw new CouponAlreadyExistsException();
        else
            this.getCouponDB().addCoupon(coupon);
    }

    public void updateCoupon(Coupon coupon) throws SQLException, ReturnConnectionFailedException {
        this.getCouponDB().updateCoupon(coupon);
    }

    public void deleteCoupon(int couponID) throws SQLException, ReturnConnectionFailedException {
        this.getCouponDB().deleteCoupon(couponID);
    }

    public ArrayList<Coupon> getCompanyCoupons() throws SQLException, ReturnConnectionFailedException {
        ArrayList<Coupon> coupons;
        coupons = this.getCouponDB().getAllCoupons();
        return coupons;
    }

    public int getCompanyID() {
        return this.companyID;
    }

    public int getCompanyID(String email, String password) throws SQLException, ReturnConnectionFailedException {
        return this.getCompanyDB().getCompanyID(email, password);
    }


}
