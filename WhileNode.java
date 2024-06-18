package Parser;

import java.util.ArrayList;

public class WhileNode extends StatementNode {

	/*
	 * Private members
	 */
	BooleanCompareNode condition;
	ArrayList <StatementNode> statements;
	
	/*
	 * Constructor which initializes the condition member
	 */
	public WhileNode(BooleanCompareNode condition) {
		this.condition = condition;
	}
	
	/*
	 * Constructor which initializes the statements member
	 */
	public WhileNode(ArrayList <StatementNode> statements) {
		this.statements = statements;
	}
	
	/*
	 * Constructor which initializes the condition and statements members
	 */
	public WhileNode(BooleanCompareNode condition, ArrayList <StatementNode> statements) {
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
		return "\nWhile Loop-> (" + condition + ")";
	}
}
