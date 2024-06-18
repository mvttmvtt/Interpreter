package Parser;

import java.util.ArrayList;

public class RepeatNode extends StatementNode {

	/*
	 * Private members
	 */
	private BooleanCompareNode condition;
	private ArrayList<StatementNode> statements;
	
	/*
	 * Constructor which initializes the condition member
	 */
	public RepeatNode(BooleanCompareNode condition) {
		this.condition = condition;
	}
	
	/*
	 * Constructor which initializes the condition member
	 */
	public RepeatNode(BooleanCompareNode condition, ArrayList<StatementNode> statements) {
		this.condition = condition;
		this.statements = statements;
	}

	/**
	 * An accessor that allows the condition variable to be called in
	 * another file
	 * @return condition
	 */
	public BooleanCompareNode getCondition() {
		return this.condition;
	}
	
	public ArrayList <StatementNode> getStatements() {
		return statements;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		return "\nRepeat Statement-> (" + condition + ") until " + statements;
	}
}
