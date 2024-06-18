package Parser;

/**
 * This node represents character expressions
 * @author Matthew Welsh
 */
public class CharacterNode extends Node {

	/*
	 * character member
	 */
	private char c;
	
	/**
	 * A constructor that initializes the character member
	 * @param right_val - boolean variable
	 */
	public CharacterNode(char c) {
		this.c = c;
	}
	
	/**
	 * An accessor that allows a character member
	 * to be called in another file
	 * @return c
	 */
	public char getChar() {
		return c;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		return "Character: "+ c;
	}
}
