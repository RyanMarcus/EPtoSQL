package edu.brandeis.cs.develops.eptosql.semantic_analysis;

/**
 * The base class for semantic issues with a given non-verbose (translated) AST
 * @author Ryan Marcus < ryan @ rmarcus.info >
 *
 */
public class SemanticAnnotation {
	public String message;
	
	public SemanticAnnotation(String s) {
		message = s;
	}
}
