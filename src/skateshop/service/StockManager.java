package skateshop.service;

import skateshop.exceptions.InsufficientStockException;
import skateshop.model.StockMovement;
import skateshop.model.StockMovement.MovementType;
import skateshop.model.products.Product;
import skateshop.util.FileManager;

import java.io.IOException;
import java.time.LocalDate;

public class StockManager {
    private static final int MIN_STOCK = 3;
    private final Catalog catalog;

    public StockManager(Catalog catalog) {
        this.catalog = catalog;
    }

    public boolean addStock(String productId, int qty) throws IOException {
        Product p = catalog.getProduct(productId);
        if (p == null) { System.out.println("  Product not found: " + productId); return false; }
        int prev = p.getStock();
        p.setStock(prev + qty);
        catalog.save();
        StockMovement sm = new StockMovement(p, MovementType.ENTRY, qty,
                LocalDate.now().toString(), prev, p.getStock());
        FileManager.appendStockMovement(sm);
        System.out.printf("  Stock added. %s now has %d units.%n", productId, p.getStock());
        alert(p);
        return true;
    }

    public boolean removeStock(String productId, int qty) throws IOException,InsufficientStockException {
        Product p = catalog.getProduct(productId);
        if (p == null) { System.out.println("  Product not found: " + productId); return false; }
        if (p.getStock() < qty) {
            throw new InsufficientStockException(productId, p.getStock(), qty);
        }
        int prev = p.getStock();
        p.setStock(prev - qty);
        catalog.save();
        StockMovement sm = new StockMovement(p, MovementType.EXIT, qty,
                LocalDate.now().toString(), prev, p.getStock());
        FileManager.appendStockMovement(sm);
        alert(p);
        return true;
    }

    public boolean adjustStock(String productId, int newQty) throws IOException {
        Product p = catalog.getProduct(productId);
        if (p == null) { System.out.println("  Product not found: " + productId); return false; }
        int prev = p.getStock();
        p.setStock(newQty);
        catalog.save();
        StockMovement sm = new StockMovement(p, MovementType.ADJUSTMENT,
                Math.abs(newQty - prev), LocalDate.now().toString(), prev, newQty);
        FileManager.appendStockMovement(sm);
        System.out.printf("  Stock adjusted: %s  %d -> %d%n", productId, prev, newQty);
        alert(p);
        return true;
    }

    public int checkStock(String productId) {
        Product p = catalog.getProduct(productId);
        return p != null ? p.getStock() : -1;
    }

    private void alert(Product p) {
        if (p.getStock() <= MIN_STOCK) {
            System.out.printf("  ⚠  LOW STOCK ALERT: %s (%s) has only %d unit(s) left!%n",
                    p.getBrand(), p.getId(), p.getStock());
        }
    }

    public void printMovements() throws IOException {
        var lines = FileManager.loadStockMovementLines();
        if (lines.isEmpty()) { System.out.println("  No stock movements recorded."); return; }
        lines.forEach(l -> System.out.println("  " + l));
    }
}
