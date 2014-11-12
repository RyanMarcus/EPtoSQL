package edu.brandeis.cs.develops.eptosql;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.brandeis.cs.develops.eptosql.parser.AllParserTests;
import edu.brandeis.cs.develops.eptosql.translator.TranslatorTest;

@RunWith(Suite.class)
@SuiteClasses({ AllParserTests.class,  TranslatorTest.class})
public class EPToSQLTests {

}
