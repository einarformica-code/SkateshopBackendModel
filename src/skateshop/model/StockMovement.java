package skateshop.model;

import skateshop.model.products.Product;
import skateshop.repository.Repository;

public class StockMovement {
	
	private Repository<StockMovement> history = new Repository<>();

	
    public enum MovementType { ENTRY, EXIT, ADJUSTMENT }
    
    private MovementType movementType;
    private int qty;
    private String date;
    private int previousStock;
    private int resultingStock;
    private Product product;

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
