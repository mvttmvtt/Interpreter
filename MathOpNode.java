package Parser;

/**
 * This node class represents math operation
 * @author Matthew Welsh
 */
public class MathOpNode extends Node {

	/**
	 * Enum for math operations
	 */
	public enum operations {
		ADD, SUBTRACT, MULTIPLY, DIVIDE,
		MODULO
	}
	
	/**
	 * The left and right sub nodes
	 */
	private Node Left, Right;
	
	/**
	 * The operator variable
	 */
	private operations op;
	
	/**
	 * A constructor that initializes the math op nodes
	 * @param Left - left node
	 * @param Right - right node
	 * @param op - operator 
	 */
	public MathOpNode(Node Left, Node Right, operations op) {
		this.Left = Left;
		this.Right = Right;
		this.op = op;
	}
	
	/**
	 * Initializes the left node
	 * @param Left 
	 */
	public void setLeft(Node Left) {
		this.Left = Left;
	}
	
	/**
	 * An accessor that allows the left node
	 * to be called in another file
	 * @return Left node
	 */
	public Node getLeft() {
		return this.Left;
	}
	
	/**
	 * Initializes the right node
	 * @param Right
	 */
	public void setRight(Node Right) {
		this.Right = Right;
	}
	
	/**
	 * An accessor that allows the left node
	 * to be called in another file
	 * @return right node
	 */
	public Node getRight() {
		return this.Right;
	}

	/**
	 * An accessor that allows the operator
	 * to be called in another file
	 * @return operator
	 */
	public operations getOp() {
		return this.op;
	}
	
	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		return "MathOp: ("+this.op+": Left->"+this.Left+"  Right->"+this.Right+")\n";
	}

}
