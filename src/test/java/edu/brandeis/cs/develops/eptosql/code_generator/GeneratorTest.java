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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.brandeis.cs.develops.eptosql.parser.Parser;
import edu.brandeis.cs.develops.eptosql.parser.parser.ParserException;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
import edu.brandeis.cs.develops.eptosql.translator.ASTTranslator;
import edu.brandeis.cs.develops.eptosql.translator.Relation;
import edu.brandeis.cs.develops.eptosql.translator.SampleTrees;

public class GeneratorTest {

    @Test
    public void test1() {
        Relation test = SampleTrees.EmployeeDepartmentExpression();
        String SQL = SQLGenerator.createUnnestedSQL(test);
        assertTrue(SQL.indexOf("departments") != -1);
        assertTrue(SQL.indexOf("SELECT") != -1);
        assertTrue(SQL.indexOf("JOIN") != -1);
        assertTrue(SQL.indexOf("HASH") != -1);
        assertTrue(SQL.indexOf("yee_employer_id=yer_employer_id") != -1);        
    }
    
    @Test
    public void test2() {
        Relation test = SampleTrees.EmployeeDepartmentExpression();
        String SQL = SQLGenerator.createNestedSQL(test);
        assertTrue(SQL.indexOf("departments") != -1);
        assertTrue(SQL.indexOf("SELECT") != -1);
        assertTrue(SQL.indexOf("JOIN") != -1);
        assertTrue(SQL.indexOf("HASH") != -1);
        assertTrue(SQL.indexOf("yee_employer_id=yer_employer_id") != -1); 
    }
    
    @Test
    public void test3() throws ParserException {
    	Parser p = new Parser();
    	EP ep = (EP) p.parseString("PSELECT(n_name = 'ASIA', PTABLE(N))");
		ASTTranslator astt = new ASTTranslator();
		Relation r = astt.parse(ep);
        String SQL = SQLGenerator.createUnnestedSQL(r);

        assertTrue(SQL.indexOf("SELECT") != -1);
        assertTrue(SQL.indexOf("ASIA") != -1);
    }
    
    @Test
    public void test4() throws ParserException {
    	Parser p = new Parser();
    	EP ep = (EP) p.parseString("PSELECT(n_name = 'ASIA', PTABLE(N))");
		ASTTranslator astt = new ASTTranslator();
		Relation r = astt.parse(ep);
        String SQL = SQLGenerator.createUnnestedSQL(r);

        assertTrue(SQL.indexOf("SELECT") != -1);
        assertTrue(SQL.indexOf("ASIA") != -1);
    }
    
    
    @Test
    public void test5() throws ParserException {
    	Parser p = new Parser();
    	EP ep = (EP) p.parseString("PNLJOIN(s_nationkey = n_nationkey, PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S)), PSELECT(n_name = 'ASIA', PTABLE(N)))");
		ASTTranslator astt = new ASTTranslator();
		Relation r = astt.parse(ep);
        String SQL = SQLGenerator.createUnnestedSQL(r);

        assertTrue(SQL.indexOf("SELECT") != -1);
        assertTrue(SQL.indexOf("ASIA") != -1);
        assertTrue(SQL.indexOf("LOOP") != -1);
        assertTrue(SQL.indexOf("MERGE") != -1);
        assertTrue(SQL.indexOf("s_nationkey") != -1);
        assertTrue(SQL.indexOf("ps_suppkey") != -1);




    }
}
