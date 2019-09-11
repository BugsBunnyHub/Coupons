package db;

import beans.Category;
import beans.Company;
import beans.Coupon;
import exceptions.ReturnConnectionFailedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyDBDAO implements CompanyDAO {

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public boolean isCompanyEmailExists(String email) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select * from coupon.companies where email=?";
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            boolean result = false;
            if (rs.next())
                result = true;
            else
                result = false;
            return result;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public boolean isCompanyNameExists(String name) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select * from coupon.companies where name=?";
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
    public boolean isCompanyExists(String email, String password) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select * from coupon.companies where email=? AND password=?";
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
    public void addCompany(Company company) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "insert into coupon.companies(name, email, password) values(?,?,?)";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getEmail());
            stmt.setString(3, company.getPassword());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public void updateCompany(Company company) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "update coupon.companies set name=?,email=?,password=? where id=?";
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getEmail());
            stmt.setString(3, company.getPassword());
            stmt.setInt(4, company.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public void deleteCompany(int companyID) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "delete from coupon.companies where id=?";
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, companyID);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new SQLException();
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public ArrayList<Company> getAllCompanies()
            throws SQLException, ReturnConnectionFailedException {
        ArrayList<Company> allCompanies = new ArrayList<>();
        Connection con = connectionPool.getConnection();
        String sql = "select id from coupon.companies";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet companiesID = stmt.executeQuery();
            while (companiesID.next()) {
                allCompanies.add(getOneCompany(companiesID.getInt(1)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }
        return allCompanies;
    }

    @Override
    public Company getOneCompany(int companyID)
            throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select * from coupon.coupons where company_id=?";
        ArrayList<Coupon> companyCoupons = new ArrayList<>();
        Company company;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, companyID);
            ResultSet rsCompanyCoupons = stmt.executeQuery();
            while (rsCompanyCoupons.next()) {
                companyCoupons.add(new Coupon(rsCompanyCoupons.getInt(1), companyID,
                        Category.values()[rsCompanyCoupons.getInt(3) - 1], rsCompanyCoupons.getString(4), //exception array out of bounds added -1
                        rsCompanyCoupons.getString(5), rsCompanyCoupons.getDate(6), rsCompanyCoupons.getDate(7),
                        rsCompanyCoupons.getInt(8), rsCompanyCoupons.getDouble(9), rsCompanyCoupons.getString(10)));
            }
            sql = "select * from coupon.companies where id=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, companyID);
            ResultSet rsCompany = stmt.executeQuery();
            rsCompany.next();
            company = new Company(rsCompany.getInt(1), rsCompany.getString(2), rsCompany.getString(3),
                    rsCompany.getString(4), companyCoupons);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }
        return company;
    }

    @Override
    public ArrayList<Integer> getCompanyCouponIDs(int companyID) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select id from coupon.coupons where company_id=?";
        ArrayList<Integer> companyCouponIDs = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, companyID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                companyCouponIDs.add(rs.getInt(1));
            }
            return companyCouponIDs;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }
    }

    @Override
    public int getCompanyID(String email, String password) throws SQLException, ReturnConnectionFailedException {
        Connection con = connectionPool.getConnection();
        String sql = "select id from coupon.companies where email=? AND password=?";
        int id = -1;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            connectionPool.returnConnection(con);
        }

    }
}
