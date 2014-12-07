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


package edu.brandeis.cs.develops.eptosql.parser.lexer;

/**
 * 
 * @author Ryan Marcus
 * @since 11/17/2014
 */
public class Token {
	
	public Token(TokenType t) {
		type = t;
	}
	
	private TokenType type;
	private String content;
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public String toString() {
		if (this.getContent() == null)
			return type.toString();
		
		return type.toString() + " (" + getContent() + ")";
	}
}
