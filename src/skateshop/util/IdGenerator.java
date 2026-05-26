package skateshop.util;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * Class used for identificator generation
 * 
 * It is used to guarantee exclusive id's in new orders, sales and users.  
 */
public class IdGenerator {
	/**
	 * Counter for order ids. 
	 * Initialised in 1000
	 */
    private static final AtomicInteger orderCount = new AtomicInteger(1000);
    
	/**
	 * Counter for sale ids. 
	 * Initialised in 500
	 */
    private static final AtomicInteger saleCount  = new AtomicInteger(500);
	/**
	 * Counter for user ids. 
	 * Initialised in 1
	 */
    private static final AtomicInteger userCount  = new AtomicInteger(1);
    
    /**
     * Generates a new id for a order
     * <p>
     * Concatenates "ORD-" representing order with current value of counter and increments it.
     * <p>
     * @returns ID with format "ORD-XXXX"
     */
    public static String nextOrderId() { return "ORD-" + orderCount.getAndIncrement(); }
    
    /**
     * Generates a new id for a sale
     * <p>
     * Concatenates "SAL-" representing sale with current value of counter and increments it.
     * <p>
     * @returns ID with format "SAL-XXXX"
     */
    public static String nextSaleId()  { return "SAL-" + saleCount.getAndIncrement(); }
    
    /**
     * Generates a new id for a User
     * <p>
     * Concatenates "USR-" representing User with current value of counter and increments it.
     * String format used to add 0s from the "-"character until the start of the number if needed
     * Ensuring all IDS follow this pattern : "USR-001".."USR-020" and not "USR-1".."USR-127"
     * <p>
     * @returns ID with format "USR-XXXX"
     */
    public static String nextUserId()  { return "USR-" + String.format("%03d", userCount.getAndIncrement()); }
}
