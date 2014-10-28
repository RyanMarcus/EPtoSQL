package edu.brandeis.cs.develops.eptosql.parser;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import edu.brandeis.cs.develops.eptosql.parser.lexer.LexerTest;
import edu.brandeis.cs.develops.eptosql.parser.parser.ShiftReduceParserTest;

@RunWith(Suite.class)
@SuiteClasses({ ShiftReduceParserTest.class, LexerTest.class, ParserTest.class })
public class AllParserTests {

}
