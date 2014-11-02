package edu.brandeis.cs.develops.eptosql.translator;

public class Join implements Relation {
	private Table leftChild;
	private Table rightChild;
	private String predicate;
	private String joinType;
	
	public Join(Table leftChild, Table rightChild, String predicate, String joinType) {
		this.setLeftChild(leftChild);
		this.setRightChild(rightChild);
		this.setPredicate(predicate);
		this.setJoinType(joinType);
	}

	public Table getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Table leftChild) {
		this.leftChild = leftChild;
	}

	public Table getRightChild() {
		return rightChild;
	}

	public void setRightChild(Table rightChild) {
		this.rightChild = rightChild;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
}
