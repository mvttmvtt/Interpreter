package Interpreter;

import Lexer.Token.tokenType;

public class IntegerDataType extends InterpreterDataType {

	/*
	 * Private members
	 */
	private int value;
	
	private tokenType type;
	
	private String name;

	/**
	 * Constructor which initializes the value member
	 * @param value
	 */
    public IntegerDataType(int value) {
        setValue(value);
    }

    public IntegerDataType(String name, tokenType type) {
    	this.type = type;
    	this.name = name;
    }
    
    /**
     * An accessor which returns the value member
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Initializes the value variable
     * @param value
     */
    public void setValue(int value) {
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
			return Integer.toString(value);
		}
	}

	/**
	 * Parser a specific string input
	 */
	public void fromString(String input) {

		this.value = Integer.parseInt(input);
	}

}
