package skateshop.model;

import skateshop.interfaces.Exportable;

public class Sale implements Exportable {
    private String saleId;
    private Order order;
    private String payment;   // e.g. "CASH", "CARD"
    private String saleDate;
    private double totalAmount;

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
