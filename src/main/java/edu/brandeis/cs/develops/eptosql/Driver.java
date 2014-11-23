package edu.brandeis.cs.develops.eptosql;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import edu.brandeis.cs.develops.eptosql.frontend.CodeGenerationOption;
import edu.brandeis.cs.develops.eptosql.frontend.EPtoSQL;
import edu.brandeis.cs.develops.eptosql.frontend.IROption;

public class Driver {

	public static void main(String[] args) {
		CodeGenerationOption cgo = CodeGenerationOption.UNNESTED;
		IROption iro = IROption.ENABLE;
		
		Scanner sc = new Scanner(System.in);
		String plan;
		while ((plan = sc.nextLine()) != null) {
			EPtoSQL ets = new EPtoSQL();
			ets.compile(cgo, iro, new ByteArrayInputStream(plan.getBytes()));
		}

		sc.close();

	}

}
