package edu.brandeis.cs.develops.eptosql.translator;


import java.util.Set;

/**
 * Relation - parent class of Join, Selection, and Table and base of Relation trees
 * @author Rachel Leeman-Munk
 * @since 11/17/2014
 */
public abstract class Relation {
	public abstract Set<Relation> getChildren();
}
