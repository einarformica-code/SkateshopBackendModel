package skateshop.model;

import skateshop.model.products.Product;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a shopping cart belonging to a customer.
 * Contains a list of CartItem and provides operations to add, remove, and clear items.
 */
public class Cart {
    private List<CartItem> items;
    private Customer customer;

    /**
     * Constructs an empty cart for the given customer.
     * @param customer the owner of the cart
     */
    public Cart(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public Customer getCustomer() { return customer; }
    public List<CartItem> getItems() { return items; }

    
    /**
     * Adds a product to the cart with the specified quantity.
     * If the product already exists, the quantity is increased.
     * @param product the product to add
     * @param qty     quantity to add
     */
    public void addItem(Product product, int qty) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQty(item.getQty() + qty);
                return;
            }
        }
        items.add(new CartItem(product, qty));
    }
    
    
    /**
     * Removes a product from the cart by its ID.
     * @param productId the ID of the product to remove
     * @return true if an item was removed, false otherwise
     */
    public boolean removeItem(String productId) {
        return items.removeIf(i -> i.getProduct().getId().equals(productId));
    }
    
    /** Calculates the total price of all items in the cart. */

    public double total() {
        double result = 0;
        for (CartItem item : items){
        	result += item.subtotal();}
        return result;
        }
        

    /** Empties the cart completely. */
    public void clear() { items.clear(); }

    /** Empties the cart completely. */
    public boolean isEmpty() { return items.isEmpty(); }

    
    /** Prints the cart contents to the console in a formatted way. */
    public void print() {
        System.out.println("  ── Cart for " + customer.getUsername() + " ──");
        if (items.isEmpty()) {
            System.out.println("  (empty)");
        } else {
            for (CartItem item : items) System.out.println(item);
            System.out.printf("  TOTAL: $%.2f%n", total());
        }
    }
}
