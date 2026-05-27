package skateshop.service;

import skateshop.exceptions.DuplicateUsernameException;
import skateshop.exceptions.ProductNotFoundException;
import skateshop.interfaces.Searchable;
import skateshop.model.Admin;
import skateshop.model.Customer;
import skateshop.model.User;
import skateshop.repository.FileManager;
import skateshop.util.IdGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Service for user management: registration, login, persistence, and search.
 */
public class UserService implements Searchable <User> {
	private HashMap<String, User> users = new HashMap<>();

    public UserService() {
        this.users = new HashMap<>();
    }

    
    /** Loads users from the users file into memory. */
    public void load() throws IOException {
        users = new HashMap<>();
        for (User u : FileManager.loadUsers()) {
            users.put(u.getUsername(), u);
        }
        
    }
    
    
    /** Saves all users to the users file. */
    public void save() throws IOException {
    	FileManager.saveUsers(new ArrayList<>(users.values()));

    }

    /** Returns the logged-in user, or null if credentials don't match */
    public User login(String username, String password) {
    	User u = users.get(username);
    	if (u != null && u.login(username, password)) return u;
    	return null;
    }
    
    
    /**
     * Registers a new customer.
     * @param username desired username (must be unique)
     * @param password password
     * @param email    customer email
     * @param address  customer address
     * @param phone    customer phone
     * @return the newly created Customer
     * @throws IOException if saving fails
     * @throws DuplicateUsernameException if username already exists
     */
    public Customer registerCustomer(String username, String password,
                                     String email, String address, String phone) throws IOException, DuplicateUsernameException {
        if (findByUsername(username) != null) {
            System.out.println("  Username already taken.");
            throw new DuplicateUsernameException(username);
        }
        Customer c = new Customer(IdGenerator.nextUserId(), username, password, email, address, phone);
        users.put(c.getUsername(), c);
        save();
        return c;
    }
    
    
    /**
     * Registers a new admin.
     * @param username desired username
     * @param password password
     * @param email    admin email
     * @param address  admin address
     * @param phone    admin phone
     * @return the newly created Admin
     * @throws IOException if saving fails
     */
    public Admin registerAdmin(String username, String password,
                               String email, String address, String phone) throws IOException {
        Admin a = new Admin(IdGenerator.nextUserId(), username, password, email, address, phone);
        users.put(a.getUsername(), a);
        save();
        return a;
    }
    
    
    /** Returns all users as a list (used by IdGenerator.initialize). */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    /** Finds a user by username, or returns null if not found. */
    public User findByUsername(String username) {
    	return users.get(username);
    }

    public void printCustomers() {
    	users.values().stream()
        .filter(u -> u instanceof Customer)
        .forEach(u -> System.out.println("  " + u));
    }
    
    
    /** Returns a set of all customer usernames. */
    public Set<String> clientNames() {
        return users.values().stream()
            .filter(u -> u instanceof Customer)
            .map(User::getUsername)
            .collect(Collectors.toSet());
    }
	@Override
	public User searchById(String id) throws ProductNotFoundException {
		User user = users.get(id);
	
		if(user==null) {throw new ProductNotFoundException("User with id: " + id + " not found");}
		
		return user;
	
	}

	@Override
	public List<User> searchByName(String nombre) {
		List<User> result = new ArrayList<User>();
		
		for (User u : users.values()) {
			if(u.getUsername().equalsIgnoreCase(nombre)) {
				result.add(u);
			}
		}
		return result;
		
		
	}
}