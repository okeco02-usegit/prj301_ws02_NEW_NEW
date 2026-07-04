package lehuuhoanganh.utils;

public class Order {

    private int orderId;
    private String itemId;
    private String itemName;
    private int quantity;
    private double unitPrice;

    public Order() {
        this.orderId   = 0;
        this.itemId    = null;
        this.itemName  = null;
        this.quantity  = 0;
        this.unitPrice = 0;
    }

    public Order(int orderId, String itemId, String itemName, int quantity, double unitPrice) {
        this.orderId   = orderId;
        this.itemId    = itemId;
        this.itemName  = itemName;
        this.quantity  = quantity;
        this.unitPrice = unitPrice;
    }

    public Order(String itemId, String itemName, int quantity, double unitPrice) {
        this(0, itemId, itemName, quantity, unitPrice);
    }

    public int getOrderId()                 { return orderId; }
    public void setOrderId(int orderId)     { this.orderId = orderId; }

    public String getItemId()               { return itemId; }
    public void setItemId(String itemId)    { this.itemId = itemId; }

    public String getItemName()             { return itemName; }
    public void setItemName(String itemName){ this.itemName = itemName; }

    public int getQuantity()                { return quantity; }
    public void setQuantity(int quantity)   { this.quantity = quantity; }

    public double getUnitPrice()            { return unitPrice; }
    public void setUnitPrice(double p)      { this.unitPrice = p; }
    
    public double getSubTotal() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return String.format("OrderId:%d | ItemId:%s | ItemName:%s | Quantity:%d | UnitPrice:%.2f | SubTotal:%.2f",
                orderId, itemId, itemName, quantity, unitPrice, getSubTotal());
    }
}