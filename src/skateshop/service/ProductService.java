package skateshop.service;

import skateshop.model.products.Product;

import java.io.IOException;

public class ProductService {
    private final Catalog catalog;
    private final StockManager stockManager;

    public ProductService(Catalog catalog, StockManager stockManager) {
        this.catalog = catalog;
        this.stockManager = stockManager;
    }

    public void addProduct(Product p) throws IOException {
        catalog.addProduct(p);
        System.out.println("  Product added: " + p.getId());
    }

    public void updateProduct(Product updated) throws IOException {
        catalog.updateProduct(updated);
        System.out.println("  Product updated: " + updated.getId());
    }

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
