package edu.brandeis.cs.develops.eptosql.translator;

import java.util.Set;

import com.google.common.collect.Sets;

public class Selection extends Relation{
	private Relation leftChild;
	private String predicate;
	public Selection(Relation leftChild, String predicate) {
		this.setChild(leftChild);
		this.setPredicate(predicate);
	}
	
	public Relation getChild() {
		return leftChild;
	}
	
	public void setChild(Relation leftChild) {
		this.leftChild = leftChild;
	}
	
	public String getPredicate() {
		return predicate;
	}
	
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	
	public Boolean hasAttributes(String predicate) {
		return this.predicate.equals(predicate);
	}
	
	public String toString() {
		return "Select " + this.getPredicate() + " from " + this.getChild().toString();
	}

	@Override
	public Set<Relation> getChildren() {
		return Sets.newHashSet(new Relation[] {leftChild});
	}
}
