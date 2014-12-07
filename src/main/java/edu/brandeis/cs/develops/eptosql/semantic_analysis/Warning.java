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


package edu.brandeis.cs.develops.eptosql.semantic_analysis;

/**
 * A class representing a warning (as opposed to a error) found
 * by the semantic analyzer. The message will contain more information
 * about the error.
 * 
 * @author Ryan Marcus < ryan @ rmarcus.info >
 *
 */
public class Warning extends SemanticAnnotation {

	public Warning(String s) {
		super(s);
	}

}
