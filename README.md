# A DSL for Physical Plans

## What?

[ ![Codeship Status for RyanMarcus/DevelOPSToSQLServerCompiler](https://codeship.com/projects/e74702d0-4ccd-0132-de06-06f14de298ab/status?branch=master)](https://codeship.com/projects/47240)

![GNU GPLv3](http://www.gnu.org/graphics/gplv3-127x51.png)


Compiles execution plans written in the [DevelOPs](http://bit.ly/1w8OuVb) execution plan DSL into SQL statements that force SQL Server to use the specified plan.

For example, given an execution plan like this (which represents a merge join of `table1` and `table2`):

    PMJOIN(attr1 = attr2, PTABLE(table1), PTABLE(table2))
    
Our compiler will output:

    SELECT *  FROM table1 INNER MERGE JOIN table2 table_right ON (attr1 = attr2) OPTION(FORCE ORDER);

For a more complex execution plan, like this:

    PNLJOIN(s_nationkey = n_nationkey, PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S)), PSELECT(n_name = 'ASIA', PTABLE(N)))

Our compiler will generate SQL like this:

    SELECT *  FROM (SELECT *  FROM PS  INNER MERGE JOIN S table_right ON (ps_suppkey = s_suppkey)) table_left INNER LOOP JOIN (SELECT *  FROM N table_inner WHERE n_name = 'ASIA') table_right ON (s_nationkey = n_nationkey) OPTION(FORCE ORDER);
 

Note that SQL Server will not allow you to use anything but a nested loop join if the join predicate involves an attribute that is also used within a selection. Our compiler automatically detects and decomposes these queries into sub-queries, so if you use an execution plan like this:

    PMJOIN(attr1 = attr2, PSELECT(attr1 = "value", PTABLE(table1)), PTABLE(table2))

Our compiler will produce SQL like this:

    SELECT * INTO #temp1 FROM table1 table_inner WHERE attr1 = "value" OPTION(FORCE ORDER);
    SELECT * INTO #temp2 FROM #temp1  INNER MERGE JOIN table2 table_right ON (attr1 = attr2) OPTION(FORCE ORDER);

## Why?

This compiler allows query optimizer developers to test the performance of queries created by their optimizers against the SQL Server execution engine.

## How?

You can download a pre-built JAR file [here](http://bgodby.info/eptosql.zip). To run it, use:

    java -jar eptosql.jar --help
    
To simply translate from standard in, use:

    java -jar eptosql.jar
    
To translate from a file, use:

    java -jar eptosql.jar -f FILE
    
Happy compiling. :)

## Who?

Project members:

  * Brionne Godby `< bgodby (at) brandeis (dot) edu >`
  * [Rachel Leeman-Munk](http://rleemanmunk.me) `< rmunk (at) brandeis (dot) edu >`
  * [Ryan Marcus](http://rmarcus.info) `< rcmarcus (at) brandeis (dot) edu >`
  
With thanks to:

  * Zhibo Peng `< docp (at) brandeis (dot) edu >`

## System Design

The following UML diagrams depict design decisions and implementation details.

![Activity Diagram](https://github.com/RyanMarcus/DevelOPSToSQLServerCompiler/raw/master/doc/uml/workflow.png)
![Sequence Diagram](https://github.com/RyanMarcus/DevelOPSToSQLServerCompiler/raw/master/doc/uml/sequence.png)


[Presentation](http://www.slideshare.net/slideshow/embed_code/42232529)

