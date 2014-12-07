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
 * Describes a Selection query - subtree of Relation
 * @author Rachel Leeman-Munk
 * @since 11/17/2014
 */

public class Selection extends Relation{
	private Relation leftChild; /**Relation being queried*/
	private String predicate; /**Selection predicate*/
	private String into;
	/**
	 * 
	 * @param leftChild Relation
	 * @param predicate Selection predicate
	 */
	public Selection(Relation leftChild, String predicate) {
		this.setChild(leftChild);
		this.setPredicate(predicate);
	}
	
	/**
	 * @return relation being queried
	 */
	public Relation getChild() {
		return leftChild;
	}
	
	/**
	 * Set leftChild of Selection
	 * @param leftChild Relation to replace leftChild
	 */
	public void setChild(Relation leftChild) {
		this.leftChild = leftChild;
	}
	
	/**
	 * Get Selection predicate
	 * @return selection predicate
	 */
	public String getPredicate() {
		return predicate;
	}
	
	/**
	 * Set Selection predicate
	 * @param predicate predicate to replace Selection predicate
	 */
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	
	/**
	 * Check if referenced Selection has parameter attributes
	 * @param predicate the selection predicate being compared
	 * @return true if Selection predicate equals predicate
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

	@Override
	public Set<Relation> getChildren() {
		return Sets.newHashSet(new Relation[] {leftChild});
	}

	public String getInto() {
		return into;
	}

	public void setInto(String into) {
		this.into = into;
	}
}
