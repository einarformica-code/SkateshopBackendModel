package skateshop.service;

import skateshop.model.Sale;
import skateshop.repository.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
/**
 * Records and manages sales. Provides methods to list sales by customer or date,
 * compute total revenue, and retrieve sales in order.
 */
public class SaleRecord {
	private TreeMap<String, Sale> sales;

    public SaleRecord() {
    	 this.sales = new TreeMap<>();
    }
    
    /** Adds a sale and appends it to the sales file. */
    public void addSale(Sale s) throws IOException {
        sales.put(s.getSaleId(), s);
        FileManager.appendSale(s);
    }
    
    
    /** Prints all sales belonging to a specific customer ID. */
    public void listByCustomer(String customerId) {
        boolean found = false;
        for (Sale s : sales.values()) {
            if (s.getOrder().getCustomer().getUserId().equals(customerId)) {
                System.out.println("  " + s);
                found = true;
            }
        }
        if (!found) System.out.println("  No sales for customer: " + customerId);
    }
    
    
    /** Prints all sales that occurred on a specific date. */
    public void listByDate(String date) {
        boolean found = false;
        for (Sale s : sales.values()) {
            if (s.getSaleDate().equals(date)) {
                System.out.println("  " + s);
                found = true;
            }
        }
        if (!found) System.out.println("  No sales on: " + date);
    }
    
    
    /** Returns the total revenue from all sales. */
    public double totalRevenue() {
        return sales.values().stream().mapToDouble(Sale::getTotalAmount).sum();
    }

    public void printAll() {
        if (sales.isEmpty()) {
            System.out.println("  No sales recorded yet.");
            return;
        }
        for (Sale s : sales.values()) System.out.println("  " + s);
        System.out.printf("  ── Total revenue: $%.2f%n", totalRevenue());
    }
    
    
    public double incomeByDate(String date) {
        return sales.values().stream()
            .filter(s -> s.getSaleDate().equals(date))
            .mapToDouble(Sale::getTotalAmount)
            .sum();
    }
    
    
    /** Returns a list of sales sorted by total amount descending. */
    public List<Sale> salesOrderedByAmount() {
        return sales.values().stream()
            .sorted(Comparator.comparingDouble(Sale::getTotalAmount).reversed())
            .collect(Collectors.toList());
    }
    
    /** Returns a copy of all sales as a list. */
    public List<Sale> getSales() { return new ArrayList<>(sales.values()); }
}
