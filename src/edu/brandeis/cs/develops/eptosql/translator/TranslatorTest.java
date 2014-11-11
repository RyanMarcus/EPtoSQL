package edu.brandeis.cs.develops.eptosql.translator;

import edu.brandeis.cs.develops.eptosql.parser.*;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;

public class TranslatorTest {
	private Parser p;
	private ASTTranslator aslp;
	public TranslatorTest() {
		p = new Parser();
		aslp = new ASTTranslator();
	}
	public static void main(String[] args) {
		TranslatorTest t = new TranslatorTest();
		System.out.println(t.test().toString());
		System.out.println(t.test2().toString());
		System.out.println(t.test3().toString());
		System.out.println(t.test4().toString());
	}
	public Relation test() {
		EP ep = (EP) p.parseString("PTABLE(PS)");
		return aslp.parse(ep);
	}
	
	public Relation test2() {
		EP ep = (EP) p.parseString("PSELECT(n_name = 'ASIA', PTABLE(N))");
		return aslp.parse(ep);
	}
	
	public Relation test3() {
		EP ep = (EP) p.parseString("PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S))");
		return aslp.parse(ep);
	}
	
	public Relation test4() {
		EP ep = (EP) p.parseString("PNLJOIN(s_nationkey = n_nationkey, PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S)), PSELECT(n_name = 'ASIA', PTABLE(N)))");
		return aslp.parse(ep);
	}
}
