package edu.brandeis.cs.develops.eptosql.translator;

import java.util.Set;

import com.google.common.collect.Sets;

public class Table extends Relation {
	private String name;
	
	public Table(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean hasAttributes(String name) {
		return this.name.equals(name);
	}
	
	public String toString() {
		return "Table" + this.name;
	}

	@Override
	public Set<Relation> getChildren() {
		return Sets.newHashSet();
	}
}
