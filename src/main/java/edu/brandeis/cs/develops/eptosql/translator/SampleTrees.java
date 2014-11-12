package edu.brandeis.cs.develops.eptosql.translator;

public class SampleTrees {
	public static Relation BasicJoinExpression() {
		return new Join(new Table("t1"),new Table("t2"),"att1=att2","HashJoin");
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
        
   
        
        public static void main(String[] args) {
            Relation employees = new Table("employees");
            Relation employers = new Table("employers");
            Relation deparments = new Table("departments");
            Relation depToEmp = new Table("employees_to_department");
            Relation emp_employees = new Join(employees, employers, "yee_employer_id=yer_employer_id", "INNER HASH");
            Relation emp_dep = new Join(depToEmp, deparments, "dep_department_id=etd_department_id", "INNER HASH");
            Relation full = new Join(emp_employees, emp_dep, "yee_employee_id=etd_employee_id", "INNER HASH");
            System.out.println(SQLGenerator.createSQL(full));
        }
}
