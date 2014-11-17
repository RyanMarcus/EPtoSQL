package edu.brandeis.cs.develops.eptosql.translator;


import java.util.Set;

import com.google.common.collect.Sets;


/**
 * Describes a Join query - subtree of Relation
 * @author Rachel Leeman-Munk
 * @since 11/17/2014
 */

public class Join extends Relation {
	private Relation leftChild; /**outer relation being joined*/
	private Relation rightChild; /**inner relation being joined*/
	private String predicate; /**predicate the relations are joined by*/
	private JoinType joinType; /**type of join*/
	
	/**
	 * @param leftChild outer relation
	 * @param rightChild inner relation
	 * @param predicate join predicate
	 * @param joinType join Type
	 */
	public Join(Relation leftChild, Relation rightChild, String predicate, String joinType) {
		this.setLeftChild(leftChild);
		this.setRightChild(rightChild);
		this.setPredicate(predicate);
		this.setJoinType(joinType);
	}

	/**
	 * Get left child of join
	 * @return left child of join
	 */
	public Relation getLeftChild() {
		return leftChild;
	}

	/**
	 * Set left child of join
	 * @param leftChild Relation to become left child
	 */
	public void setLeftChild(Relation leftChild) {
		this.leftChild = leftChild;
	}

	/**
	 * Get right child of join
	 * @return right child of join
	 */
	public Relation getRightChild() {
		return rightChild;
	}

	/**
	 * Set right child of join
	 * @param rightChild Relation to become right child
	 */
	public void setRightChild(Relation rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * Get join predicate
	 * @return join predicate
	 */
	public String getPredicate() {
		return predicate;
	}

	/**
	 * Set join predicate
	 * @param predicate join predicate
	 */
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	/**
	 * Get join type
	 * @return join type
	 */
	public JoinType getJoinType() {
		return joinType;
	}

	/**
	 * Set join type
	 * @param joinType type to become join type
	 */
	public void setJoinType(String joinType) {
		if(joinType.equals("PMJOIN")) {
			this.joinType = JoinType.PMJOIN;
		} else if(joinType.equals("PNLJOIN")) {
			this.joinType = JoinType.PNLJOIN;
		} else if(joinType.equals("PHJOIN")) {
			this.joinType = JoinType.PHJOIN;
		}
	}
	
	/**
	 * Check if Join has the parameter attributes
	 * @param joinType join type to be compared
	 * @param predicate predicate to be compared
	 * @return True if Join has matching joinType and predicate 
	 */
	public Boolean hasAttributes(JoinType joinType, String predicate) {
		return this.joinType==joinType && this.predicate.equals(predicate);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.getLeftChild().toString() + " " + this.getJoinType() + " " + this.getRightChild().toString() + " on " + this.getPredicate();
	}

	@Override
	public Set<Relation> getChildren() {
		return Sets.newHashSet(new Relation[] { this.getLeftChild(), this.getRightChild() });
	}
}
