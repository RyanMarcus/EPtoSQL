package edu.brandeis.cs.develops.eptosql.semantic_analysis;

/**
 * A class representing an error (as opposed to a warning) found
 * by the semantic analyzer. The message will contain more information
 * about the error.
 * 
 * @author Ryan Marcus < ryan @ rmarcus.info >
 *
 */
public class Error extends SemanticAnnotation {

	public Error(String s) {
		super(s);
	}

}
