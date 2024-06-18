package Lexer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Interpreter.Interpreter;
import Interpreter.SemanticAnalysis;
import Parser.Parser;
import Parser.ProgramNode;
import Lexer.SyntaxErrorException;
import Lexer.Token;

/**
 * This file reads all of the lines of the file denoted by the filename.
 * @author Matthew Welsh
 */
public class Shank extends Lexer {
	
	/**
	 * Main method.
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		/*
		 * Prints an error message and exits if there is none or more than one arguments.
		 */
		if (args.length > 1 || args.length == 1) {
			throw new SyntaxErrorException("Error!: Insufficient number of Arguments.");
		}
		
		/*
		 * Reads every line of the file
		 */
		Path file = Paths.get("C:\\Users\\matth\\workspace\\Lexer\\src\\Input.txt");
		List <String> lines = Files.readAllLines(file, StandardCharsets.ISO_8859_1);
		
		/*
		 * An array list of tokens
		 */
		ArrayList<Token> tokenlist = new ArrayList<Token>();
		
		/*
		 * Reads through each line and calls the Lex method
		 */
		int i = 1;
		for (String line: lines) {
			System.out.println("Line: " + i);
			tokenlist.addAll((Lex(line)));
			i++;
        }
		
		System.out.println("Now Parsing...");
		System.out.println("===========================================================\n");
		
		/*
		 * Parses the token list
		 */
		Parser par = new Parser(tokenlist);
		par.Parse();
		
		System.out.println("\nNow Interpreting...");
		System.out.println("===========================================================\n");
		
		/*
		 * Interprets the list of functions
		 */
		new Interpreter(par.functions);
		
		/**
		 * Performs semantic analysis on the program
		 */
		ProgramNode program = par.getProgram();
		SemanticAnalysis.CheckAssignments(program);
    }
}
