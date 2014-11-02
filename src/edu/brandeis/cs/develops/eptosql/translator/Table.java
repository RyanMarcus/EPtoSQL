package edu.brandeis.cs.develops.eptosql.translator;

public class Table implements Relation {
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
}
