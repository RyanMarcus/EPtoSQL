package edu.brandeis.cs.develops.eptosql.translator;

public class SampleTrees {
	public static Relation BasicJoinExpression() {
		return new Join(new Table("t1"),new Table("t2"),"id","HashJoin");
	}
}
