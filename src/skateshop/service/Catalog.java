package skateshop.service;

import skateshop.model.products.Product;
import skateshop.util.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private List<Product> products;

    public Catalog() {
        products = new ArrayList<>();
    }

    public void load() throws IOException {
        products = FileManager.loadCatalog();
    }

    public void save() throws IOException {
        FileManager.saveCatalog(products);
    }

    public void addProduct(Product p) throws IOException {
        products.add(p);
        save();
    }

    public boolean removeProduct(String id) throws IOException {
        boolean removed = products.removeIf(p -> p.getId().equals(id));
        if (removed) save();
        return removed;
    }

    public Product getProduct(String id) {
        return products.stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
    }

    public List<Product> getProducts() { return products; }

    public void updateProduct(Product updated) throws IOException {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(updated.getId())) {
                products.set(i, updated);
                save();
                return;
            }
        }
    }

    public void printAll() {
        if (products.isEmpty()) {
            System.out.println("  (catalog is empty)");
            return;
        }
        products.forEach(p -> System.out.println("  " + p));
    }

    public void printByType(String type) {
        boolean found = false;
        for (Product p : products) {
            if (p.getType().equalsIgnoreCase(type)) {
                System.out.println("  " + p);
                found = true;
            }
        }
        if (!found) System.out.println("  No products of type: " + type);
    }
}
