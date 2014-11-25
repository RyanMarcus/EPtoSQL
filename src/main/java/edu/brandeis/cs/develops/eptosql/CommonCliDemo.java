package edu.brandeis.cs.develops.eptosql;

import java.io.FileNotFoundException;

public class CommonCliDemo {

	public static void main(String[] args) throws FileNotFoundException {
		new Cli(args).parse();
	}
}
