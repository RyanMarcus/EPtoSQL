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


package edu.brandeis.cs.develops.eptosql;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import edu.brandeis.cs.develops.eptosql.frontend.CodeGenerationOption;
import edu.brandeis.cs.develops.eptosql.frontend.EPtoSQL;
import edu.brandeis.cs.develops.eptosql.frontend.IROption;

public class Cli {
 //private static final Logger log = Logger.getLogger(Cli.class.getName());
 private String[] args = null;
 private Options options = new Options();

 public Cli(String[] args) {

  this.args = args;

  options.addOption("h", "help", false, "show help.");
  options.addOption("f", "filename", true, "file to translate.");
  options.addOption("nested", "nested", false, "if included, generate nested code");
  options.addOption("disable_ir", "disable_ir", false, "if included, disable ir");
 }

 public void parse() throws IOException {
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
   if (cmd.hasOption("disable_ir"))
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
	   plan = sc.nextLine().trim();
	   if (plan.length() == 0)
		   continue;
	   System.out.println(EPtoSQL.syncCompile(cgo, iro, plan));
   }
   sc.close();
 }
/**
 * Prints out options specifying requirements
 */
 private void help() {
  HelpFormatter formatter = new HelpFormatter();

  formatter.printHelp("eptosql.jar", options);
  System.exit(0);
 }
}
