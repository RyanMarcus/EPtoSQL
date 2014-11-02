package edu.brandeis.cs.develops.eptosql.translator;

public class Selection {
	private Table child;
	private String predicate;
	public Selection(Table child, String predicate) {
		this.setChild(child);
		this.setPredicate(predicate);
	}
	
	public Table getChild() {
		return child;
	}
	
	public void setChild(Table child) {
		this.child = child;
	}
	
	public String getPredicate() {
		return predicate;
	}
	
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
}
