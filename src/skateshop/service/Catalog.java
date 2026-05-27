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
/**
 * Manages the product catalog, storing products in a LinkedHashMap for ordered access.
 * Provides CRUD operations, search capabilities, and persistence via FileManager.
 * 
 * <p>This class implements Searchable to allow searching by ID or brand name.
 * 
 * @author Einar Formica
 * @version 1.0
 */
public class Catalog implements Searchable <Product> {
    private LinkedHashMap<String, Product> products;
    
    /**
     * Constructs an empty catalog using a LinkedHashMap to preserve insertion order.
     */
    public Catalog() {
    	products =new LinkedHashMap<String, Product>();
    }
    /**
     * Loads products from the data file into memory.
     * Replaces any existing in-memory catalog with the persisted data.
     * 
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void load() throws IOException {
    	products = new LinkedHashMap<>();
    	for (Product p : FileManager.loadCatalog()) {
    	    products.put(p.getId(), p);
    	}
    }
    
    
    /**
     * Saves the current in-memory catalog to the data file.
     * Overwrites the existing file completely.
     * 
     * @throws IOException if an I/O error occurs while writing the file
     */
    public void save() throws IOException {
        FileManager.saveCatalog(new ArrayList<>(products.values()));
    }
    /**
     * Adds a new product to the catalog and persists the change.
     * 
     * @param p the product to add
     * @throws IOException if saving to disk fails
     */
    public void addProduct(Product p) throws IOException {
    	products.put(p.getId(), p);
        save();
    }
    
    
    /**
     * Removes a product from the catalog by its ID.
     * 
     * @param id the unique identifier of the product to remove
     * @return true if the product was found and removed, false otherwise
     * @throws IOException if saving to disk fails
     */
    public boolean removeProduct(String id) throws IOException {
    	boolean removed = products.remove(id) != null;
        if (removed) save();
        return removed;
    }

    public Product getProduct(String id) {return products.get(id);}

    public List<Product> getProducts() { return new ArrayList<>(products.values()); }
    
    
    /**
     * Updates an existing product in the catalog.
     * 
     * @param updated the product with updated fields (must have existing ID)
     * @throws IOException if saving to disk fails
     */
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
    
    
    /**
     * Prints only products of a specific type (e.g., "BOARD", "WHEELS").
     * 
     * @param type the product type to filter by (case-insensitive)
     */
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
    
    
    /**
     * Searches for a product by its exact ID.
     * 
     * @param id the product identifier to search for
     * @return the Product with the matching ID
     * @throws ProductNotFoundException if no product with the given ID exists
     */
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
