package skateshop;

import skateshop.exceptions.DuplicateUsernameException;
import skateshop.exceptions.HardnessOutOfRangeException;
import skateshop.exceptions.InsufficientStockException;
import skateshop.exceptions.ProductNotFoundException;
import skateshop.model.*;
import skateshop.model.products.*;
import skateshop.service.*;
import skateshop.util.IdGenerator;

import java.io.IOException;
import java.util.Scanner;

/**
 * SkateShop Console Application
 * ─────────────────────────────
 * Run this class from Eclipse (Run As → Java Application) or
 * from the terminal:  java -cp bin skateshop.Main
 *
 * Data is persisted in the  data/  folder as plain .txt files.
 */
public class Main {

    // ── Services ───────────────────────────────────────────────
    private static Catalog        catalog;
    private static StockManager   stockManager;
    private static SaleRecord     saleRecord;
    private static OrderService   orderService;
    private static ProductService productService;
    private static UserService    userService;

    // ── Session ────────────────────────────────────────────────
    private static User    currentUser = null;
    private static Cart    currentCart = null;
    private static Scanner sc          = new Scanner(System.in);

    // Main program
    public static void main(String[] args) throws HardnessOutOfRangeException {
        try {
			initServices();
		} catch (DuplicateUsernameException e) {
		    System.out.println("  Error initializing: " + e.getMessage());

		}
        mainLoop();
        System.out.println("\n  Goodbye! Come back and skate soon.");
    }

    // ── Initialisation ─────────────────────────────────────────
    private static void initServices() throws DuplicateUsernameException, HardnessOutOfRangeException {
        catalog       = new Catalog();
        stockManager  = new StockManager(catalog);
        saleRecord    = new SaleRecord();
        orderService  = new OrderService(stockManager, saleRecord);
        productService= new ProductService(catalog, stockManager);
        userService   = new UserService();

        try {
            catalog.load();
            userService.load();
            seedDemoDataIfEmpty();
        } catch (IOException e) {
            System.out.println("  Warning: could not load data files – " + e.getMessage());
        }
    }

    /** Inserts demo records the very first time (empty files). 
     * @throws DuplicateUsernameException 
     * @throws HardnessOutOfRangeException */
    private static void seedDemoDataIfEmpty() throws IOException, DuplicateUsernameException, HardnessOutOfRangeException {
        if (catalog.getProducts().isEmpty()) {
            catalog.addProduct(new Board("B001", "Zero",  49.99, 10, 8.0));
            catalog.addProduct(new Board("B002", "Santa Cruz", 54.99, 8, 8.25));
            catalog.addProduct(new Truck("T001", "Independent", 34.99, 15, 5.25));
            catalog.addProduct(new Truck("T002", "Venture",     31.99, 12, 5.0));
            catalog.addProduct(new Wheels("W001", "Spitfire", 24.99, 20, 52, "99A"));
            catalog.addProduct(new Wheels("W002", "OJ Wheels", 22.99, 18, 54, "87A"));
            catalog.addProduct(new Bearings("BR01", "Bones Reds", 9.99, 25));
        }
        if (userService.findByUsername("admin") == null) {
            userService.registerAdmin("admin", "admin123",
                    "admin@skateshop.com", "123 Skate Ave", "555-0001");
        }
        if (userService.findByUsername("tony") == null) {
            userService.registerCustomer("tony", "hawk99",
                    "tony@example.com", "900 Vert Lane", "555-0002");
        }
    }

    // ── Main loop ──────────────────────────────────────────────
    private static void mainLoop() {
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else if ("ADMIN".equals(currentUser.getRole())) {
                showAdminMenu();
            } else {
                showCustomerMenu();
            }
        }
    }


    //  LOGIN / REGISTER

    private static void showLoginMenu() {
        header("Welcome to SkateShop");
        System.out.println("  1. Login");
        System.out.println("  2. Register as Customer");
        System.out.println("  0. Exit");
        String choice = prompt("Select");
        switch (choice) {
            case "1":
            	
            	doLogin();   
            	
            	break;
            case "2":
            	
				doRegister();
            	
			break;
			
            case "0":
                saveAll();
                System.out.println("  See you soon!");
                System.exit(0);
                break;
            
            default: 
            invalid();
        }
    }

    private static void doLogin() {
        String user = prompt("Username");
        String pass = prompt("Password");
        currentUser = userService.login(user, pass);
        if (currentUser == null) {
            System.out.println("   Invalid credentials.");
        } else {
            System.out.println("   Welcome, " + currentUser.getUsername()
                    + " [" + currentUser.getRole() + "]");
            if (currentUser instanceof Customer) {
                currentCart = new Cart((Customer) currentUser);
            }
        }
    }

    private static void doRegister() {
        String user  = prompt("Username");
        String pass  = prompt("Password");
        String email = prompt("Email");
        String addr  = prompt("Address");
        String phone = prompt("Phone");
        try {
            Customer c = userService.registerCustomer(user, pass, email, addr, phone);
            if (c != null) System.out.println("  ✓ Account created. You can now log in.");
        } catch (IOException e) { 
            ioError(e); 
        } catch (DuplicateUsernameException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }


    //  ADMIN MENU
  
    private static void showAdminMenu() {
        header("Admin Panel – " + currentUser.getUsername());
        System.out.println("  ── Catalog ──");
        System.out.println("  1. View all products");
        System.out.println("  2. Add product");
        System.out.println("  3. Delete product");
        System.out.println("  ── Stock ──");
        System.out.println("  4. Add stock");
        System.out.println("  5. Remove stock");
        System.out.println("  6. Adjust stock");
        System.out.println("  7. View stock movements");
        System.out.println("  ── Sales ──");
        System.out.println("  8. View all sales");
        System.out.println("  9. Total revenue");
        System.out.println("  ── Users ──");
        System.out.println("  10. View customers");
        System.out.println("  ── Orders ──");
        System.out.println("  11. View all orders");
        System.out.println("  0. Logout");
        String choice = prompt("Select");
        switch (choice) {
            case "1":  adminViewCatalog();       break;
            case "2":  adminAddProduct();        break;
            case "3":  adminDeleteProduct();     break;
            case "4":  adminAddStock();          break;
            case "5":  adminRemoveStock();       break;
            case "6":  adminAdjustStock();       break;
            case "7":  adminStockMovements();    break;
            case "8":  adminViewSales();         break;
            case "9":  adminRevenue();           break;
            case "10": adminViewCustomers();     break;
            case "11": adminViewOrders();        break;
            case "0":  logout();                 break;
            default:   invalid();
        }
    }

    private static void adminViewCatalog() {
        header("Product Catalog");
        catalog.printAll();
    }

    private static void adminAddProduct() {
        header("Add Product");
        System.out.println("  Types: BOARD, TRUCK, WHEELS, BEARINGS");
        String type  = prompt("Type").toUpperCase();
        String id    = prompt("ID (e.g. B003)");
        String brand = prompt("Brand");
        double price = parseDouble(prompt("Price"));
        int stock    = parseInt(prompt("Initial stock"));

        try {
            switch (type) {
                case "BOARD": {
                    double w = parseDouble(prompt("Width (e.g. 8.25)"));
                    productService.addProduct(new Board(id, brand, price, stock, w));
                    break;
                }
                case "TRUCK": {
                    double w = parseDouble(prompt("Width (e.g. 5.25)"));
                    productService.addProduct(new Truck(id, brand, price, stock, w));
                    break;
                }
                case "WHEELS": {
                    int    w = parseInt(prompt("Diameter in mm (e.g. 52)"));
                    String h = prompt("Hardness (e.g. 99A)");
                    productService.addProduct(new Wheels(id, brand, price, stock, w, h));
                    break;
                }
                case "BEARINGS":
                    productService.addProduct(new Bearings(id, brand, price, stock));
                    break;
                default:
                    System.out.println("  Unknown type: " + type);
            }
        } catch (IOException e) { ioError(e); } catch (HardnessOutOfRangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private static void adminDeleteProduct() {
        header("Delete Product");
        String id = prompt("Product ID");
        try { productService.deleteProduct(id); } catch (IOException e) { ioError(e); }
    }

    private static void adminAddStock() {
        String id  = prompt("Product ID");
        int qty    = parseInt(prompt("Quantity to add"));
        try { stockManager.addStock(id, qty); } catch (IOException e) { ioError(e); }
    }

    private static void adminRemoveStock() {
        String id  = prompt("Product ID");
        int qty    = parseInt(prompt("Quantity to remove"));
        try { 
            stockManager.removeStock(id, qty); 
        } catch (IOException e) { 
            ioError(e); 
        } catch (InsufficientStockException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    private static void adminAdjustStock() {
        String id  = prompt("Product ID");
        int qty    = parseInt(prompt("New stock level"));
        try { stockManager.adjustStock(id, qty); } catch (IOException e) { ioError(e); }
    }

    private static void adminStockMovements() {
        header("Stock Movements");
        try { stockManager.printMovements(); } catch (IOException e) { ioError(e); }
    }

    private static void adminViewSales() {
        header("All Sales");
        saleRecord.printAll();
    }

    private static void adminRevenue() {
        System.out.printf("  Total revenue: $%.2f%n", saleRecord.totalRevenue());
    }

    private static void adminViewCustomers() {
        header("Customers");
        userService.printCustomers();
    }

    private static void adminViewOrders() {
        header("All Orders");
        orderService.printOrders();
    }


    //  CUSTOMER MENU

    private static void showCustomerMenu() {
        header("Shop – " + currentUser.getUsername());
        System.out.println("  1. Browse catalog");
        System.out.println("  2. View cart");
        System.out.println("  3. Add item to cart");
        System.out.println("  4. Remove item from cart");
        System.out.println("  5. Checkout");
        System.out.println("  6. My purchase history");
        System.out.println("  0. Logout");
        String choice = prompt("Select");
        switch (choice) {
            case "1": customerBrowse();   break;
            case "2": customerViewCart(); break;
            case "3": customerAddItem();  break;
            case "4": customerRemItem();  break;
            case "5": customerCheckout(); break;
            case "6": customerHistory();  break;
            case "0": logout();           break;
            default:  invalid();
        }
    }

    private static void customerBrowse() {
        header("Catalog");
        catalog.printAll();
    }

    private static void customerViewCart() {
        header("My Cart");
        currentCart.print();
    }

    private static void customerAddItem() {
        catalog.printAll();
        String id = prompt("Product ID to add");
        Product p;
		try {
			p = catalog.searchById(id);
		} catch (ProductNotFoundException e) {
			System.out.println("Product with id:" + id + " not found." );
			return;
		}
      
        int qty = parseInt(prompt("Quantity"));
        if (qty <= 0) { System.out.println("  Invalid quantity."); return; }
        if (p.getStock() < qty) {
            System.out.printf("  Only %d in stock.%n", p.getStock()); return;
        }
        currentCart.addItem(p, qty);
        System.out.println("  Added to cart.");
    }

    private static void customerRemItem() {
        currentCart.print();
        String id = prompt("Product ID to remove");
        boolean ok = currentCart.removeItem(id);
        System.out.println(ok ? "  Item removed." : "  Item not in cart.");
    }

    private static void customerCheckout() {
        if (currentCart.isEmpty()) { System.out.println("  Cart is empty."); return; }
        header("Checkout");
        currentCart.print();
        System.out.println("  Payment: 1. CASH   2. CARD");
        String pay = prompt("Choose").equals("2") ? "CARD" : "CASH";
        System.out.print("  Confirm checkout? (y/n): ");
        if (!"y".equalsIgnoreCase(sc.nextLine().trim())) return;
        try {
            orderService.checkout(currentCart, pay);
        } catch (InsufficientStockException e) {
            System.out.println("  Error: " + e.getMessage());
        } catch (ProductNotFoundException e) {
            System.out.println("  Error: " + e.getMessage());
        } catch (IOException e) { 
            ioError(e); 
        }
    }

    private static void customerHistory() {
        header("My Purchases");
        saleRecord.listByCustomer(currentUser.getUserId());
    }

    // ── Helpers ────────────────────────────────────────────────
    private static void logout() {
        System.out.println("  Logged out.");
        currentUser = null;
        currentCart = null;
    }
    
    private static void saveAll() {
        try {
            catalog.save();
            userService.save();
            System.out.println("  Data saved.");
        } catch (IOException e) {
            System.out.println("  Error saving data: " + e.getMessage());
        }
    }
    private static String prompt(String label) {
        System.out.print("  " + label + ": ");
        return sc.nextLine().trim();
    }

    private static double parseDouble(String s) {
        try { return Double.parseDouble(s); }
        catch (NumberFormatException e) { System.out.println("  Invalid number, using 0."); return 0; }
    }

    private static int parseInt(String s) {
        try { return Integer.parseInt(s); }
        catch (NumberFormatException e) { System.out.println("  Invalid number, using 0."); return 0; }
    }

    private static void invalid() { System.out.println("  Invalid option."); }
    private static void ioError(IOException e) { System.out.println("  IO Error: " + e.getMessage()); }



    private static void header(String title) {
        System.out.println("\n──── " + title + " ────");
    }
}
