package db;

import exceptions.ReturnConnectionFailedException;
import beans.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {

    void addCoupon(Coupon coupon) throws SQLException, ReturnConnectionFailedException;

    void updateCoupon(Coupon coupon) throws SQLException, ReturnConnectionFailedException;

    void deleteCoupon(int couponID) throws SQLException, ReturnConnectionFailedException;

    void deleteAllCompanyCoupons(int companyID) throws SQLException, ReturnConnectionFailedException;

    ArrayList<Coupon> getAllCoupons() throws SQLException, ReturnConnectionFailedException;

    Coupon getOneCoupon(int couponID) throws SQLException, ReturnConnectionFailedException;

    void addCouponPurchase(int customerID, int CouponID) throws SQLException, ReturnConnectionFailedException;

    void deleteCouponPurchase(int customerID, int CouponID) throws SQLException, ReturnConnectionFailedException;

    boolean isCouponNameExists(String name) throws SQLException, ReturnConnectionFailedException;

}
