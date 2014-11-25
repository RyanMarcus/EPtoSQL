package edu.brandeis.cs.develops.eptosql.code_generator;

import edu.brandeis.cs.develops.eptosql.translator.Relation;
import edu.brandeis.cs.develops.eptosql.translator.SampleTrees;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

public class GeneratorTest {

    @Test
    public void dummy() {
        Relation test = SampleTrees.EmployeeDepartmentExpression();
        String SQL = SQLGenerator.createUnnestedSQL(test);
        //System.out.println(SQL);
        assertTrue("Dummy Test", true);
    }
}
