package edu.brandeis.cs.develops.eptosql;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import edu.brandeis.cs.develops.eptosql.code_generator.SQLGenerator;
import edu.brandeis.cs.develops.eptosql.frontend.CodeGenerationOption;
import edu.brandeis.cs.develops.eptosql.frontend.EPtoSQL;
import edu.brandeis.cs.develops.eptosql.frontend.IROption;
import edu.brandeis.cs.develops.eptosql.parser.Parser;
import edu.brandeis.cs.develops.eptosql.parser.parser.ParserException;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.ASTNode;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
import edu.brandeis.cs.develops.eptosql.translator.ASTTranslator;
import edu.brandeis.cs.develops.eptosql.translator.Relation;

public class Cli {
 private static final Logger log = Logger.getLogger(Cli.class.getName());
 private String[] args = null;
 private Options options = new Options();

 public Cli(String[] args) {

  this.args = args;

  options.addOption("h", "help", false, "show help.");
  options.addOption("f", "filename", true, "file to translate.");
  options.addOption("nested", "nested", false, "if included, generate nested code");
  options.addOption("ir_disable", "disable ir", false, "if included, disable ir");
 }

 public void parse() throws FileNotFoundException {
  CommandLineParser parser = new BasicParser();
  
  CodeGenerationOption cgo = CodeGenerationOption.UNNESTED;
  IROption iro = IROption.ENABLE;
  CommandLine cmd = null;
  Scanner sc = null;
   try {
	cmd = parser.parse(options, args);
} catch (ParseException e) {
	e.printStackTrace();
}

   if (cmd.hasOption("h"))
    help();
   if (cmd.hasOption("nested"))
	   cgo = CodeGenerationOption.NESTED;
   if (cmd.hasOption("ir_disable"))
	   iro = IROption.DISABLE;
   if (cmd.hasOption("f")) {
	   //log.log(Level.INFO, "Using cli argument -f=" + cmd.getOptionValue("f"));
	   String filename = cmd.getOptionValue("f");
	   sc = new Scanner(new File(filename));
   } else {   
	   sc = new Scanner(System.in);
   }
   String plan;
   while((sc.hasNextLine())) {
	   plan = sc.nextLine();
	   System.out.println(plan);
	   EPtoSQL ets = new EPtoSQL();
	   ets.compile(cgo, iro, new ByteArrayInputStream(plan.getBytes()), System.out);
   }
   sc.close();
 }
/**
 * Prints out options specifying requirements
 */
 private void help() {
  HelpFormatter formatter = new HelpFormatter();

  formatter.printHelp("Main", options);
  System.exit(0);
 }
}
