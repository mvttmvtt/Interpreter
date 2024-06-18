package Parser;

import Lexer.Token;
import Lexer.Token.tokenType;

/**
 * This node represents variables
 * @author Matthew Welsh
 */
public class VariableNode extends StatementNode {
	
	/*
	 * Private members
	 */
	private boolean changeable;
	private String name;
	private Node value;
	private Token.tokenType type;
	float realFrom, realTo;
	int intFrom, intTo;
	String StringFrom, StringTo;
	
	/**
	 * Constructors which initializes the private members
	 * @param n
	 * @param changeable
	 * @param type
	 * @param value
	 */
	public VariableNode (String n, boolean changeable, Token.tokenType type, Node value) {
		name = n;
		this.changeable = changeable;
		this.type = type;
		this.value = value;
	}
	
	public VariableNode (String n, boolean changeable, Token.tokenType type) {
		name = n;
		this.changeable = changeable;
		this.type = type;
	}
	
	public VariableNode (String n, boolean changeable, Node value) {
		name = n;
		this.changeable = changeable;
		this.value = value;
	}
	
	public VariableNode (String n, boolean changeable, Token.tokenType type, float realFrom, float realTo) {
		name = n;
		this.changeable = changeable;
		this.type = type;
		this.realFrom = realFrom;
		this.realTo = realTo;
	}
	
	public VariableNode (String n, boolean changeable, Token.tokenType type, int intFrom, int intTo) {
		name = n;
		this.changeable = changeable;
		this.type = type;
		this.intFrom = intFrom;
		this.intTo = intTo;
	}
	
	public VariableNode (String n, boolean changeable, Token.tokenType type, String StringFrom, String StringTo) {
		name = n;
		this.changeable = changeable;
		this.type = type;
		this.StringFrom = StringFrom;
		this.StringTo = StringTo;
	}
	
	/**
	 * An accessor that allows the name variable to be called in
	 * another file
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * An accessor that allows the changeable variable to be called in
	 * another file
	 * @return changeable
	 */
	public boolean isChangeable() {
		return changeable;
	}
	
	/**
	 * An accessor that allows the value variable to be called in
	 * another file
	 * @return value
	 */
	public String getVal() {
		return ""+value;
	}
	
	/**
	 * An accessor that allows the type variable to be called in
	 * another file
	 * @return type
	 */
	public tokenType getType() {
		return type;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		
		if (type == tokenType.NUMBER || type == tokenType.CHARACTERLITERAL || type == tokenType.STRINGLITERAL) {
			return "Name: " + name + "  Value-->" + value;
		
		} else if (type == tokenType.Integer){
			type = tokenType.INTEGER;
			return "Variable: "+ name + "  Type: "+ type + " from " + intFrom + " to " + intTo;
			
		} else if (type == tokenType.Real) {
			type = tokenType.REAL;
			return "Variable: "+ name + "  Type: "+ type + " from " + realFrom + " to " + realTo;
			
		} else if (type == tokenType.StrValue) {
			return "Variable: "+ name + "  Type: "+ tokenType.STRINGLITERAL + " from " + StringFrom + " to " + StringTo;
				
		} else {
			return "Variable: "+ name + "  Type: " + type;
		}
	}
}
