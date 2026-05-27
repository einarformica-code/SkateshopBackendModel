package skateshop.model;

import skateshop.interfaces.Exportable;
/**
 * Represents a completed sale, linked to an order and a payment method.
 * Sales are recorded after successful checkout.
 */
public class Sale implements Exportable {
    private String saleId;
    private Order order;
    private String payment;   // e.g. "CASH", "CARD"
    private String saleDate;
    private double totalAmount;
    
    /**
     * Constructs a Sale record.
     * @param saleId    unique sale identifier
     * @param order     the order being sold
     * @param payment   payment method (CASH/CARD)
     * @param saleDate  date of the sale
     */
    public Sale(String saleId, Order order, String payment, String saleDate) {
        this.saleId = saleId;
        this.order = order;
        this.payment = payment;
        this.saleDate = saleDate;
        this.totalAmount = order.getTotal();
    }

    public String getSaleId()      { return saleId; }
    public Order getOrder()        { return order; }
    public String getPayment()     { return payment; }
    public String getSaleDate()    { return saleDate; }
    public double getTotalAmount() { return totalAmount; }
    
    /** Marks the associated order as CONFIRMED. */
    public void completeSale() {
        order.setStatus(OrderStatus.CONFIRMED);
    }

    /** Single pipe-separated line */
    public String toFileString() {
        return String.format("SALE|%s|%s|%s|%s|%s|%.2f",
                saleId, order.getOrderId(),
                order.getCustomer().getUserId(),
                payment, saleDate, totalAmount);
    }

    @Override
    public String toString() {
        return String.format("Sale #%s  Order:%s  Customer:%-12s  Payment:%-6s  Date:%s  Total:$%.2f",
                saleId, order.getOrderId(),
                order.getCustomer().getUsername(),
                payment, saleDate, totalAmount);
    }

	@Override
	public String toDisplayString() {
		// TODO Auto-generated method stub
		return toString();
	}
}
