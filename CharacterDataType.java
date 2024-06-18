package Interpreter;

import Lexer.SyntaxErrorException;
import Lexer.Token.tokenType;

public class CharacterDataType extends InterpreterDataType{
	
	/*
	 * Private members
	 */
	private char value;
	
	private tokenType type;
	
	private String name;	

	/**
	 * Constructor which initializes the value member
	 * @param value
	 */
    public CharacterDataType(char value) {
        setValue(value);
    }
    
    public CharacterDataType(String name, tokenType type) {
    	this.type = type;
    	this.name = name;
    }

    /**
     * An accessor which returns the value member
     * @return value
     */
    public char getValue() {
        return value;
    }

    /**
     * Initializes the value variable
     * @param value
     */
    public void setValue(char value) {
        this.value = value;
    }
	
    /**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		if (name != null) {
			return "" + name + " of Type "+ type;
		}
		else {
			return Character.toString(value);
		}
	}

	/**
	 * Parsers a specific string input
	 */
	public void fromString(String input) {

		if (input.length() != 1) {
			try {
				throw new SyntaxErrorException("Error: Character length exceeds 1.");
			} catch (SyntaxErrorException e) {
				e.printStackTrace();
			}
		}
		this.value = input.charAt(0);
	}
}
