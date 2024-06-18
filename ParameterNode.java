package Parser;

import Lexer.Token;

public class ParameterNode extends Node {
	
	/*
	 * Private members
	 */
	private VariableReferenceNode Var;
	private Node notVar;
	private Token.tokenType type;
	
	/**
	 * Constructor which initializes the members if there is a variableReferenceNode
	 * @param condition
	 * @param Var
	 */
	public ParameterNode(VariableReferenceNode Var, Token.tokenType type) {
		this.Var = Var;
		this.type = type;
	}
	
	/**
	 * Constructor which initializes the members if there isn't a variableReferenceNode
	 * @param condition
	 * @param notVar
	 */
	public ParameterNode(Node notVar, Token.tokenType type) {
		this.notVar = notVar;
		this.type = type;
	}
	
	public Node getValue() {
		if (Var == null) {
			return notVar;
		}
		else {
			return Var;
		}
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		if (Var == null) {
			return "Variable: " + notVar + "  Type: " + type;
		}
		return "Variable: " + Var + "  Type: " + type;
	}
}
