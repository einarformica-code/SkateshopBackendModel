package skateshop.model.products;

import skateshop.exceptions.HardnessOutOfRangeException;

/**
 * Wheel is the representation of a Skateboard wheel.
 * 
 * <p>
 * Its specific attributes consist of width (mm) and hardness expressed in the durometer scale,
 * in a range betwen 79A-101A
 *
 */
public class Wheels extends Product {
    private int width;       // mm
    private String hardness; // e.g. "99A"

    /**
     * This constructor will be called by adminAddProduct() when the chosen option is Wheels.
     * 
     * <p>
     * It validates 
     * @param id
     * @param brand
     * @param price
     * @param stock
     * @param width
     * @param hardness
     * @throws HardnessOutOfRangeException
     */
    public Wheels(String id, String brand, double price, int stock, int width, String hardness) throws HardnessOutOfRangeException {
        super(id, brand, price, stock);
        this.width = width;
        
        //Hardness validation logic
        this.hardness=  validateHardness(hardness) ? hardness : null;

    
    }

    public int getWidth()        { return width; }
    public String getHardness()  { return hardness; }
    public void setWidth(int width)          { this.width = width; }
    
    


    @Override public String getType() { return "WHEELS"; }

    @Override
    public String toFileString() {
        return String.format("WHEELS|%s|%s|%.2f|%d|%d|%s",
                getId(), getBrand(), getPrice(), getStock(), width, hardness);
    }

    @Override
    public String toString() {
        return super.toString() + String.format("  Width:%dmm  Hardness:%s", width, hardness);
    }
    /**
     * Validates if the hardness is in the specified range.
     * 
     * <p>
     * Receives a string, converts the numerical part (before letter A) to an int.
     * @param hardness
     * @return true if the numerical part is in the accepted range.
     * @throws HardnessOutOfRangeException if the numerical part is out of range.
     */
    public boolean validateHardness(String hardness) throws HardnessOutOfRangeException {
    	String auxiliarString  = hardness;
    	int positionOfA = auxiliarString.indexOf('A');
    	String nummericalPartOfString = auxiliarString.substring(0, positionOfA);
    	int extractedHardnessInt = Integer.valueOf(nummericalPartOfString);
    	
    	
    	if (extractedHardnessInt<78 || extractedHardnessInt > 101) {
    		throw new HardnessOutOfRangeException(hardness);
    	}else {
        	return true;
    	}
    }
    
}
