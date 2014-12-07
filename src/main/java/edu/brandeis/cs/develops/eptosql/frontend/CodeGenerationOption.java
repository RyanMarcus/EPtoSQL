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


package edu.brandeis.cs.develops.eptosql.frontend;

/**
 * 
 * @author Ryan Marcus < ryan @ rmarcus.info >
 *
 * An enum containing options to specify the code generation mode. Currently,
 * this can be either "nested" (where each operator is contained in a query),
 * or "unnested", where predicates are pulled into the WHERE clause and tables
 * are pulled into the FROM clause. See the code_generator package for more information.
 *
 */
public enum CodeGenerationOption {
	NESTED, UNNESTED;
}
