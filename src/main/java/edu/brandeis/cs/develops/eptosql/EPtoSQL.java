package edu.brandeis.cs.develops.eptosql;

import java.util.Scanner;

import edu.brandeis.cs.develops.eptosql.code_generator.SQLGenerator;
import edu.brandeis.cs.develops.eptosql.parser.Parser;
import edu.brandeis.cs.develops.eptosql.parser.parser.ParserException;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.ASTNode;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
import edu.brandeis.cs.develops.eptosql.translator.ASTTranslator;
import edu.brandeis.cs.develops.eptosql.translator.Relation;

public class EPtoSQL {

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String plan = sc.nextLine().trim();		
		
		try {
			Parser p = new Parser();
			ASTNode n = p.parseString(plan);
			ASTTranslator t = new ASTTranslator();
			Relation r = t.parse((EP) n);
			System.out.println(SQLGenerator.createNestedSQL(r));
			
		} catch (ParserException e) {
			System.err.println("Parser exception: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} finally {
			sc.close();
		}
		
		
		
	}

}
