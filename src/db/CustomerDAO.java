package db;

import beans.Coupon;
import exceptions.CouponsNotFoundForCustomerException;
import exceptions.CustomerNotFoundException;
import exceptions.ReturnConnectionFailedException;
import beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerDAO {
    boolean isCustomerExists(String email, String password) throws SQLException, ReturnConnectionFailedException;

    void addCustomer(Customer customer) throws SQLException, ReturnConnectionFailedException;

    void updateCustomer(Customer customer) throws SQLException, ReturnConnectionFailedException;

    void deleteCustomer(int customerID) throws SQLException, ReturnConnectionFailedException;

    ArrayList<Customer> getAllCustomers() throws SQLException, ReturnConnectionFailedException, CustomerNotFoundException;

    Customer getOneCustomer(int customerID) throws SQLException, ReturnConnectionFailedException, CustomerNotFoundException;

    boolean isCustomerEmailExists(String email) throws SQLException, ReturnConnectionFailedException;

    boolean isCouponAlreadyPurchased(int customerID, int couponID) throws SQLException, ReturnConnectionFailedException, CouponsNotFoundForCustomerException;

    int getCustomerID(String email, String password) throws SQLException, ReturnConnectionFailedException;

    List<Coupon> getCustomerCoupons(int customerId) throws SQLException , ReturnConnectionFailedException;

}
