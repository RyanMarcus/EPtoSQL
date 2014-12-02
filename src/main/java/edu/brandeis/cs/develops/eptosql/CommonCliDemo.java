package edu.brandeis.cs.develops.eptosql;

import java.io.IOException;

public class CommonCliDemo {

	public static void main(String[] args) throws IOException {
		new Cli(args).parse();
	}
}
