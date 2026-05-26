package skateshop.exceptions;
/**Exception thrown when the hardness of the wheel is not contained within the defined range.
 * <p>
 * Designed to be thrown in the Wheel class constructor when the numerical part is
 * out of the specified range.
 *  *@author Einar Formica
	@version 1.0
 */
public class HardnessOutOfRangeException  extends Exception{
	public HardnessOutOfRangeException(String hardness) {
    	super("Hardness out of range:" +hardness + "./n Range:78A-101A");
    }
}
