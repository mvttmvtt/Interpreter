package Interpreter;
import java.util.ArrayList;

import Parser.FunctionNode;
import Parser.StatementNode;
import Parser.VariableNode;

public class BuiltInGetRandom extends FunctionNode {

	/**
	 * A constructor which calls the following elements
	 * @param n
	 * @param variables
	 * @param constants
	 * @param statements
	 */
	public BuiltInGetRandom(String n, ArrayList<VariableNode> variables, ArrayList<VariableNode> constants,
																		ArrayList<StatementNode> statements) {
		super(n, variables, constants, statements);
	}
	
	/**
	 * Returns a random number
	 */
	public static void execute(ArrayList <InterpreterDataType> IDT) {
			double random = Math.random();
			System.out.println(random);
	}
}
