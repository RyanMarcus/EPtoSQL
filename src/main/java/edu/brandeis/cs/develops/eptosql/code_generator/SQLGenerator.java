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


package edu.brandeis.cs.develops.eptosql.code_generator;

import edu.brandeis.cs.develops.eptosql.translator.Join;
import edu.brandeis.cs.develops.eptosql.translator.JoinType;
import edu.brandeis.cs.develops.eptosql.translator.Relation;
import edu.brandeis.cs.develops.eptosql.translator.Selection;
import edu.brandeis.cs.develops.eptosql.translator.Table;
/**
 * 
 * @author Brionne Godby
 * @since 11/17/2014
 */
public class SQLGenerator {

	
	/**
	 * Builds a collapsed query from the relation tree (use this one)
	 * @param fullRelation the relation tree to build SQL for
	 * @return the SQL
	 */
    public static String createUnnestedSQL(Relation fullRelation) {
        SQLDto full = unnestedSQL(fullRelation);
        return String.format("%s OPTION(FORCE ORDER);", full.generateSQL());
    }
    
    /**
     * Builds an fully-expanded query from the relation tree
     * @param fullRelation the relation tree to build SQL for
     * @return the SQL
     */
    public static String createNestedSQL(Relation fullRelation) {
        String sql = String.format("%s OPTION(FORCE ORDER);", nestedSQL(fullRelation));
        return sql;
    }

    private static SQLDto unnestedSQL(Relation relation) {
        if (relation instanceof Join) {
            return unnestedSQL((Join) relation);
        } else if (relation instanceof Table) {
            return unnestedSQL((Table) relation);
        } else if (relation instanceof Selection) {
            return unnestedSQL((Selection) relation);
        } else {
            throw new UnsupportedOperationException("Unrecognized type");
        }
    }

    private static SQLDto unnestedSQL(Join relation) {
        SQLDto leftDto = unnestedSQL(relation.getLeftChild());
        SQLDto rightDto = unnestedSQL(relation.getRightChild());
        if (relation.getLeftChild() instanceof Table) {
            return easyJoin(relation, leftDto, rightDto);
        } else if (relation.getRightChild() instanceof Table) {
            return easyJoin(relation, rightDto, leftDto);
        } else {
            if (relation.getInto() != null)
            	leftDto.setInto(relation.getInto());
        	
            leftDto.appendJoin(" ");
            leftDto.appendJoin(evaluateJoinType(relation.getJoinType()));
            leftDto.appendJoin(" (");
            leftDto.appendJoin(rightDto.generateSQL());
            leftDto.appendJoin(") ");
            leftDto.appendJoin(leftDto.getNextAlias());
            leftDto.appendJoin(" ON ");
            leftDto.appendJoin(relation.getPredicate());
            return leftDto;
        }
        
        
    }

    private static SQLDto easyJoin(Join relation, SQLDto simpleDto, SQLDto complexDto) {
        complexDto.appendJoin(" ");
        complexDto.appendJoin(evaluateJoinType(relation.getJoinType()));
        complexDto.appendJoin(" ");
        complexDto.appendJoin(simpleDto.getFromClause().toString());
        complexDto.appendJoin(" ON ");
        if (simpleDto.getWhereClause().length() > 0) {
            complexDto.appendJoin(simpleDto.getWhereClause().toString());
            complexDto.appendJoin(" AND ");
        }
        complexDto.appendJoin(relation.getPredicate());
        
        if (relation.getInto() != null)
        	complexDto.setInto(relation.getInto());
        
        return complexDto;
    }

    private static SQLDto unnestedSQL(Selection relation) {
        SQLDto childDto = unnestedSQL(relation.getChild());
        childDto.appendWhere(relation.getPredicate());
        
        if (relation.getInto() != null)
        	childDto.setInto(relation.getInto());
        
        return childDto;
    }

    private static SQLDto unnestedSQL(Table relation) {
        SQLDto tableDto = new SQLDto();
        tableDto.appendFrom(relation.getName());
        return tableDto;
    }

    

    private static String nestedSQL(Relation relation) {
        if (relation instanceof Join) {
            return nestedSQL((Join) relation);
        } else if (relation instanceof Table) {
            return nestedSQL((Table) relation);
        } else if (relation instanceof Selection) {
            return nestedSQL((Selection) relation);
        } else {
            throw new UnsupportedOperationException("Unrecognized type");
        }
    }

    private static String nestedSQL(Join relation) {
    	String intoClause = "";
    	
    	if (relation.getInto() != null)
    		intoClause = "INTO " + relation.getInto();
    	
        if(relation.getLeftChild() instanceof Table && relation.getRightChild() instanceof Table){
            return String.format("SELECT * %s FROM %s  %s %s table_right ON (%s)", intoClause, ((Table) relation.getLeftChild()).getName(), evaluateJoinType(relation.getJoinType()), ((Table) relation.getRightChild()).getName(), relation.getPredicate());
        }else{
            return String.format("SELECT * %s FROM (%s) table_left %s (%s) table_right ON (%s)", intoClause, nestedSQL(relation.getLeftChild()), evaluateJoinType(relation.getJoinType()), nestedSQL(relation.getRightChild()), relation.getPredicate());
        }
    }

    private static String nestedSQL(Selection relation) {
    	String intoClause = "";
    	
    	if (relation.getInto() != null)
    		intoClause = "INTO " + relation.getInto();
    	
    	
        if(relation.getChild() instanceof Table){
            return String.format("SELECT * %s FROM %s table_inner WHERE %s", intoClause, ((Table) relation.getChild()).getName(), relation.getPredicate());
        }else{
            return String.format("SELECT * %s FROM (%s) table_inner WHERE %s", intoClause, nestedSQL(relation.getChild()), relation.getPredicate());
        }
    }

    private static String nestedSQL(Table relation) {
        String result = String.format("SELECT * FROM %s", relation.getName());
        return result;
    }

    public static String evaluateJoinType(JoinType join) {
        switch (join) {
            case PHJOIN:
                return "INNER HASH JOIN";
            case PNLJOIN:
                return "INNER LOOP JOIN";
            case PMJOIN:
            default:
                return "INNER MERGE JOIN";
        }
    }
}
