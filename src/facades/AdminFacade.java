package facades;

import exceptions.*;
import beans.Company;
import beans.Customer;
import db.CompanyDBDAO;
import db.CouponsDBDAO;
import db.CustomerDBDAO;


import java.sql.SQLException;
import java.util.ArrayList;

public class AdminFacade extends ClientFacade {

    public AdminFacade(CustomerDBDAO customerDB, CompanyDBDAO companyDB, CouponsDBDAO couponDB) {
        super(customerDB, companyDB, couponDB);
    }

    public AdminFacade() {
        super();
    }

    public boolean login(String email, String password) throws FailedLoginException {
        if (email.equals("admin@admin.com") && password.equals("admin"))
            return true;
        else
            throw new FailedLoginException();
    }

    public void addCompany(Company company) throws SQLException,
            CompanyEmailAlreadyExistsException, CompanyNameAlreadyExistsException, ReturnConnectionFailedException {
            if (this.getCompanyDB().isCompanyEmailExists(company.getEmail()))
                throw new CompanyEmailAlreadyExistsException();
            else if (this.getCompanyDB().isCompanyNameExists(company.getName()))
                throw new CompanyNameAlreadyExistsException();
            else
                this.getCompanyDB().addCompany(company);
        }


    public void updateCompany(Company company)
            throws SQLException, ReturnConnectionFailedException, CompanyNameAlreadyExistsException, CompanyEmailAlreadyExistsException {
        if (this.getCompanyDB().isCompanyEmailExists(company.getEmail()))
            throw new CompanyEmailAlreadyExistsException();
        else if (this.getCompanyDB().isCompanyNameExists(company.getName()))
            throw new CompanyNameAlreadyExistsException();

        else
            this.getCompanyDB().updateCompany(company);
    }

    public void deleteCompany(int companyID)
            throws SQLException, ReturnConnectionFailedException {
        this.getCouponDB().deleteAllCompanyCoupons(companyID);
        this.getCompanyDB().deleteCompany(companyID);
    }

    public void deleteCouponPurchase(int customerID, int couponID) throws SQLException, ReturnConnectionFailedException {
        this.getCouponDB().deleteCouponPurchase(customerID,couponID);
    }

    public ArrayList<Company> getAllCompanies()
            throws SQLException, ReturnConnectionFailedException {
        return this.getCompanyDB().getAllCompanies();
    }

    public Company getOneCompany(int companyID)
            throws SQLException, ReturnConnectionFailedException {
        return this.getCompanyDB().getOneCompany(companyID);
    }

    public void addCustomer(Customer customer)
            throws SQLException, ReturnConnectionFailedException, CustomerEmailAlreadyExistsException {
        if (!this.getCustomerDB().isCustomerEmailExists(customer.getEmail()))
            this.getCustomerDB().addCustomer(customer);
        else
            throw new CustomerEmailAlreadyExistsException();
    }

    public void updateCustomer(Customer customer) throws SQLException, ReturnConnectionFailedException {
        this.getCustomerDB().updateCustomer(customer);
    }

    public void deleteCustomer(int customerID) throws SQLException, ReturnConnectionFailedException {
        this.getCustomerDB().deleteCustomer(customerID);
    }

    public ArrayList<Customer> getAllCustomers() throws SQLException, ReturnConnectionFailedException, CustomerNotFoundException {
        return this.getCustomerDB().getAllCustomers();
    }

    public Customer getOneCustomer(int customerID) throws SQLException, ReturnConnectionFailedException, CustomerNotFoundException {
        return this.getCustomerDB().getOneCustomer(customerID);
    }
}
