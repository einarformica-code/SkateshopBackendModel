package skateshop.model;

import skateshop.model.products.Product;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    private Customer customer;

    public Cart(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public Customer getCustomer() { return customer; }
    public List<CartItem> getItems() { return items; }

    public void addItem(Product product, int qty) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQty(item.getQty() + qty);
                return;
            }
        }
        items.add(new CartItem(product, qty));
    }

    public boolean removeItem(String productId) {
        return items.removeIf(i -> i.getProduct().getId().equals(productId));
    }

    public double total() {
        double t = 0;
        for (CartItem item : items) t += item.subtotal();
        return t;
    }

    public void clear() { items.clear(); }

    public boolean isEmpty() { return items.isEmpty(); }

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
