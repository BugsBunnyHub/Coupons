package db;

import exceptions.ReturnConnectionFailedException;
import beans.Category;
import beans.Coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CouponsDBDAO implements CouponsDAO {

    private int couponID;

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void addCoupon(Coupon coupon) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "insert into coupons(company_id,category_id,title,description,start_date,end_date,amount,price,image) values(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, coupon.getCompanyID());
            stmt.setInt(2, coupon.getCategory().ordinal() + 1);
            stmt.setString(3, coupon.getTitle());
            stmt.setString(4, coupon.getDescription());
            stmt.setDate(5, coupon.getStartDate());
            stmt.setDate(6, coupon.getEndDate());
            stmt.setInt(7, coupon.getAmount());
            stmt.setDouble(8, coupon.getPrice());
            stmt.setString(9, coupon.getImage());
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    @Override
    public void updateCoupon(Coupon coupon) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "update coupon.coupons set category_id=?,title=?,description=?,start_date=?,end_date=?,amount=?,price=?,image=? where id=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, coupon.getCategory().ordinal() + 1);
            stmt.setString(2, coupon.getTitle());
            stmt.setString(3, coupon.getDescription());
            stmt.setDate(4, coupon.getStartDate());
            stmt.setDate(5, coupon.getEndDate());
            stmt.setInt(6, coupon.getAmount());
            stmt.setDouble(7, coupon.getPrice());
            stmt.setString(8, coupon.getImage());
            stmt.setInt(9, coupon.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    @Override
    public void deleteCoupon(int couponID) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "delete from coupons where id=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, couponID);
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    @Override
    public ArrayList<Coupon> getAllCoupons() throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select * from coupons";
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                coupons.add(new Coupon(rs.getInt(1), rs.getInt(2), Category.values()[rs.getInt(3)-1], rs.getString(4),
                        rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getInt(8), rs.getDouble(9),
                        rs.getString(10)));
            }
            return coupons;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public Coupon getOneCoupon(int couponID) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select * from coupons where id=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, couponID);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Coupon coupon = new Coupon(rs.getInt(1), rs.getInt(2), Category.values()[rs.getInt(3)-1], rs.getString(4),
                    rs.getString(5), rs.getDate(6), rs.getDate(7), rs.getInt(8), rs.getDouble(9), rs.getString(10));
            return coupon;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "insert into coupon.customers_vs_coupons (customer_id, coupon_id) values(?,?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, customerID);
            stmt.setInt(2, couponID);
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "delete from coupon.customers_vs_coupons where customer_id=? AND coupon_id=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, customerID);
            stmt.setInt(2, couponID);
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

    @Override
    public boolean isCouponNameExists(String name) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select * from coupons where title=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
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
    public void deleteAllCompanyCoupons(int companyID) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "delete from coupons where company_id=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, companyID);
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }

}
