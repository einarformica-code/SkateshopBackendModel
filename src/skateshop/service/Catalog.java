package skateshop.service;

import skateshop.exceptions.ProductNotFoundException;
import skateshop.interfaces.Searchable;
import skateshop.model.products.Product;
import skateshop.repository.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class Catalog implements Searchable <Product> {
    private LinkedHashMap<String, Product> products;

    public Catalog() {
    	products =new LinkedHashMap<String, Product>();
    }

    public void load() throws IOException {
    	products = new LinkedHashMap<>();
    	for (Product p : FileManager.loadCatalog()) {
    	    products.put(p.getId(), p);
    	}
    }

    public void save() throws IOException {
        FileManager.saveCatalog(new ArrayList<>(products.values()));
    }

    public void addProduct(Product p) throws IOException {
    	products.put(p.getId(), p);
        save();
    }

    public boolean removeProduct(String id) throws IOException {
    	boolean removed = products.remove(id) != null;
        if (removed) save();
        return removed;
    }

    public Product getProduct(String id) {return products.get(id);}

    public List<Product> getProducts() { return new ArrayList<>(products.values()); }

    public void updateProduct(Product updated) throws IOException {
    	if (products.containsKey(updated.getId())) {
    	    products.put(updated.getId(), updated);
    	    save();
    	}
    }

    public void printAll() {
        if (products.isEmpty()) {
            System.out.println("  (catalog is empty)");
            return;
        }
        products.values().forEach(p -> System.out.println("  " + p));
    }

    public void printByType(String type) {
        boolean found = false;
        for (Product p : products.values()) {
            if (p.getType().equalsIgnoreCase(type)) {
                System.out.println("  " + p);
                found = true;
            }
        }
        if (!found) System.out.println("  No products of type: " + type);
    }
    
    /**
     * Receives type of product and returns (if existing) the most expensive one. 
     * Optional makes this operation safe regarding NullPointerExceptions.
     * @param typpe
     * @return
     */
    public Optional<Product> mostExpensiveByType(String type) {
        return products.values().stream()
            .filter(p -> p.getType().equalsIgnoreCase(type))
            .max(Comparator.comparingDouble(Product::getPrice));
    }

	@Override
	public Product searchById(String id) throws ProductNotFoundException {
		Product product = products.get(id);
		
		if(product == null) {throw new ProductNotFoundException("Product with ID " + id + " not found");}
		
		return product;
	}

	@Override
	public List <Product> searchByName(String name) {
		List <Product> result = new ArrayList<>();
		
		for (Product p : products.values()) {
			if (p.getBrand().equalsIgnoreCase(name)) {
				result.add(p);
			}
		}
		return result;
		
	}


}
