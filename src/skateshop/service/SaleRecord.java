package skateshop.service;

import skateshop.model.Sale;
import skateshop.repository.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SaleRecord {
	private TreeMap<String, Sale> sales;

    public SaleRecord() {
    	 this.sales = new TreeMap<>();
    }

    public void addSale(Sale s) throws IOException {
        sales.put(s.getSaleId(), s);
        FileManager.appendSale(s);
    }

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
    
    public List<Sale> ventasOrdenadasPorImporte() {
        return sales.values().stream()
            .sorted(Comparator.comparingDouble(Sale::getTotalAmount).reversed())
            .collect(Collectors.toList());
    }
    
    
    public List<Sale> getSales() { return new ArrayList<>(sales.values()); }
}
