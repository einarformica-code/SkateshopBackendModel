package skateshop.model.products;

import skateshop.interfaces.Exportable;

public abstract class Product implements Exportable {
    private String id;
    private String brand;
    private double price;
    private int stock;

    public Product(String id, String brand, double price, int stock) {
        this.id = id;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
    }

    // Getters
    public String getId()     { return id; }
    public String getBrand()  { return brand; }
    public double getPrice()  { return price; }
    public int getStock()     { return stock; }

    // Setters
    public void setId(String id)         { this.id = id; }
    public void setBrand(String brand)   { this.brand = brand; }
    public void setPrice(double price)   { this.price = price; }
    public void setStock(int stock)      { this.stock = stock; }

    /** Returns the product type label used for TXT persistence */
    public abstract String getType();

    /**
     * Serialize to a single pipe-separated line.
     * Format: TYPE|id|brand|price|stock|<extra fields>
     */
    public abstract String toFileString();

    @Override
    public String toString() {
        return String.format("[%s] ID:%-6s  Brand:%-15s  Price:$%8.2f  Stock:%d",
                getType(), id, brand, price, stock);
    }
  
    @Override
    public String toDisplayString() {
    	return toString();
    }
}
