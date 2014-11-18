package edu.brandeis.cs.develops.eptosql;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import edu.brandeis.cs.develops.eptosql.code_generator.SQLGenerator;
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
  options.addOption("e", "expression", true, "expression to translate.");

 }

 public void parse() {
  CommandLineParser parser = new BasicParser();

  CommandLine cmd = null;
  try {
   cmd = parser.parse(options, args);

   if (cmd.hasOption("h"))
    help();

   if (cmd.hasOption("e")) {
    log.log(Level.INFO, "Using cli argument -e=" + cmd.getOptionValue("e"));
    // Run user command
    try {
		Parser p = new Parser();
		ASTNode n = p.parseString(new String(cmd.getOptionValue("e")));
		ASTTranslator t = new ASTTranslator();
		Relation r = t.parse((EP) n);
		System.out.println(SQLGenerator.createUnnestedSQL(r));

	} catch (ParserException e) {
		System.err.println("Parser exception: " + e.getMessage());
		e.printStackTrace();
		System.exit(1);
	}
   } else {
    log.log(Level.SEVERE, "Missing e option");
    help();
   }

  } catch (ParseException e) {
   log.log(Level.SEVERE, "Failed to parse comand line properties", e);
   help();
  }
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
