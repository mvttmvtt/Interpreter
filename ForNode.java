package Parser;

import java.util.ArrayList;

public class ForNode extends StatementNode {
	
	/*
	 * Private members
	 */
	private VariableReferenceNode variable;
	private Node forTo, forFrom;
	private ArrayList<StatementNode> statements;
	
	/**
	 * Constructor which initializes the forTo, forFrom, and variable members
	 * @param variable
	 * @param forTo
	 * @param forFrom
	 */
	public ForNode(VariableReferenceNode variable, Node forTo, Node forFrom) {
		this.variable = variable;
		this.forTo = forTo;
		this.forFrom = forFrom;
	}

	/**
	 * Constructor which initializes the forTo, forFrom, variable, and statements members
	 * @param variable
	 * @param forTo
	 * @param forFrom
	 * @param statements
	 */
	public ForNode(VariableReferenceNode variable, Node forTo, Node forFrom, ArrayList<StatementNode> statements) {
		this.variable = variable;
		this.forTo = forTo;
		this.forFrom = forFrom;
		this.statements = statements;
	}
	
	/**
	 * An accessor that allows the variable variable to be called in
	 * another file
	 * @return variable
	 */
	public VariableReferenceNode getVariableReferenceNode () {
		return this.variable;
	}
	
	/**
	 * An accessor that allows the forFrom variable to be called in
	 * another file
	 * @return forFrom
	 */
	public Node getForFrom() {
		return this.forFrom;
	}
	
	/**
	 * An accessor that allows the forTo variable to be called in
	 * another file
	 * @return forTo
	 */
	public Node getForTo() {
		return this.forTo;
	}
	
	public ArrayList <StatementNode> getStatements() {
		return statements;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		return "\nFor Loop->" + variable + " From->" + forFrom + " To->" + forTo;
	}
}
