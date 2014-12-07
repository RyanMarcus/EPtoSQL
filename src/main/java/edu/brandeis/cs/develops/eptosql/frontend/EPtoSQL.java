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


package edu.brandeis.cs.develops.eptosql.frontend;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import edu.brandeis.cs.develops.eptosql.code_generator.SQLGenerator;
import edu.brandeis.cs.develops.eptosql.ir_generator.IRGenerationException;
import edu.brandeis.cs.develops.eptosql.ir_generator.IRGenerator;
import edu.brandeis.cs.develops.eptosql.parser.Parser;
import edu.brandeis.cs.develops.eptosql.parser.parser.ParserException;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
import edu.brandeis.cs.develops.eptosql.semantic_analysis.SemanticAnalyzer;
import edu.brandeis.cs.develops.eptosql.semantic_analysis.SemanticAnnotation;
import edu.brandeis.cs.develops.eptosql.translator.ASTTranslator;
import edu.brandeis.cs.develops.eptosql.translator.Relation;

/**
 * 
 * This class contains two methods that could be used to access the compiler. The compile() method
 * is the preferred method of accessing the compiler, and can work asynchronously. The static method
 * syncCompile() provides a synchronous way of accessing the compiler that may be simpler.
 * 
 * @author Ryan Marcus < ryan @ rmarcus.info >
 *
 */
public class EPtoSQL {
	/**
	 * The main API frontend to the compiler. Give an input stream with a single physical
	 * execution plan terminated by a new line. Also give an output stream where the SQL
	 * will be written to. The write will be synchronous, meaning that the output stream will be
	 * fully readable when the method returns. This may change in the future.
	 * 
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
	 * @return true if SQL was written, false if an error was thrown
	 */
	public boolean compile(CodeGenerationOption cgo, IROption iro, InputStream is, OutputStream out) {		
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
			
			SemanticAnalyzer sa = new SemanticAnalyzer();
			for (Relation subR : toGenerate) {
				List<SemanticAnnotation> errors = sa.analyze(subR);
				if (!errors.isEmpty()) {
					for (SemanticAnnotation semann : errors) {
						System.err.println(semann.message);
					}
					return false;
				}
			}
		
			for (Relation subr : toGenerate) {
				if (cgo == CodeGenerationOption.NESTED) {
					os.println(SQLGenerator.createNestedSQL(subr));
				} else if (cgo == CodeGenerationOption.UNNESTED) {
					os.println(SQLGenerator.createUnnestedSQL(subr));
				}
			}
			
			os.flush();
			os.close();
			return true;
			
		} catch (ParserException e) {
			System.err.println("Parser exception: " + e.getMessage());
			return false;
		} catch (IRGenerationException e) {
			System.err.println("IR generation exception: " + e.getMessage());
			return false;
		}
	}
	
	public static String syncCompile(CodeGenerationOption cgo, IROption iro, String executionPlan) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		EPtoSQL compiler = new EPtoSQL();
		
		ByteArrayInputStream in = new ByteArrayInputStream(executionPlan.getBytes());

		PipedInputStream resultStream = new PipedInputStream();
		PipedOutputStream writeTo = new PipedOutputStream(resultStream);
		
		if (!compiler.compile(cgo, iro, in, writeTo)) {
			// error!
			return "";
		}
		
		
		BufferedReader sc = new BufferedReader(new InputStreamReader(resultStream));
		String line;
		while ((line = sc.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		
		
		writeTo.close();
		return sb.toString();
		
	}
	
	/**
	 * Some example code showing how to use this class
	 * @param args ignored
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		/* -------------------------------------------
		 * Example 1: The async API
		 * -------------------------------------------
		 */
		EPtoSQL compiler = new EPtoSQL();
		String query = "PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S))";
		
		ByteArrayInputStream in = new ByteArrayInputStream(query.getBytes());

		PipedInputStream resultStream = new PipedInputStream();
		PipedOutputStream writeTo = new PipedOutputStream(resultStream);
		
		compiler.compile(CodeGenerationOption.NESTED, IROption.ENABLE, in, writeTo);
		
		
		BufferedReader sc = new BufferedReader(new InputStreamReader(resultStream));
		String line;
		while ((line = sc.readLine()) != null) {
			System.out.println(line);
		}
		
		/* -------------------------------------------
		 * Example 2: The sync API
		 * -------------------------------------------
		 */
		
		String SQL = EPtoSQL.syncCompile(CodeGenerationOption.NESTED, IROption.ENABLE, query);
		System.out.println(SQL);
		
		
		
	}
	
}
