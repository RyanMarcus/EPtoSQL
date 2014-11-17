package edu.brandeis.cs.develops.eptosql.translator;

/**
 * Sample Relation trees for use in building future sections of the program
 * @author Rachel Leeman-Munk
 * @since 11/17/2014
 */
public class SampleTrees {
	/**
	 * @return basic Join Relation
	 */
	public static Relation BasicJoinExpression() {
		return new Join(new Table("t1"),new Table("t2"),"att1=att2","HashJoin");
	}
	/**
	 * @return basic Selection Relation
	 */
	public static Relation BasicSelectionExpression() {
		return new Selection(new Table("t3"),"att2>10");
	}
	/**
	 * @return two Join Relation
	 */
	public static Relation TwoJoinExpression() {
		return new Join(new Table("t4"), BasicJoinExpression(), "att3>att3", "MergeJoin");
	}
	/**
	 * @return Join of Table and Selection Relation
	 */
	public static Relation JoinSelectionExpression() {
		return new Join(new Table("t5"), BasicSelectionExpression(), "att4=att4","HashJoin");
	}
	/**
	 * @return Two Join of Join and Selection Relation
	 */
	public static Relation TwoJoinSelectionExpression() {
		return new Join(BasicJoinExpression(), BasicSelectionExpression(), "att5=att5", "MergeJoin");
	}
        public static Relation EmployeeDepartmentExpression(){
            Relation employees = new Table("employees");
            Relation employees_sel = new Selection(employees, "yee_employee_last_name='Dallas'");
            Relation employers = new Table("employers");
            Relation deparments = new Table("departments");
            Relation deparments_sel = new Selection(deparments, "dep_department_id=1");
            Relation depToEmp = new Table("employees_to_department");
            Relation emp_employees = new Join(employees_sel, employers, "yee_employer_id=yer_employer_id", "PHJOIN");
            Relation emp_dep = new Join(depToEmp, deparments_sel, "dep_department_id=etd_department_id", "PNLJOIN");
            Relation full = new Join(emp_employees, emp_dep, "yee_employee_id=etd_employee_id", "PHJOIN");
            return full;
        }
}
