import java.io.*;

public class Lex {

	BufferedReader br;
	char nextChar;

	public Lex (BufferedReader buffread) throws IOException {
		br = buffread;											//assign br object
		nextChar = (char)br.read();								//get first token
	}
	
	public void getNextChar() throws IOException {
		nextChar = (char)br.read();
	}
	
	public boolean isDigit() {
		return ((nextChar >= '0') && ( nextChar <= '9'));
	}

	public boolean isLetter() {
		return ((nextChar >= 'a') && ( nextChar <= 'z'));
	}

	public boolean ws() {
		return ((nextChar == ' ') || (nextChar == '\t') || (nextChar == '\n'));
	}

	public boolean isIdentChar() {
		return (isDigit() || isLetter() || (nextChar == '_'));
	}
  
	public int getNextToken() throws IOException {
		int state = 0;
		boolean stop = false;
    
		while (!stop) {
			switch (state) {
				case 0:								//initial case
					switch (nextChar) {
						case 'i':
							getNextChar();
							state = 1;
							break;
						case 'w':
							getNextChar();
							state = 3;
							break;
						case 'p':
							getNextChar();
							state = 8;
							break;
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							getNextChar();
							state = 13;
							break;
						case ';':
							getNextChar();
							return Tkns.SEMI;
						case '=':
							getNextChar();
							state = 14;
							break;
						case '(':
							getNextChar();
							return Tkns.LEFT_P;
						case ')':
							getNextChar();
							return Tkns.RIGHT_P;
						case '{':
							getNextChar();
							return Tkns.LEFT_B;
						case '}':
							getNextChar();
							return Tkns.RIGHT_B;
						case '/':
							getNextChar();
							return Tkns.DIV;
						case '*':
							getNextChar();
							return Tkns.MULT;
						case '-':
							getNextChar();
							return Tkns.SUB;
						case '+':
							getNextChar();
							return Tkns.ADD;
						case '<':
							getNextChar();
							state = 15;
							break;
						case '>':
							getNextChar();
							state = 16;
							break;
						case '!':
							getNextChar();
							state = 17;
							break;
						case ' ':
						case '\t':
						case '\n':
							getNextChar();
							break;
						default:
							if (isLetter()) {
								getNextChar();
								state = 99;
							} else {
								return 666;		//end token
							}
							break;
					}
					break;
				case 1:
					if (nextChar == 'f') {
						getNextChar();
						state = 2;
					} else if (nextChar == 'n') {	//NEWEST
						getNextChar();
						state = 18;
					} else {
						state = 99;
					}
					break;
				case 2:						//see if truly IF
					if (isIdentChar()) {
						getNextChar();
						state = 99;
					} else {
						return Tkns.IF;
					}
					break;
				case 3:
					if (nextChar == 'h') {
						getNextChar();
						state = 4;
					} else {
						state = 99;
					}
					break;
				case 4:
					if (nextChar == 'i') {
						getNextChar();
						state = 5;
					} else {
						state = 99;
					}
					break;
				case 5:
					if (nextChar == 'l') {
						getNextChar();
						state = 6;
					} else {
						state = 99;
					}
					break;
				case 6:
					if (nextChar == 'e') {
						getNextChar();
						state = 7;
					} else {
						state = 99;
					}
					break;
				case 7:							//see if truly WHILE
					if (isIdentChar()) {
						getNextChar();
						state = 99;
					} else {
						return Tkns.WHILE;
					}
					break;
				case 8:
					if (nextChar == 'r') {
						getNextChar();
						state = 9;
					} else {
						state = 99;
					}
					break;
				case 9:
					if (nextChar == 'i') {
						getNextChar();
						state = 10;
					} else {
						state = 99;
					}
					break;
				case 10:
					if (nextChar == 'n') {
						getNextChar();
						state = 11;
					} else {
						state = 99;
					}
					break;
				case 11:
					if (nextChar == 't') {
						getNextChar();
						state = 12;
					} else {
						state = 99;
					}
					break;
				case 12:						//see if truly PRINT
					if (isIdentChar()) {
						getNextChar();
						state = 99;
					} else {
						return Tkns.PRINT;
					}
					break;
				case 13:
					if (isDigit()) {
						getNextChar();
					} else {
						return Tkns.INT_LIT;
					}
					break;
				case 14:
					if (nextChar == '=') {
						getNextChar();
						return Tkns.EQUAL_EQUAL;
					} else {
						return Tkns.EQUAL;
					}
				case 15:
					if (nextChar == '=') {
						getNextChar();
						return Tkns.LESS_EQ;
					} else {
						return Tkns.LESS;
					}
				case 16:
					if (nextChar == '=') {
						getNextChar();
						return Tkns.GREATER_EQ;
					} else {
						return Tkns.GREATER;
					}
				case 17:
					if (nextChar == '=') {
						getNextChar();
						return Tkns.NOT_EQUAL;
					} else {
						return 666;
					}
				case 18:
					if (nextChar == 't') {
						getNextChar();
						state = 19;
					} else {
						state = 99;
					}
					break;
				case 19:							//see if truly INT
					if (isIdentChar()) {
						getNextChar();
						state = 99;
					} else {
						return Tkns.INT;
					}
					break;
				case 99:
					if (isIdentChar()) {
						getNextChar();
					} else {
						return Tkns.IDENT;
					}
					break;
				default:
					stop = true;
			}
		}
		return -1;
	}
}
