package edu.brandeis.cs.develops.eptosql.translator;

/**
 * @author Rachel Leeman-Munk
 *
 */
public class Selection extends Relation{
	private Relation leftChild;
	private String predicate;
	public Selection(Relation leftChild, String predicate) {
		this.setChild(leftChild);
		this.setPredicate(predicate);
	}
	
	/**
	 * @return left child of selection
	 */
	public Relation getChild() {
		return leftChild;
	}
	
	/**
	 * @param leftChild selection child
	 */
	public void setChild(Relation leftChild) {
		this.leftChild = leftChild;
	}
	
	/**
	 * @return selection predicate
	 */
	public String getPredicate() {
		return predicate;
	}
	
	/**
	 * @param predicate selection predicate
	 */
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	
	/**
	 * @param predicate the selection predicate being compared
	 * @return boolean - this.predicate == parameter predicate
	 */
	public Boolean hasAttributes(String predicate) {
		return this.predicate.equals(predicate);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Select " + this.getPredicate() + " from " + this.getChild().toString();
	}
}
