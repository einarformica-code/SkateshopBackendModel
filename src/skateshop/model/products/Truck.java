package skateshop.model.products;
/**
 * Truck is the class representation of a product that will later be instanced. 
 * <p>
 *Its specific attribute is the width designed to be expressed in imperial metric system
 * .It is capable of return its type  and format its attributes into a string.
 */
public class Truck extends Product {
    private double width;
    
	/**
	 * This constructor will be called by adminAddProduct() when the chosen option is Bearing.
	 * @param id
	 * @param brand
	 * @param price
	 * @param stock
	 */
    public Truck(String id, String brand, double price, int stock, double width) {
        super(id, brand, price, stock);
        this.width = width;
    }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    @Override public String getType() { return "TRUCK"; }
    /**
     * This function creates and returns a String where id, brand, price and stock will be inserted.
     * 
     *
     * Each element separated by vertical lines specifies their data types
     *  (%s=String, %.2f = float of 2 decimals, %d=whole number)
     */
    @Override
    public String toFileString() {
        return String.format("TRUCK|%s|%s|%.2f|%d|%.2f",
                getId(), getBrand(), getPrice(), getStock(), width);
    }

    @Override
    public String toString() {
        return super.toString() + String.format("  Width:%.2f\"", width);
    }
}
