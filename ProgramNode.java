package Parser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * This node represents the program
 * @author Matthew Welsh
 */
public class ProgramNode extends Node {
	
	/** 
	 * The Hashmap - used to locate function nodes
	 */
	HashMap <String, FunctionNode> program = new HashMap<String, FunctionNode>();
	
	/**
	 * A constructor which populates the program node
	 * @param function
	 */
	public ProgramNode(ArrayList <FunctionNode> function) {
		for (int i=0; i < function.size(); i++) {
			program.put(function.get(i).getName(), function.get(i));
		}
	}
	
	/**
	 * A method used to locate a specific function in the program
	 * @param name
	 * @return
	 */
	public Collection<FunctionNode> getFunctions() {
		
		return program.values();
	}

	/**
	 * Overrides the built in toString() method and outputs a specific string 
	 */
	@Override
	public String toString() {
		return ""+program;
	}
}
