package edu.brandeis.cs.develops.eptosql.parser.parser;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import edu.brandeis.cs.develops.eptosql.parser.lexer.Lexer;
import edu.brandeis.cs.develops.eptosql.parser.lexer.Token;
import edu.brandeis.cs.develops.eptosql.parser.lexer.TokenType;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.ASTNode;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.COMMA_EXPR;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.DOUBLE_COMMA_EXPR;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EP;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.EXPR;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.JOIN;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.JOIN_TYPE;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.PAREN_STRING;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.PAREN_STRING_EXPR;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.PAREN_STRING_EXPR_EXPR;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.SELECTION;
import edu.brandeis.cs.develops.eptosql.parser.parser.AST.TABLE;

/* Grammer:
 * 
 * (1)  EP: EXPR
 * (2)  EXPR: SELECTION | JOIN | TABLE
 * (3)  JOIN_TYPE: PHJOIN | PNLJOIN | PMJOIN
 * 
 * (4)  TABLE: PTABLE PAREN_STRING
 * (5)  SELECTION: PSELECT PAREN_STRING_EXPR
 * (6)  JOIN: JOIN_TYPE PAREN_STRING_EXPR_EXPR
 * 
 * (7)  COMMA_EXPR: COMMA EXPR
 * (8)  DOUBLE_COMMA_EXPR: COMMA_EXPR COMMA_EXPR
 * 
 * (9)  PAREN_STRING: LP STRING RP
 * (10) PAREN_STRING_EXPR: LP STRING COMMA_EXPR RP
 * (11) PAREN_STRING_EXPR_EXPR: LP STRING DOUBLE_COMMA_EXPR RP
 * 
 */

/**
 * @author Ryan Marcus
 * @since 11/17/2014
 */
public class ShiftReduceParser {
	private Deque<ASTNode> stack;

	public ShiftReduceParser() {
		stack = new LinkedList<ASTNode>();
	}

	public ASTNode parseTokens(Iterator<Token> tokens) throws ParserException {
		stack = new LinkedList<ASTNode>();
		while (tokens.hasNext()) {
			stack.push(shift(tokens.next()));
			 System.out.println(stack);
			while (reduce()) {
				 System.out.println(stack);
			}
		}

		if (stack.size() != 1)
			throw new ParserException(
					"More than one element remaining on the stack: "
							+ stack.toString());

		if (!(stack.peek() instanceof EP))
			throw new ParserException(
					"Item left on stack is not of proper type: "
							+ stack.toString());

		return stack.pop();
	}

	private boolean reduce() throws ParserException {
		ASTNode lookahead = stack.pop();
		if (lookahead.isTokenType(TokenType.RP)) {
			// apply rule 11
			if (stack.peek() instanceof DOUBLE_COMMA_EXPR) {
				PAREN_STRING_EXPR_EXPR toPush = new PAREN_STRING_EXPR_EXPR();
				toPush.double_comma_expr = (DOUBLE_COMMA_EXPR) stack.pop();
				toPush.string = stack.pop().getToken().getContent();

				if (stack.isEmpty()
						|| stack.peek().getToken().getType() != TokenType.LP) {
					throw new ParserException("Was expecting LP");
				}

				stack.pop(); // the LP
				stack.push(toPush);
				return true;
			}

			// apply rule 10
			if (stack.peek() instanceof COMMA_EXPR) {
				PAREN_STRING_EXPR toPush = new PAREN_STRING_EXPR();
				toPush.expr = (COMMA_EXPR) stack.pop();

				if (stack.isEmpty()
						|| stack.peek().getToken() == null
						|| stack.peek().getToken().getType() != TokenType.STRING)
					throw new ParserException(
							"Expected string followed by comma expression");

				toPush.string = stack.pop().getToken().getContent();
				stack.pop(); // the LP
				stack.push(toPush);
				return true;
			}

			// apply rule 9
			if (stack.peek().isTokenType(TokenType.STRING)) {
				PAREN_STRING toPush = new PAREN_STRING();
				toPush.string = stack.pop().getToken().getContent();
				stack.pop(); // the LP
				stack.push(toPush);
				return true;
			}
		}

		if (lookahead instanceof COMMA_EXPR) {
			// apply rule 8
			if (stack.peek() instanceof COMMA_EXPR) {
				DOUBLE_COMMA_EXPR toPush = new DOUBLE_COMMA_EXPR();
				toPush.expr1 = (COMMA_EXPR) stack.pop();
				toPush.expr2 = (COMMA_EXPR) lookahead;
				stack.push(toPush);
				return true;
			}
		}

		if (lookahead instanceof EXPR) {
			// apply rule 1
			if (stack.isEmpty()) {
				EP toPush = new EP();
				toPush.expr = (EXPR) lookahead;
				stack.push(toPush);
				return true;
			}

			// apply rule 7
			if (stack.peek().isTokenType(TokenType.COMMA)) {
				COMMA_EXPR toPush = new COMMA_EXPR();
				toPush.expr = (EXPR) lookahead;
				stack.pop(); // the comma
				stack.push(toPush);
				return true;
			}
		}

		if (lookahead instanceof PAREN_STRING_EXPR_EXPR) {
			// apply rule 6
			if (stack.peek() instanceof JOIN_TYPE) {
				JOIN toPush = new JOIN();
				toPush.paren_string_expr_expr = (PAREN_STRING_EXPR_EXPR) lookahead;
				toPush.join_type = (JOIN_TYPE) stack.pop();
				stack.push(toPush);
				return true;
			}
		}

		if (lookahead instanceof PAREN_STRING_EXPR) {
			// apply rule 5
			if (stack.peek().isTokenType(TokenType.PSELECT)) {
				SELECTION toPush = new SELECTION();
				toPush.paren_string_expr = (PAREN_STRING_EXPR) lookahead;
				stack.pop(); // the PSELECT
				stack.push(toPush);
				return true;
			}
		}

		if (lookahead instanceof PAREN_STRING) {
			// apply rule 4
			if (stack.peek().isTokenType(TokenType.PTABLE)) {
				TABLE toPush = new TABLE();
				toPush.paren_string = (PAREN_STRING) lookahead;
				stack.pop(); // the PSELECT
				stack.push(toPush);
				return true;
			}
		}

		if (lookahead.isTokenType(TokenType.PHJOIN)
				|| lookahead.isTokenType(TokenType.PMJOIN)
				|| lookahead.isTokenType(TokenType.PNLJOIN)) {
			// apply rule 3
			JOIN_TYPE toPush = new JOIN_TYPE();
			toPush.type = lookahead.getToken().getType().toString();
			stack.push(toPush);
			return true;
		}

		if (lookahead instanceof SELECTION) {
			// apply rule 2
			EXPR toPush = new EXPR();
			toPush.selection = (SELECTION) lookahead;
			stack.push(toPush);
			return true;
		}

		if (lookahead instanceof JOIN) {
			// apply rule 2
			EXPR toPush = new EXPR();
			toPush.join = (JOIN) lookahead;
			stack.push(toPush);
			return true;
		}

		if (lookahead instanceof TABLE) {
			// apply rule 2
			EXPR toPush = new EXPR();
			toPush.table = (TABLE) lookahead;
			stack.push(toPush);
			return true;
		}

		stack.push(lookahead);
		return false;
	}

	private ASTNode shift(Token next) {
		return new ASTNode(next);
	}

	public static void main(String[] args) throws ParserException {
		ShiftReduceParser p = new ShiftReduceParser();
		p.parseTokens(Lexer
				.createLexerForString("PMJOIN(ps_suppkey = s_suppkey, PTABLE(PS), PTABLE(S))"));
	}
}
