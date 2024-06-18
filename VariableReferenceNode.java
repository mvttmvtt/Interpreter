package Parser;

public class VariableReferenceNode extends StatementNode {

	/*
	 * Represents the name of the variable
	 */
	private String name;
	
	private VariableNode variable;
	
	
	/*
	 * Represents the Array index expression
	 */
	private Node index;

	/**
	 * A constructor which initializes the private members
	 * @param name
	 */
	public VariableReferenceNode(String name) {
		this.name = name;
	}
	
	/**
	 * A constructor which initializes the private members
	 * @param name
	 * @param variable
	 * @param index
	 */
	public VariableReferenceNode(String name, VariableNode variable, Node index) {
		this.name = name;
		this.index = index;
		this.variable = variable;
	}
	
	/**
	 * A constructor which initializes the private members
	 * @param name
	 * @param index
	 */
	public VariableReferenceNode(String name, Node index) {
		this.name = name;
		this.index = index;
	}
	
	/**
	 * An accessor that allows the variable variable to be called in
	 * another file
	 * @return variable
	 */
	public VariableNode getVar() {
		return variable;
	}
	
	/**
	 * An accessor that allows the name variable to be called in
	 * another file
	 * @return name
	 */
	public String getrefName() {
		return name;
	}
	
	/**
	 * An accessor that allows the index variable to be called in
	 * another file
	 * @return index
	 */
	public Node getIndex() {
		return index;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		
		if (index != null) {
			return ""+ name +"["+index+"]";
		}
		if (variable != null) {
			return "" +variable;
		}
		
		return "" + name;
	}
}
