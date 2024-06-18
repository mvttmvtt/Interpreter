package Interpreter;

import Lexer.Token.tokenType;

public class BooleanDataType extends InterpreterDataType{
	
	/*
	 * Private member
	 */
	private boolean bool;

	private tokenType type;
	
	private String name;
	
	/**
	 * Constructor which initializes the bool member
	 * @param bool
	 */
    public BooleanDataType(boolean bool) {
        setValue(bool);
    }
    
    public BooleanDataType(String name, tokenType type) {
    	this.type = type;
    	this.name = name;
    }

    /**
     * An accessor which returns the bool member
     * @return bool
     */
    public boolean getValue() {
        return bool;
    }

    /**
     * Initializes the value variable
     * @param bool
     */
    public void setValue(boolean bool) {
        this.bool = bool;
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
			return Boolean.toString(bool);
		}
	}

	/**
	 * Parser a specific string input
	 */
	public void fromString(String input) {
		this.bool = Boolean.parseBoolean(input);
	}
}
