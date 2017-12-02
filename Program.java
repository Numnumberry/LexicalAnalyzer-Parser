import java.io.*;

public class Program {
	public static void main(String[] args) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			Lex lex = new Lex(br);
			Parser parser = new Parser(lex);

			parser.block();
			System.out.println("valid");

		} catch (Exception e) {
			System.out.println("Input file name as command line argument!");
		}
	}
}
