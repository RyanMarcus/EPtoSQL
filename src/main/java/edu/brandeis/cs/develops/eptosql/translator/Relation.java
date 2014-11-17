package edu.brandeis.cs.develops.eptosql.translator;

import java.util.Set;

public abstract class Relation {
	public abstract Set<Relation> getChildren();
	
}
