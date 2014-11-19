package edu.brandeis.cs.develops.eptosql.frontend;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import edu.brandeis.cs.develops.eptosql.code_generator.SQLGenerator;
import edu.brandeis.cs.develops.eptosql.ir_generator.IRGenerationException;
import edu.brandeis.cs.develops.eptosql.ir_generator.IRGenerator;
import edu.brandeis.cs.develops.eptosql.parser.Parser;
import edu.brandeis.cs.develops.eptosql.parser.parser.ParserException;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
import edu.brandeis.cs.develops.eptosql.translator.ASTTranslator;
import edu.brandeis.cs.develops.eptosql.translator.Relation;

public class EPtoSQL {
	public void compile(CodeGenerationOption cgo, IROption iro, InputStream is) {		
		try {
			Parser p = new Parser();
			EP ep = (EP) p.parseStream(is);
			
			ASTTranslator astt = new ASTTranslator();
			Relation r = astt.parse(ep);
			
			List<Relation> toGenerate = null;
			
			if (iro == IROption.DISABLE) {
				toGenerate = Arrays.asList(r);
			} else if (iro == IROption.ENABLE) {
				IRGenerator ir = new IRGenerator();
				toGenerate = ir.decompose(r);
			}
			
		
			for (Relation subr : toGenerate) {
				if (cgo == CodeGenerationOption.NESTED) {
					System.out.println(SQLGenerator.createNestedSQL(subr));
				} else if (cgo == CodeGenerationOption.UNNESTED) {
					System.out.println(SQLGenerator.createUnnestedSQL(subr));
				}
			}
			
		} catch (ParserException e) {
			System.err.println("Parser exception: " + e.getMessage());
			System.exit(-1);
		} catch (IRGenerationException e) {
			System.err.println("IR generation exception: " + e.getMessage());
			System.exit(-1);
		}
	}
	
}
