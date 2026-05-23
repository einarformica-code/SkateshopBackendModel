package skateshop.model;

import skateshop.model.products.Product;

public class CartItem {
    private Product product;
    private int qty;
    private double unitPrice;

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
