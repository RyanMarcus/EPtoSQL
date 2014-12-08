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
        
        public static Relation StoreProductDepartments(){
            Relation stores = new Table("stores");
            Relation stores_where = new Selection(stores, "LOWER(store_name) LIKE '%b[a-z]d%'");
            Relation departments = new Table("departments");
            Relation products = new Table("products");
            Relation stores_to_products = new Table ("stores_to_products");
            Relation stores_s2p_join = new Join (stores_where, stores_to_products, "store_id=s2p_store_id", "PHJOIN");
            Relation stores_products_join = new Join(stores_s2p_join, products, "product_id=s2p_product_id", "PHJOIN");
            Relation all_joins= new Join(stores_products_join, departments, "department_id=product_department_id", "PHJOIN");
            Relation full = new Selection(all_joins, "LOWER(department_name) = 'test department 1'");;
           
            return full;
        }

}
