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
