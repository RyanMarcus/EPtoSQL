/*
Copyright 2014 Brionne Godby, Rachel Leeman-Munk, Ryan Marcus

This file is part of EPtoSQL.

EPtoSQL is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

EPtoSQL is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with EPtoSQL.  If not, see <http://www.gnu.org/licenses/>.
*/


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
	private String into;
	
	/**
	 * @param leftChild outer relation
	 * @param rightChild inner relation
	 * @param predicate join predicate
	 * @param joinType join type, as a string from the DevelOPs code
	 */
	public Join(Relation leftChild, Relation rightChild, String predicate, String joinType) {
		this.setLeftChild(leftChild);
		this.setRightChild(rightChild);
		this.setPredicate(predicate);
		this.setJoinType(joinType);
	}
	
	/**
	 * @param leftChild outer relation
	 * @param rightChild inner relation
	 * @param predicate join predicate
	 * @param joinType join type
	 */
	public Join(Relation leftChild, Relation rightChild, String predicate, JoinType joinType) {
		this.setLeftChild(leftChild);
		this.setRightChild(rightChild);
		this.setPredicate(predicate);
		this.joinType = joinType;
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

	public String getInto() {
		return into;
	}

	public void setInto(String into) {
		this.into = into;
	}
}
