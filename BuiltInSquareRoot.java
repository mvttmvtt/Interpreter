package Interpreter;
import java.util.ArrayList;

import Parser.FunctionNode;
import Parser.StatementNode;
import Parser.VariableNode;

public class BuiltInSquareRoot extends FunctionNode {

	/**
	 * A constructor which calls the following elements
	 * @param n
	 * @param variables
	 * @param constants
	 * @param statements
	 */
	public BuiltInSquareRoot(String n, ArrayList<VariableNode> variables, ArrayList<VariableNode> constants,
																			ArrayList<StatementNode> statements) {
		super(n, variables, constants, statements);
	}
	
	/**
	 * Returns the square root of a number
	 */
	public static void execute(final ArrayList<RealDataType> num) {
		for (int i = 0; i < num.size(); i++) {
			float number = (float) Math.sqrt(num.get(i).getValue());
			System.out.println(number);	
		}
	}
}
