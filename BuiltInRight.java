package Interpreter;
import java.util.ArrayList;

import Parser.FunctionNode;
import Parser.StatementNode;
import Parser.VariableNode;

public class BuiltInRight extends FunctionNode {

	/**
	 * A constructor which calls the following elements
	 * @param n
	 * @param variables
	 * @param constants
	 * @param statements
	 */
	public BuiltInRight(String n, ArrayList<VariableNode> variables, ArrayList<VariableNode> constants,
																	ArrayList<StatementNode> statements) {
		super(n, variables, constants, statements);
	}
	
	/**
	 * Returns the last character of a string
	 */
	public static void execute(ArrayList <StringDataType> SDT) {
		for (int i = 0; i < SDT.size(); i++) {
			String str = SDT.get(i).getValue();
			System.out.println(str.lastIndexOf(str));	
		}
	}
}
