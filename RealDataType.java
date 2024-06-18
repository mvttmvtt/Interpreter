package Interpreter;

import Lexer.Token.tokenType;

public class RealDataType extends InterpreterDataType {
	
	/*
	 * Private members
	 */
	private float real;
	
	private tokenType type;
	
	private String name;
	
	/**
	 * Constructor which initializes the real member
	 * @param real
	 */
	public RealDataType(float real) {
       setValue(real);
    }
	
	public RealDataType(String name, tokenType type) {
    	this.type = type;
    	this.name = name;
    }

	/**
     * An accessor which returns the real member
     * @return real
     */
    public float getValue() {
        return real;
    }

    /**
     * Initializes the real variable
     * @param real
     */
    public void setValue(float real) {
        this.real = real;
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
			return Float.toString(real);
		}
	}

	/**
	 * Parser a specific string input
	 */
	public void fromString(String input) {

		this.real = Float.parseFloat(input);
	}
	
}
