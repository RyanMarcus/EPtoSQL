package edu.brandeis.cs.develops.eptosql.semantic_analysis;

/**
 * A class representing a warning (as opposed to a error) found
 * by the semantic analyzer. The message will contain more information
 * about the error.
 * 
 * @author Ryan Marcus < ryan @ rmarcus.info >
 *
 */
public class Warning extends SemanticAnnotation {

	public Warning(String s) {
		super(s);
	}

}
