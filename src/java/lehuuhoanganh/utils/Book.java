package lehuuhoanganh.utils;

public class Book {
    private String id;
    private String title;
    private double unitPrice;

    public Book() {}
    public Book(String id, String title, double unitPrice) {
        this.id = id;
        this.title = title;
        this.unitPrice = unitPrice;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}