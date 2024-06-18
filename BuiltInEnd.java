package Interpreter;
import java.util.ArrayList;

import Parser.FunctionNode;
import Parser.StatementNode;
import Parser.VariableNode;

public class BuiltInEnd extends FunctionNode {

	/**
	 * A constructor which calls the following elements
	 * @param n
	 * @param variables
	 * @param constants
	 * @param statements
	 */
	public BuiltInEnd(String n, ArrayList<VariableNode> variables, ArrayList<VariableNode> constants,
																	ArrayList<StatementNode> statements) {
		super(n, variables, constants, statements);
	}
	
	/**
	 * Returns the last index of an array
	 */
	@SuppressWarnings("rawtypes")
	public void execute(ArrayList <ArrayDataType> ADT) {
		
		for (int i = 0; i < ADT.size(); i++) {
			Object val = ADT.get(i).getValues().get(ADT.size());
		} 
	}
}
