package skateshop.model.products;
/**
 * Bearings is the class representation of a product that will later be instanced. 
 * <p>
 * It is capable of return its type  and format its attributes into a string.
 */
public class Bearings extends Product {
	/**
	 * This constructor will be called by adminAddProduct() when the chosen option is Bearing.
	 * @param id
	 * @param brand
	 * @param price
	 * @param stock
	 */
    public Bearings(String id, String brand, double price, int stock) {
        super(id, brand, price, stock);
    }

    @Override public String getType() { return "BEARINGS"; }
    
    /**
     * This function creates and returns a String where id, brand, price and stock will be inserted.
     * 
     *
     * Each element separated by vertical lines specifies their data types
     *  (%s=String, %.2f = float of 2 decimals)
     */
    @Override
    public String toFileString() {
        return String.format("BEARINGS|%s|%s|%.2f|%d",
                getId(), getBrand(), getPrice(), getStock());
    }
}
