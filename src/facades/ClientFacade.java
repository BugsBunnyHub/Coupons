package facades;

import exceptions.FailedLoginException;
import exceptions.ReturnConnectionFailedException;
import db.CompanyDBDAO;
import db.CouponsDBDAO;
import db.CustomerDBDAO;

import java.sql.SQLException;

public abstract class ClientFacade {
    private CustomerDBDAO customerDB = new CustomerDBDAO();
    private CompanyDBDAO companyDB = new CompanyDBDAO();
    private CouponsDBDAO couponDB = new CouponsDBDAO();

    public ClientFacade(CustomerDBDAO customerDB, CompanyDBDAO companyDB, CouponsDBDAO couponDB) {
        super();
        this.customerDB = customerDB;
        this.companyDB = companyDB;
        this.couponDB = couponDB;
    }

    public ClientFacade() {
        super();
    }

    public CustomerDBDAO getCustomerDB() {
        return customerDB;
    }

    public CompanyDBDAO getCompanyDB() {
        return companyDB;
    }

    public CouponsDBDAO getCouponDB() {
        return couponDB;
    }

    abstract boolean login(String email, String password) throws SQLException, ReturnConnectionFailedException, FailedLoginException;

    @Override
    public String toString() {
        return "ClientFacade [customerDB=" + customerDB + ", companyDB=" + companyDB + ", couponDB=" + couponDB + "]";
    }
}
