package db;

import exceptions.ReturnConnectionFailedException;
import exceptions.NoSuchCategoryException;
import beans.Company;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompanyDAO {
    boolean isCompanyExists(String email, String password) throws SQLException, ReturnConnectionFailedException;

    boolean isCompanyEmailExists(String email) throws SQLException, ReturnConnectionFailedException;

    boolean isCompanyNameExists(String name) throws SQLException, ReturnConnectionFailedException;

    void addCompany(Company company) throws SQLException, ReturnConnectionFailedException;

    void updateCompany(Company company) throws SQLException, ReturnConnectionFailedException;

    void deleteCompany(int companyID) throws SQLException, ReturnConnectionFailedException;

    ArrayList<Company> getAllCompanies() throws SQLException, NoSuchCategoryException, ReturnConnectionFailedException;

    Company getOneCompany(int companyID) throws SQLException, NoSuchCategoryException, ReturnConnectionFailedException;

    ArrayList<Integer> getCompanyCouponIDs(int companyID) throws SQLException, ReturnConnectionFailedException;

    int getCompanyID(String email, String password) throws SQLException, ReturnConnectionFailedException;

}
