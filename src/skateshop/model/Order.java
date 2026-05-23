package skateshop.model;

import java.util.ArrayList;
import java.util.List;

import skateshop.interfaces.Exportable;

public class Order implements Exportable {
    private String orderId;
    private Customer customer;
    private List<CartItem> items;
    private OrderStatus status;
    private String date;
    private double total;

    public Order(String orderId, Customer customer, List<CartItem> items, String date) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.PENDING;
        this.date = date;
        this.total = items.stream().mapToDouble(CartItem::subtotal).sum();
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
