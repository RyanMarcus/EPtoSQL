package edu.brandeis.cs.develops.eptosql.translator;

/**
 * @author rachel
 *
 */
/**
 * @author rachel
 *
 */
public class Join extends Relation {
	private Relation leftChild;
	private Relation rightChild;
	private String predicate;
	private JoinType joinType;
	
	/**
	 * @param leftChild
	 * @param rightChild
	 * @param predicate
	 * @param joinType
	 */
	public Join(Relation leftChild, Relation rightChild, String predicate, String joinType) {
		this.setLeftChild(leftChild);
		this.setRightChild(rightChild);
		this.setPredicate(predicate);
		this.setJoinType(joinType);
	}

	/**
	 * @return
	 */
	public Relation getLeftChild() {
		return leftChild;
	}

	/**
	 * @param leftChild
	 */
	public void setLeftChild(Relation leftChild) {
		this.leftChild = leftChild;
	}

	/**
	 * @return
	 */
	public Relation getRightChild() {
		return rightChild;
	}

	/**
	 * @param rightChild
	 */
	public void setRightChild(Relation rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * @return
	 */
	public String getPredicate() {
		return predicate;
	}

	/**
	 * @param predicate
	 */
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	/**
	 * @return
	 */
	public JoinType getJoinType() {
		return joinType;
	}

	/**
	 * @param joinType
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
	 * @param joinType
	 * @param predicate
	 * @return
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
}
