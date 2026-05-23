package skateshop.service;

import skateshop.exceptions.InsufficientStockException;
import skateshop.exceptions.ProductNotFoundException;
import skateshop.model.*;
import skateshop.util.FileManager;
import skateshop.util.IdGenerator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final StockManager stockManager;
    private final SaleRecord saleRecord;
    private final List<Order> orders;

    public OrderService(StockManager stockManager, SaleRecord saleRecord) {
        this.stockManager = stockManager;
        this.saleRecord = saleRecord;
        this.orders = new ArrayList<>();
    }

    public Order createOrder(Cart cart) throws IOException {
        if (cart.isEmpty()) {
            System.out.println("  Cart is empty.");
            return null;
        }
        // Check stock for all items
        for (CartItem item : cart.getItems()) {
            int available = stockManager.checkStock(item.getProduct().getId());
            if (available < item.getQty()) {
                System.out.printf("  Not enough stock for %s. Available: %d, Requested: %d%n",
                        item.getProduct().getId(), available, item.getQty());
                return null;
            }
        }
        Order order = new Order(IdGenerator.nextOrderId(), cart.getCustomer(),
                cart.getItems(), LocalDate.now().toString());
        orders.add(order);
        FileManager.appendOrder(order);
        System.out.println("  Order created: " + order.getOrderId());
        return order;
    }
    /**
     * Confirms the given order.
     * <p>
     * If the order is not {@code null}, the required stock is removed
     * and the order is registered.
     *
     * @param order the order to confirm
     * @return {@code true} if the order was successfully confirmed;
     *         {@code false} if the order is {@code null}
     * @throws IOException if an I/O error occurs while processing the order
     * @throws InsufficientStockException if there is not enough stock available
     * @throws ProductNotFoundException if a product in the order cannot be found
     */
    public boolean confirmOrder(Order order) throws IOException, InsufficientStockException, ProductNotFoundException {
        if (order == null) return false;
        
        for (CartItem item : order.getItems()) {
            stockManager.removeStock(item.getProduct().getId(), item.getQty());
        }
        
        order.placeOrder();
        System.out.println("  Order confirmed: " + order.getOrderId());
        return true;
    }

    public void cancelOrder(Order order) {
        if (order == null) return;
        order.setStatus(OrderStatus.CANCELED);
        System.out.println("  Order canceled: " + order.getOrderId());
    }

    /**
     * Full checkout: create order → confirm → record sale.
     * @param payment  e.g. "CASH" or "CARD"
     * @throws ProductNotFoundException 
     * @throws InsufficientStockException 
     */
    public Sale checkout(Cart cart, String payment) throws IOException, InsufficientStockException, ProductNotFoundException {
        Order order = createOrder(cart);
        if (order == null) return null;
        boolean confirmed = confirmOrder(order);
        if (!confirmed) return null;

        Sale sale = new Sale(IdGenerator.nextSaleId(), order, payment, LocalDate.now().toString());
        sale.completeSale();
        saleRecord.addSale(sale);
        cart.clear();
        System.out.printf("   Checkout complete! Sale %s  Total: $%.2f%n",
                sale.getSaleId(), sale.getTotalAmount());
        return sale;
    }

    public void printOrders() {
        if (orders.isEmpty()) { System.out.println("  No orders yet."); return; }
        for (Order o : orders) System.out.println("  " + o);
    }

    public List<Order> getOrders() { return orders; }
}
