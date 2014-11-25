package edu.brandeis.cs.develops.eptosql.frontend;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
	/**
	 * The main API frontend to the compiler. Give an input stream with a single physical
	 * execution plan terminated by a new line. Also give an output stream where the SQL
	 * will be written to. The write will be synchronous, meaning that the output stream will be
	 * fully readable when the method returns. This may change in the future.
	 * 
	 * You should assume that the output stream is not ready to be read until it is closed.
	 * 
	 * The CodeGenerationOption allows you to pick between nested (queries where each physical
	 * operator is explicitly given in the SQL statement) and unnested (where selections are pulled
	 * up into the WHERE clause and joins are pulled into the FROM clause).
	 * 
	 * The IROption allows you to specify if physical plans should be broken down into 
	 * subqueries that insert into temporary tables. This option will avoid "selection over join"
	 * and "join over selection" errors. 
	 * 
	 * @param cgo the code generation option
	 * @param iro the IR generation option
	 * @param is the input stream to read from
	 * @param out the output stream the resulting SQL will be written to
	 */
	public void compile(CodeGenerationOption cgo, IROption iro, InputStream is, OutputStream out) {		
		try {
			Parser p = new Parser();
			EP ep = (EP) p.parseStream(is);
			
			PrintWriter os = new PrintWriter(out);
			
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
					os.println(SQLGenerator.createNestedSQL(subr));
				} else if (cgo == CodeGenerationOption.UNNESTED) {
					os.println(SQLGenerator.createUnnestedSQL(subr));
				}
			}
			
			os.close();
			
		} catch (ParserException e) {
			System.err.println("Parser exception: " + e.getMessage());
			System.exit(-1);
		} catch (IRGenerationException e) {
			System.err.println("IR generation exception: " + e.getMessage());
			System.exit(-1);
		}
	}
	
}
