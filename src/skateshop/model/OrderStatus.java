package skateshop.model;

/**
 * Defines the 4 possible states of the order.
 * 
 * Implemented to tack the flow of the orders during different times in the execution of the program.
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    SHIPPED,
    CANCELED
}
