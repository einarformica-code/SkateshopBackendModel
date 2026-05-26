package skateshop.exceptions;

public class HardnessOutOfRangeException  extends Exception{
	public HardnessOutOfRangeException(String hardness) {
    	super("Hardness out of range:" +hardness + "./n Range:78A-101A");
    }
}
