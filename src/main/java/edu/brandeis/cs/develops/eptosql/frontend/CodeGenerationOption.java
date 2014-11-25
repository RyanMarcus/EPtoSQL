package edu.brandeis.cs.develops.eptosql.frontend;

/**
 * 
 * @author Ryan Marcus < ryan @ rmarcus.info >
 *
 * An enum containing options to specify the code generation mode. Currently,
 * this can be either "nested" (where each operator is contained in a query),
 * or "unnested", where predicates are pulled into the WHERE clause and tables
 * are pulled into the FROM clause. See the code_generator package for more information.
 *
 */
public enum CodeGenerationOption {
	NESTED, UNNESTED;
}
