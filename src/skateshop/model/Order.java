package skateshop.model;

import java.util.ArrayList;
import java.util.List;

import skateshop.interfaces.Exportable;
/**
 * Represents an order placed by a customer, containing a list of items.
 * Orders have a status (PENDING, CONFIRMED, SHIPPED, CANCELED) and a creation date.
 */
public class Order implements Exportable {
    private String orderId;
    private Customer customer;
    private List<CartItem> items;
    private OrderStatus status;
    private String date;
    private double total;
    
    /**
     * Constructs a new Order with a generated ID and current date.
     * @param orderId  unique order identifier
     * @param customer the customer who placed the order
     * @param items    list of cart items
     * @param date     order creation date (as string)
     */
    public Order(String orderId, Customer customer, List<CartItem> items, String date) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.PENDING;
        this.date = date;
        this.total = items.stream().mapToDouble(CartItem::subtotal).sum();
    }
    //Copy constructors
    public Order(Order other) {
        this.orderId  = other.orderId;
        this.customer = other.customer;
        this.items    = new ArrayList<>(other.items);
        this.status   = other.status;
        this.date     = other.date;
        this.total    = other.total;
    }

    public String getOrderId()       { return orderId; }
    public Customer getCustomer()    { return customer; }
    public List<CartItem> getItems() { return items; }
    public OrderStatus getStatus()   { return status; }
    public String getDate()          { return date; }
    public double getTotal()         { return total; }

    public void setStatus(OrderStatus status) { this.status = status; }

    public void placeOrder() {
        this.status = OrderStatus.CONFIRMED;
    }

    /** Serialize order to multi-line TXT block */
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ORDER|%s|%s|%s|%s|%.2f%n",
                orderId, customer.getUserId(), status, date, total));
        for (CartItem item : items) {
            sb.append(String.format("  ITEM|%s|%d|%.2f%n",
                    item.getProduct().getId(), item.getQty(), item.getUnitPrice()));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Order #%s  Customer:%s  Date:%s  Status:%-10s  Total:$%.2f%n",
                orderId, customer.getUsername(), date, status, total));
        for (CartItem item : items) sb.append(item).append("\n");
        return sb.toString();
    }

    public String toDisplayString() {
		return toString();
    	
    }
    
    
}
