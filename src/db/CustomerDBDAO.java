package db;


import exceptions.CouponsNotFoundForCustomerException;
import exceptions.CustomerNotFoundException;
import exceptions.ReturnConnectionFailedException;
import beans.Category;
import beans.Coupon;
import beans.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerDBDAO implements CustomerDAO {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public boolean isCustomerExists(String email, String password) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select * from customers where email=? AND password=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    @Override
    public void addCustomer(Customer customer) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "insert into customers(first_name,last_name,email,password) values(?,?,?,?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ;
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPassword());
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "update customers set first_name=?,last_name=?,email=?,password=? where id=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPassword());
            stmt.setInt(5, customer.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    @Override
    public void deleteCustomer(int customerID) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "delete from customers where id=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, customerID);
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException, ReturnConnectionFailedException, CustomerNotFoundException{
        Connection con = connectionPool.getConnection();
        String sql = "select id from customers";
        ArrayList<Customer> allCustomers = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rsCustomerID = stmt.executeQuery();
            while (rsCustomerID.next()) {
                allCustomers.add(getOneCustomer(rsCustomerID.getInt(1)));
            }
            return allCustomers;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public Customer getOneCustomer(int customerID) throws SQLException, ReturnConnectionFailedException, CustomerNotFoundException {
        Connection con = connectionPool.getConnection();
        ArrayList<Coupon> customerCoupons = new ArrayList<>();
        String sql = "select coupon_id from coupon.customers_vs_coupons where customer_id=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, customerID);
            ResultSet rsCouponIDs = stmt.executeQuery();
            sql = "select * from coupon.coupons where id=?";
            while (rsCouponIDs.next()) {
                PreparedStatement stmt2 = con.prepareStatement(sql);
                stmt2.setInt(1, rsCouponIDs.getInt(1));
                ResultSet rsSingleCoupon = stmt2.executeQuery();
                rsSingleCoupon.next();
                customerCoupons.add(new Coupon(rsSingleCoupon.getInt(1), rsSingleCoupon.getInt(2),
                        Category.values()[rsSingleCoupon.getInt(3) - 1], rsSingleCoupon.getString(4),
                        rsSingleCoupon.getString(5), rsSingleCoupon.getDate(6), rsSingleCoupon.getDate(7),
                        rsSingleCoupon.getInt(8), rsSingleCoupon.getDouble(9), rsSingleCoupon.getString(10)));

            }
            sql = "select * from customers where id=?";
            PreparedStatement stmt3 = con.prepareStatement(sql);
            stmt3.setInt(1, customerID);
            ResultSet rsCustomer = stmt3.executeQuery();
            rsCustomer.next();
            return new Customer(rsCustomer.getInt(1), rsCustomer.getString(2), rsCustomer.getString(3),
                    rsCustomer.getString(4), rsCustomer.getString(5), customerCoupons);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    @Override
    public boolean isCustomerEmailExists(String email) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select * from customers where email=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public boolean isCouponAlreadyPurchased(int customerID, int couponID) throws SQLException, ReturnConnectionFailedException, CouponsNotFoundForCustomerException {
        Connection con = connectionPool.getConnection();
        String sql = "select * from customers_vs_coupons where customer_id=? AND coupon_id=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, customerID);
            stmt.setInt(2, couponID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public int getCustomerID(String email, String password) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select id from customers where email=? AND password=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    //not my method, it works don't touch
    public List<Coupon> getCustomerCoupons(int customerId) throws SQLException {
        Connection con = connectionPool.getConnection();
        String sql = "SELECT coupon_id FROM coupon.customers_vs_coupons WHERE customer_id = ?";
        List<Integer> listId = new ArrayList<>();
        List<Coupon> coupons = new ArrayList<>();
        try (PreparedStatement prep = con.prepareStatement(sql)) {
            prep.setInt(1, customerId);
            ResultSet rs = prep.executeQuery();
            while (rs.next())
                listId.add(rs.getInt("coupon_id"));
            CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
            listId.forEach(id -> {
                try {
                    coupons.add(couponsDBDAO.getOneCoupon(id));
                } catch (SQLException | ReturnConnectionFailedException e) {
                    System.out.println(e.getMessage());
                } finally {
                    try {
                        connectionPool.returnConnection(con);
                    } catch (ReturnConnectionFailedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return coupons;
    }

}

