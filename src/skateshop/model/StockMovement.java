package skateshop.model;

import skateshop.model.products.Product;
import skateshop.repository.Repository;
/**
 * Records a change in stock for a product: entry, exit, or adjustment.
 * Stores the previous stock, new stock, quantity changed, and the date.
 */
public class StockMovement {
	
/**
 * Type of stock movement
 * <p>
 * Defines the nature of the operation done involving stock.
 */
	
    public enum MovementType {
    	//Entry of Stock
    	ENTRY,
    	//Elimination of stock
    	EXIT, 
    	//Modification of stock
    	ADJUSTMENT }
    
    private MovementType movementType;
    private int qty;
    private String date;
    private int previousStock;
    private int resultingStock;
    private Product product;
    
    /**
     * Constructs a stock movement record.
     * @param product        the product affected
     * @param movementType   type of movement (ENTRY, EXIT, ADJUSTMENT)
     * @param qty            quantity added/removed (absolute value)
     * @param date           date of the movement
     * @param previousStock  stock before the movement
     * @param resultingStock stock after the movement
     */
    public StockMovement(Product product, MovementType movementType,
                         int qty, String date, int previousStock, int resultingStock) {
        this.product = product;
        this.movementType = movementType;
        this.qty = qty;
        this.date = date;
        this.previousStock = previousStock;
        this.resultingStock = resultingStock;
    }

    public MovementType getMovementType() { return movementType; }
    public int getQty()                   { return qty; }
    public String getDate()               { return date; }
    public int getPreviousStock()         { return previousStock; }
    public int getResultingStock()        { return resultingStock; }
    public Product getProduct()           { return product; }

    public String toFileString() {
        return String.format("MOVEMENT|%s|%s|%d|%s|%d|%d",
                product.getId(), movementType, qty, date, previousStock, resultingStock);
    }

    @Override
    public String toString() {
        return String.format("[%s] Product:%-6s  Type:%-12s  Qty:%4d  %d -> %d  Date:%s",
                date, product.getId(), movementType, qty, previousStock, resultingStock);
    }
}
