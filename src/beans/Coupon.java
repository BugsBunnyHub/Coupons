package beans;

import java.sql.Date;

public class Coupon {
    private int id, companyID, amount;
    private String title, description, image;
    private Category category;
    private Date startDate, endDate;
    private double price;

    public Coupon(int id, int companyID, Category category, String title, String description, Date startDate,
                  Date endDate, int amount, double price, String image) {
        super();
        this.id = id;
        this.companyID = companyID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.image = image;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Coupon(int companyID, Category category, String title, String description, Date startDate,
                  Date endDate, int amount, double price, String image) {
        super();
        this.companyID = companyID;
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.image = image;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public Coupon(Category category, String title, String description, Date startDate,
                  Date endDate, int amount, double price, String image) {
        super();
        this.amount = amount;
        this.title = title;
        this.description = description;
        this.image = image;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public int getAmount() {
        return amount;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Category getCategory() {
        return category;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Coupon [id=" + id + ", companyID=" + companyID + ", amount=" + amount + ", title=" + title
                + ", description=" + description + ", image=" + image + ", startDate=" + startDate + ", endDate="
                + endDate + ", price=" + price + "]";
    }
}
