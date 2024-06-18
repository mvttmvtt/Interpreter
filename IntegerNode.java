package Parser;

/**
 * This node class represents integers
 * @author Matthew Welsh
 */
public class IntegerNode extends Node {
	
	/*
	 * integer member
	 */
	private int i;
	
	/**
	 * A constructor that initializes the integer member
	 * @param i - integer variable
	 */
	public IntegerNode(int i) {
		this.i = i;
	}
	
	/**
	 * An accessor that allows a integer member
	 * to be called in another file
	 * @return i
	 */
	public int getInt() {
		return i;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		return "Integer: "+ i;
	}
}
