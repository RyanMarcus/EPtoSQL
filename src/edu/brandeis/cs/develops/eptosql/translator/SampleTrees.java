package edu.brandeis.cs.develops.eptosql.translator;

public class SampleTrees {
	public static Relation BasicJoinExpression() {
		return new Join(new Table("t1"),new Table("t2"),"att1=att1","HashJoin");
	}
	public static Relation BasicSelectionExpression() {
		return new Selection(new Table("t3"),"att2>10");
	}
	public static Relation TwoJoinExpression() {
		return new Join(new Table("t4"), BasicJoinExpression(), "att3>att3", "MergeJoin");
	}
	public static Relation JoinSelectionExpression() {
		return new Join(new Table("t5"), BasicSelectionExpression(), "att4=att4","HashJoin");
	}
	public static Relation TwoJoinSelectionExpression() {
		return new Join(BasicJoinExpression(), BasicSelectionExpression(), "att5=att5", "MergeJoin");
	}
}
