package Parser;

/**
 * This node represents strings
 * @author Matthew Welsh
 */
public class StringNode extends Node {

	/*
	 * String member
	 */
	private String string;
	
	/**
	 * A constructor that initializes the boolean member
	 * @param s - String variable
	 */
	public StringNode(String s) {
		string = s;
	}
	
	/**
	 * An accessor that allows a string member
	 * to be called in another file
	 * @return string
	 */
	public String getString() {
		return string;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		return "String: "+ string;
	}
}
