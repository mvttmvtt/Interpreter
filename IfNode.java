package Parser;

import java.util.ArrayList;

public class IfNode extends StatementNode {

	/*
	 * Private members
	 */
	private BooleanCompareNode condition;
	private IfNode nextIf;
	private ArrayList <StatementNode> statements;
	
	/*
	 * Constructor which initializes the condition member
	 */
	public IfNode(BooleanCompareNode condition) {
		this.condition = condition;
	}
	
	/*
	 * Constructor which initializes the statements member
	 */
	public IfNode(ArrayList <StatementNode> statements) {
		this.statements = statements;
	}
	
	/*
	 * Constructor which initializes the condition and statements members
	 */
	public IfNode(BooleanCompareNode condition, ArrayList <StatementNode> statements) {
		this.condition = condition;
		this.statements = statements;
	}
	
	/*
	 * Constructor which initializes the condition, nextIf, and statements members
	 */
	public IfNode(BooleanCompareNode condition, IfNode nextIf, ArrayList <StatementNode> statements) {
		this.condition = condition;
		this.statements = statements;
		this.nextIf = nextIf;
	}

	public ArrayList <StatementNode> getStatements() {
		return statements;
	}
	
	/**
	 * An accessor that allows the condition variable to be called in
	 * another file
	 * @return condition
	 */
	public BooleanCompareNode getCondition() {
		return this.condition;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		if (nextIf != null) {
			return "\nels"+nextIf;
		}
		return "\nIf Statement-> (" + condition + ") then, "+statements;
	}
}
