package facades;

import exceptions.FailedLoginException;
import exceptions.ReturnConnectionFailedException;
import db.CompanyDBDAO;
import db.CouponsDBDAO;
import db.CustomerDBDAO;

import java.sql.SQLException;

public class LoginManager {
    private static LoginManager instance = new LoginManager();

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException, ReturnConnectionFailedException, FailedLoginException {
        CustomerDBDAO customerDB = new CustomerDBDAO();
        CompanyDBDAO companyDB = new CompanyDBDAO();
        CouponsDBDAO couponDB = new CouponsDBDAO();
        switch (clientType) {
            case Admin:
                AdminFacade adminFacade = new AdminFacade(customerDB, companyDB, couponDB);
                if (adminFacade.login(email, password)) {
                    return adminFacade;
                } else {
                    throw new FailedLoginException();
                }
            case Company:
                CompanyFacade companyFacade = new CompanyFacade(customerDB, companyDB, couponDB);
                if (companyFacade.login(email, password)) {
                    int id = companyFacade.getCompanyID(email, password);

                    return new CompanyFacade(id);
                } else {
                    throw new FailedLoginException();
                }
            case Customer:
                CustomerFacade customerFacade = new CustomerFacade(customerDB, companyDB, couponDB);
                if (customerFacade.login(email, password)) {
                    int id = customerFacade.getCustomerID(email, password);

                    return new CustomerFacade(id);
                } else {
                    throw new FailedLoginException();
                }
        }
        return null;
    }

}
