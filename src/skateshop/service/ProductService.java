package skateshop.service;

import skateshop.model.products.Product;

import java.io.IOException;
/**
 * Service layer for product management, delegating to Catalog and StockManager.
 */
public class ProductService {
    private final Catalog catalog;
    private final StockManager stockManager;

    
    public ProductService(Catalog catalog, StockManager stockManager) {
        this.catalog = catalog;
        this.stockManager = stockManager;
    }

    
    /** Adds a product to the catalog and persists it. */
    public void addProduct(Product p) throws IOException {
        catalog.addProduct(p);
        System.out.println("  Product added: " + p.getId());
    }

    
    /** Updates an existing product in the catalog. */ 
    public void updateProduct(Product updated) throws IOException {
        catalog.updateProduct(updated);
        System.out.println("  Product updated: " + updated.getId());
    }

    
    /** Deletes a product by ID. Returns true if it existed. */
    public boolean deleteProduct(String id) throws IOException {
        boolean ok = catalog.removeProduct(id);
        System.out.println(ok ? "  Product removed: " + id : "  Product not found: " + id);
        return ok;
    }

    public Product getProduct(String id) {
        return catalog.getProduct(id);
    }

    public int checkStock(String id) {
        return stockManager.checkStock(id);
    }
}
