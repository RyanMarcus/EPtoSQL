package edu.brandeis.cs.develops.eptosql.translator;

public class Join extends Relation {
	private Relation leftChild;
	private Relation rightChild;
	private String predicate;
	private JoinType joinType;
	
	public Join(Relation leftChild, Relation rightChild, String predicate, String joinType) {
		this.setLeftChild(leftChild);
		this.setRightChild(rightChild);
		this.setPredicate(predicate);
		this.setJoinType(joinType);
	}

	public Relation getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Relation leftChild) {
		this.leftChild = leftChild;
	}

	public Relation getRightChild() {
		return rightChild;
	}

	public void setRightChild(Relation rightChild) {
		this.rightChild = rightChild;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		if(joinType.equals("PMJOIN")) {
			this.joinType = JoinType.PMJOIN;
		} else if(joinType.equals("PNLJOIN")) {
			this.joinType = JoinType.PNLJOIN;
		} else if(joinType.equals("PHJOIN")) {
			this.joinType.equals("PHJOIN");
		}
	}
	
	public String toString() {
		return this.getLeftChild().toString() + " " + this.getJoinType() + " " + this.getRightChild().toString() + " on " + this.getPredicate();
	}
}
