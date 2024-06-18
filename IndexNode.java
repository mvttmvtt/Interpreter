package Parser;

/**
 * This node class represents array Indices
 * @author Matthew Welsh
 */
public class IndexNode extends Node {
	
	/*
	 * Private members
	 */
	private String value;
	private IndexNode value2;

	/**
	 * A constructor that initializes the String member
	 * @param value1 - integer variable
	 */
	public IndexNode (String value) {
		this.value = value;
	}
	
	/**
	 * A constructor that initializes the String 
	 * and IndexNode members
	 * @param value1 - integer variable
	 * @param value2 - IndexNode variable
	 */
	public IndexNode (String value, IndexNode value2) {
		this.value = value;
		this.value2 = value2;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		if (value2 != null) {
			return ""+value+value2;
		}
		return ""+value;
	}
}
