package skateshop.model;

import skateshop.model.products.Product;
/**
 * Represents a single line item inside a shopping cart.
 * Stores the product, quantity, and the unit price at the time of addition.
 */
public class CartItem {
    private Product product;
    private int qty;
    private double unitPrice;
    
    
    /**
     * Creates a cart item for a given product and quantity.
     * The unit price is taken from the product's current price.
     * @param product the product being added
     * @param qty     quantity
     */
    public CartItem(Product product, int qty) {
        this.product = product;
        this.qty = qty;
        this.unitPrice = product.getPrice();
    }

    public Product getProduct() { return product; }
    public int getQty()         { return qty; }
    public double getUnitPrice(){ return unitPrice; }

    public void setQty(int qty) { this.qty = qty; }

    public double subtotal() {
        return unitPrice * qty;
    }

    @Override
    public String toString() {
        return String.format("  %-30s  x%d  @$%.2f  = $%.2f",
                product.getBrand() + " " + product.getType(), qty, unitPrice, subtotal());
    }
}
