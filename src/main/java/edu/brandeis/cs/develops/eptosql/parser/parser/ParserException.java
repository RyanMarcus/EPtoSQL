package edu.brandeis.cs.develops.eptosql.parser.parser;

/**
 * An exception that is thrown when a lexing or parsing error is encountered.
 *
 * The error message given will provide more information about why the lexing or
 * parsing failed. 
 * 
 * @author Ryan Marcus < ryan @ rmarcus.info >
 *
 */
public class ParserException extends Exception {

	private static final long serialVersionUID = 1;

	public ParserException(String string) {
		super(string);
	}

}
