package Interpreter;

public abstract class InterpreterDataType {

	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	public abstract String toString();
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	public abstract void fromString(String input);

}
