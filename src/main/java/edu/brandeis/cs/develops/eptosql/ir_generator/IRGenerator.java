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


package edu.brandeis.cs.develops.eptosql.ir_generator;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;

import edu.brandeis.cs.develops.eptosql.translator.Join;
import edu.brandeis.cs.develops.eptosql.translator.Relation;
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
	
}
