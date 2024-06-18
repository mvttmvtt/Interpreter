package Parser;

/**
 * This node represents boolean expressions
 * @author Matthew Welsh
 */
public class BooleanNode extends Node {

	/*
	 * boolean member 
	 */
	private boolean bool;
	
	/**
	 * A constructor that initializes the boolean member
	 * @param b - boolean variable
	 */
	public BooleanNode(boolean b) {
		bool = b;
	}
	
	/**
	 * An accessor that allows a boolean member
	 * to be called in another file
	 * @return bool
	 */
	public boolean getBool() {
		return bool;
	}

	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		return "Boolean: "+ bool;
	}
	
}
