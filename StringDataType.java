package Interpreter;

import Lexer.Token.tokenType;

public class StringDataType extends InterpreterDataType{
	
	/*
	 * Private members
	 */
	private String value;
	
	private tokenType type;

	/**
	 * Constructor which initializes the value member
	 * @param value
	 */
    public StringDataType(String value) {
        setValue(value);
    }
    
    public StringDataType(String value, tokenType type) {
    	this.type = type;
    	this.value = value;
    }

    /**
     * An accessor which returns the value member
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * Initializes the value variable
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
	
    /**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		if (type != null) {
			return "" + value + " of Type "+ type;
		}
		else {
			return value;
		}
	}

	/**
	 * Parsers a specific string input
	 */
	public void fromString(String input) {

		this.value = input;
	}
}
