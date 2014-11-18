package edu.brandeis.cs.develops.eptosql.ir_generator;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;

import edu.brandeis.cs.develops.eptosql.code_generator.SQLGenerator;
import edu.brandeis.cs.develops.eptosql.translator.Join;
import edu.brandeis.cs.develops.eptosql.translator.Relation;
import edu.brandeis.cs.develops.eptosql.translator.SampleTrees;
import edu.brandeis.cs.develops.eptosql.translator.Selection;
import edu.brandeis.cs.develops.eptosql.translator.Table;

/**
 * Generates "IR" for SQL Server. It isn't really IR, since
 * the representation is just subtrees of teh AST, but it does
 * generate a series of trees that, when compiled, produce the
 * correct answer.
 * 
 * @author "Ryan Marcus <ryan@rmarcus.info>"
 *
 */
public class IRGenerator {

	private AtomicInteger tempCounter;
	private List<Relation> emit;
	
	public synchronized List<Relation> decompose(Relation r) throws IRGenerationException {
		if (r instanceof Table) {
			return Lists.asList(r, new Relation[] {});
		}
		
		
		tempCounter = new AtomicInteger(0);
		emit = new LinkedList<Relation>();
		decomposeSubtree(r);
		
		
		
		List<Relation> toR = emit;
		emit = null;
		tempCounter = null;
		return toR;
	}

	private String decomposeSubtree(Relation r) throws IRGenerationException {
		if (r instanceof Table) {
			return ((Table) r).getName();
		} else if (r instanceof Selection) {
			Selection sel = (Selection) r;
			Selection s = new Selection(new Table(decomposeSubtree(sel.getChild())),
					                    sel.getPredicate());
			
			String label = "#temp" + tempCounter.incrementAndGet();
			s.setInto(label);
			emit.add(s);
			return label;
			
		} else if (r instanceof Join) {
			Join j = (Join) r;
			Join nj = new Join(new Table(decomposeSubtree(j.getLeftChild())), 
						       new Table(decomposeSubtree(j.getRightChild())),
						       j.getPredicate(), j.getJoinType());

			String label = "#temp" + tempCounter.incrementAndGet();
			nj.setInto(label);
			emit.add(nj);
			return label;
		}
		
		throw new IRGenerationException("Unhandled type in IR generation: " + r);
	
	}
	
	public static void main(String[] args) throws IRGenerationException {
		IRGenerator ir = new IRGenerator();
		
		List<Relation> subPlans = ir.decompose(SampleTrees.EmployeeDepartmentExpression());
		
		for (Relation r : subPlans) {
			System.out.println(SQLGenerator.createUnnestedSQL(r));
		}
		
	}
}
