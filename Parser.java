import java.io.*;

public class Parser {

	private static int lookahead;
	private Lex lex;
	
	public Parser(Lex l) throws IOException {
		lex = l;
		lookahead = lex.getNextToken();
		if (lookahead != 9) {
			throw new Error("Syntax Error: must begin with '{'");
		}
	}
	
	public void block() throws IOException {
		match(Tkns.LEFT_B);
		items();
		match(Tkns.RIGHT_B);
	}
	
	public void items() throws IOException {
		item();
		itemsP();
	}
	public void itemsP() throws IOException {
		if (lookahead != Tkns.RIGHT_B) {
			item();
			itemsP();
		}
	}
	
	public void item() throws IOException {
		if (lookahead == Tkns.INT) {
			decl();
		} else {
			stmt();
		}
	}
	
	public void decl() throws IOException {
		match(Tkns.INT);
		match(Tkns.IDENT);
		match(Tkns.SEMI);
	}
	
	public void stmt() throws IOException {
		if (lookahead == Tkns.IF) {
			ifstmt();
		} else if (lookahead == Tkns.WHILE) {
			whilestmt();
		} else if (lookahead == Tkns.PRINT) {
			printstmt();
		} else {
			expr();
			match(Tkns.SEMI);
		}
	}
	
	public void ifstmt() throws IOException {
		match(Tkns.IF);
		match(Tkns.LEFT_P);
		expr();
		match(Tkns.RIGHT_P);
		block();
	}
	
	public void whilestmt() throws IOException {
		match(Tkns.WHILE);
		match(Tkns.LEFT_P);
		expr();
		match(Tkns.RIGHT_P);
		block();
	}
	
	public void printstmt() throws IOException {
		match(Tkns.PRINT);
		match(Tkns.LEFT_P);
		match(Tkns.IDENT);
		match(Tkns.RIGHT_P);
		match(Tkns.SEMI);
	}
	
	public void expr() throws IOException {
		A();
	}
	
	public void A() throws IOException {
		if (lookahead == Tkns.IDENT) {
			match(Tkns.IDENT);
		}
		if (lookahead == Tkns.EQUAL) {
			match(Tkns.EQUAL);
			A();
		} else {
			B();
		}
	}
	
	public void B() throws IOException {
		C();
		Bp();
	}
	public void Bp() throws IOException {
		if (lookahead == Tkns.EQUAL_EQUAL) {
			match(Tkns.EQUAL_EQUAL);
			C();
			Bp();
		} else if (lookahead == Tkns.NOT_EQUAL) {
			match(Tkns.NOT_EQUAL);
			C();
			Bp();
		}
	}
	
	public void C() throws IOException {
		D();
		Cp();
	}
	public void Cp() throws IOException {
		if (lookahead == Tkns.LESS) {
			match(Tkns.LESS);
			D();
			Cp();
		} else if (lookahead == Tkns.GREATER) {
			match(Tkns.GREATER);
			D();
			Cp();
		} else if (lookahead == Tkns.LESS_EQ) {
			match(Tkns.LESS_EQ);
			D();
			Cp();
		} else if (lookahead == Tkns.GREATER_EQ) {
			match(Tkns.GREATER_EQ);
			D();
			Cp();
		}
	}
	
	public void D() throws IOException {
		E();
		Dp();
	}
	public void Dp() throws IOException {
		if (lookahead == Tkns.ADD) {
			match(Tkns.ADD);
			E();
			Dp();
		} else if (lookahead == Tkns.SUB) {
			match(Tkns.SUB);
			E();
			Dp();
		}
	}
	
	public void E() throws IOException {
		F();
		Ep();
	}
	public void Ep() throws IOException {
		if (lookahead == Tkns.MULT) {
			match(Tkns.MULT);
			F();
			Ep();
		} else if (lookahead == Tkns.DIV) {
			match(Tkns.DIV);
			F();
			Ep();
		}
	}
	
	public void F() throws IOException {
		if (lookahead == Tkns.LEFT_P) {
			match(Tkns.LEFT_P);
			A();
			match(Tkns.RIGHT_P);
		} else if (lookahead == Tkns.INT_LIT) {
			match(Tkns.INT_LIT);
		} else if (lookahead == Tkns.IDENT) {
			match(Tkns.IDENT);
		}
	}
	
	public void match(int t) throws IOException {
		if (lookahead == t) {
			getNextToken();
		} else {
			throw new Error("Syntax Error");
		}
	}
	
	public void getNextToken() throws IOException {
		lookahead = lex.getNextToken();
	}
}
