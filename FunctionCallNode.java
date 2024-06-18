package Parser;

import java.util.ArrayList;

public class FunctionCallNode extends StatementNode {

	/*
	 * Private members
	 */
	private String name;
	private ArrayList<ParameterNode> parameters;

	/**
	 * Constructor which initializes the members
	 * @param name
	 * @param parameters
	 */
	public FunctionCallNode(String name, ArrayList<ParameterNode> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {

		return "\nCall Function--> " + name + ": " + parameters;
	}

}
