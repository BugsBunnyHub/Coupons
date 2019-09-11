package tests;

import beans.Category;
import beans.Company;
import beans.Coupon;
import beans.Customer;
import db.CompanyDBDAO;
import db.ConnectionPool;
import db.CustomerDBDAO;
import facades.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestMethods {

    private static AdminFacade adminFacade;
    private static CustomerFacade customerFacade;
    private static CompanyFacade companyFacade;

    static {
        LoginManager login = LoginManager.getInstance();
        try {
            clearDB(); // clear the DB before each run to eliminate errors, keep in mind ID won't reset to 0!
            initUsers();
            adminFacade = (AdminFacade) login.login("admin@admin.com", "admin", ClientType.Admin);
            customerFacade = (CustomerFacade) login.login("daniel@shatz.com", "123", ClientType.Customer);
            companyFacade = (CompanyFacade) login.login("daniel@shatz.com", "123", ClientType.Company);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initUsers() throws Exception {
        CustomerDBDAO custdao = new CustomerDBDAO();
        CompanyDBDAO compdao = new CompanyDBDAO();
        custdao.addCustomer(new Customer("daniel", "shatz", "daniel@shatz.com", "123"));
        compdao.addCompany(new Company("shatz.LTD", "daniel@shatz.com", "123"));
    }

    private static void clearDB() throws Exception { //clears the DB tables to start clean
        Connection con = ConnectionPool.getInstance().getConnection();
        PreparedStatement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt4 = null;
        String sql1 = "delete from coupon.customers_vs_coupons";
        String sql2 = "delete from coupon.coupons";
        String sql3 = "delete from coupon.customers";
        String sql4 = "delete from coupon.companies";
        try {
            stmt1 = con.prepareStatement(sql1);
            stmt2 = con.prepareStatement(sql2);
            stmt3 = con.prepareStatement(sql3);
            stmt4 = con.prepareStatement(sql4);
            stmt1.execute();
            stmt2.execute();
            stmt3.execute();
            stmt4.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            stmt1.close();
            stmt2.close();
            stmt3.close();
            stmt4.close();
            ConnectionPool.getInstance().returnConnection(con);
        }
    }

    public static void addCompaniesAdmin(int amount) throws Exception {
        for (int i = 0; i < amount; i++) {
            Company Company = new Company("Company" + i, "email" + i + "@companies.com", "123" + i);
            adminFacade.addCompany(Company);
        }
    }

    private static Category randomCategory() {
        return Category.values()[new Random().nextInt(4)];
    }

    public static void addCouponsToCompanyAdmin(int amount) throws Exception {
        List<Company> companies = companyFacade.getCompanyDB().getAllCompanies();

        for (int i = 0; i < companies.size(); i++) {
            Company comp = companies.get(i);
            for (int j = 1; j <= amount; j++) { // math random to avoid same title from "j" since it starts from 0 and the loop in inside a loop so it starts over with 0 and throws exception
                Coupon coupon = new Coupon(comp.getId(), randomCategory(), "Coupon" + j * new Random().nextInt(), "Coupon" + j, Date.valueOf("2019-01-01"),
                        Date.valueOf("2019-12-31"), j + 1, j + 1, null); //(int)(Math.floor(Math.random() * 9000) - casting int from float to avoid .0 at the end of the coupon name
                companyFacade.addCoupon(coupon);
            }
        }
    }

    public static void addCustomersAdmin(int amount) throws Exception {
        for (int i = 0; i < amount; i++) {
            Customer customer = new Customer("Customer" + i, "LastName" + i, "customer" + i + "@customers.com", "123" + i);
            adminFacade.addCustomer(customer);
        }
    }

    public static void deleteCompanyAdmin(int id) throws Exception {
        System.out.println("Deleted company is" + adminFacade.getOneCompany(id));
        adminFacade.deleteCompany(id);
    }

    public static void updateCompanyAdmin(int id) throws Exception {
        Company company = adminFacade.getOneCompany(id);
        System.out.println("before update: " + adminFacade.getOneCompany(id));
        company.setEmail("Company@Updated.com");
        company.setName("CompanyUpdated");
        adminFacade.updateCompany(company);
        System.out.println("after update: " + adminFacade.getOneCompany(id));
    }

    public static ArrayList<Company> getAllCompaniesAdmin() throws Exception {
        return adminFacade.getAllCompanies();
    }

    public static void updateCustomerAdmin(int id) throws Exception {
        Customer customer = adminFacade.getOneCustomer(id);
        System.out.println("Before update: " + customer.getFirstName());
        customer.setFirstName("UpdatedFirstName");
        System.out.println("After update: " + customer.getFirstName());
        adminFacade.updateCustomer(customer);
    }

    public static void deleteCustomerAdmin(int id) throws Exception {
        System.out.println("Customer that gonna be deleted is: " + adminFacade.getOneCustomer(id));
        adminFacade.deleteCustomer(id);
    }

    public static ArrayList<Customer> getAllCustomersAdmin() throws Exception {
        return adminFacade.getAllCustomers();
    }

    public static void deleteCouponPurchaseAdmin(int custId, int coupId) throws Exception {
        adminFacade.deleteCouponPurchase(custId, coupId);
        System.out.println("Deleted coupon purchase:\nCustomer ID: " + custId + "\nCoupon ID: " + coupId);
    }

    public static void updateCouponCompany() throws Exception {
        ArrayList<Coupon> couponsU = companyFacade.getCompanyCoupons();
        System.out.println("Coupon to be updated is: " + couponsU.get(0).getTitle());
        couponsU.get(0).setTitle("UpdatedCouponByCompany");
        for (Coupon coupons : couponsU) {
            companyFacade.updateCoupon(coupons);
        }
    }

    public static ArrayList<Coupon> getCompanyCouponsCompany() throws Exception {
        return companyFacade.getCompanyCoupons();
    }

    public static void deleteCouponCompany(int id) throws Exception {
        System.out.println("Deleted coupon is:  " + id);
        companyFacade.deleteCoupon(id);
    }

    public static void purchaseCouponCustomer(Coupon coupon) throws Exception {
        System.out.println("Purchased coupon is: " + coupon);
        customerFacade.purchaseCoupon(coupon);
    }

    public static List<Coupon> getCustomerCouponsCustomer() throws Exception {
        return customerFacade.getCustomerCoupons();
    }

    public static Customer getCustomerDetailsCustomer() throws Exception {
        return customerFacade.getCustomerDetails();
    }


    public static void testAll() {
        ExpiredCouponDelete expiredCouponDelete = new ExpiredCouponDelete();
        Thread t = new Thread(expiredCouponDelete);
        t.start();
        try {
            addCompaniesAdmin(20);
            addCouponsToCompanyAdmin(5);
            addCustomersAdmin(50);

            List<Company> companies = getAllCompaniesAdmin();
            List<Coupon> coupons = getCompanyCouponsCompany();
            List<Customer> customers = getAllCustomersAdmin();

            System.out.println(companies);
            System.out.println("================================================================");
            System.out.println(coupons);
            System.out.println("================================================================");
            System.out.println(customers);
            System.out.println("================================================================");

            updateCompanyAdmin(companies.get(0).getId());
            System.out.println("================================================================");
            updateCouponCompany();
            System.out.println("================================================================");
            updateCustomerAdmin(customers.get(0).getId());
            System.out.println("================================================================");

            deleteCouponCompany(coupons.get(2).getId());
            System.out.println("================================================================");
            deleteCompanyAdmin(companies.get(2).getId());
            System.out.println("================================================================");
            deleteCustomerAdmin(customers.get(2).getId());
            System.out.println("================================================================");

            purchaseCouponCustomer(getCompanyCouponsCompany().get(0));
            System.out.println("================================================================");
            System.out.println(getCustomerCouponsCustomer());
            System.out.println("================================================================");
            System.out.println(getCustomerDetailsCustomer());
            System.out.println("================================================================");
            deleteCouponPurchaseAdmin(customerFacade.getCustomerID(), getCompanyCouponsCompany().get(0).getId());
            System.out.println("================================================================");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            expiredCouponDelete.stop();
            ConnectionPool.getInstance().closeAllConnections();
        }
    }
}




