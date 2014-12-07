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


package edu.brandeis.cs.develops.eptosql.semantic_analysis;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Sets;

import edu.brandeis.cs.develops.eptosql.translator.Join;
import edu.brandeis.cs.develops.eptosql.translator.JoinType;
import edu.brandeis.cs.develops.eptosql.translator.Relation;
import edu.brandeis.cs.develops.eptosql.translator.SampleTrees;
import edu.brandeis.cs.develops.eptosql.translator.Selection;

/**
 * A semantic analysis class that analyzes the output of the AST translator
 * and checks for various issues (join over selection, selection over join)
 * that are known to occur with SQL Server.
 * 
 * @author Ryan Marcus < ryan @ rmarcus.info >
 *
 */
public class SemanticAnalyzer {
	public List<SemanticAnnotation> analyze(Relation r) {
		List<SemanticAnnotation> toR = new LinkedList<SemanticAnnotation>();

		toR.addAll(joinSelectionCheck(r));
		toR.addAll(selectionJoinCheck(r));

		return toR;

	}

	private List<SemanticAnnotation> joinSelectionCheck(Relation r) {
		List<SemanticAnnotation> toR = new LinkedList<SemanticAnnotation>();
		Deque<Relation> stack = new LinkedList<Relation>();

		Join beneathJoin = null;
		stack.push(r);
		while (!stack.isEmpty()) {
			Relation curr = stack.pop();
			for (Relation child : curr.getChildren()) {
				stack.push(child);
			}

			if (curr instanceof Selection && beneathJoin != null) {
				if (shareAttributes(((Selection) curr).getPredicate(),
						beneathJoin.getPredicate())) {
					toR.add(new Error(
							"You have a selection beneath a non-loop join -- "
									+ "the SQL Server compiler won't accept that. Join: "
									+ beneathJoin.toString() + " Selection: "
									+ curr.toString()));
				}

			}

			if (curr instanceof Join
					&& ((Join) curr).getJoinType() != JoinType.PNLJOIN) {
				beneathJoin = (Join) curr;
			}

		}

		return toR;
	}

	private List<SemanticAnnotation> selectionJoinCheck(Relation r) {
		List<SemanticAnnotation> toR = new LinkedList<SemanticAnnotation>();
		Deque<Relation> stack = new LinkedList<Relation>();

		Selection beneathSelect = null;
		stack.push(r);
		while (!stack.isEmpty()) {
			Relation curr = stack.pop();
			for (Relation child : curr.getChildren()) {
				stack.push(child);
			}

			if (curr instanceof Join && beneathSelect != null) {
				if (((Join) curr).getJoinType() != JoinType.PNLJOIN) {
					if (shareAttributes(((Join) curr).getPredicate(),
							beneathSelect.getPredicate())) {
						toR.add(new Error(
								"You have a non-loop join beneath a select -- "
										+ "the SQL Server compiler won't accept that. Join: "
										+ beneathSelect.toString()
										+ " Selection: " + curr.toString()));
					}
				}

			}

			if (curr instanceof Selection)
				beneathSelect = (Selection) curr;

		}

		return toR;

	}

	private boolean shareAttributes(String predicate1, String predicate2) {
		Set<String> columns1 = new HashSet<String>();
		Set<String> columns2 = new HashSet<String>();
		
		Pattern p = Pattern.compile("[A-Za-z]+[0-9]*");
		
		Matcher m = p.matcher(predicate1);
		while (m.find()) {
			columns1.add(m.group());
		}
		
		m = p.matcher(predicate2);
		while (m.find()) {
			columns2.add(m.group());
		}

		return Sets.intersection(columns1, columns2).size() != 0;
	}
	
	public static void main(String[] args) {
		SemanticAnalyzer sa = new SemanticAnalyzer();

		List<SemanticAnnotation> sem = sa.analyze(SampleTrees.TwoJoinSelectionExpression());
		
		for (SemanticAnnotation SemAn : sem) {
			System.out.println(SemAn.message);
		}
	}
}
