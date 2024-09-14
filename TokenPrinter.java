import java.io.FileReader;
import java.io.IOException;

public class TokenPrinter {

    // Método para imprimir tokens com base no código gerado pelo JFlex
    public static void printToken(int tokenType, String lexeme) {
        switch (tokenType) {
            case 1: System.out.println("CLASS"); break;
            case 2: System.out.println("INT"); break;
            case 3: System.out.println("BOOL"); break;
            case 4: System.out.println("VOID"); break;
            case 5: System.out.println("IF"); break;
            case 6: System.out.println("ELSE"); break;
            case 7: System.out.println("RETURN"); break;
            case 8: System.out.println("TRUE"); break;
            case 9: System.out.println("FALSE"); break;
            case 10: System.out.println("NOT"); break;
            case 11: System.out.println("AND"); break;
            case 12: System.out.println("OR"); break;
            case 13: System.out.println("ASSIGN"); break;
            case 14: System.out.println("EQUALS"); break;
            case 15: System.out.println("LESS_THAN"); break;
            case 16: System.out.println("GREATER_THAN"); break;
            case 17: System.out.println("PLUS"); break;
            case 18: System.out.println("TIMES"); break;
            case 19: System.out.println("LPAREN"); break;
            case 20: System.out.println("RPAREN"); break;
            case 21: System.out.println("LBRACE"); break;
            case 22: System.out.println("RBRACE"); break;
            case 23: System.out.println("SEMICOLON"); break;
            case 24: System.out.println("COMMA"); break;
            case 25: System.out.println("IDENTIFIER: " + lexeme); break;
            case 26: System.out.println("NUMBER: " + lexeme); break;
            default: System.out.println("UNKNOWN: " + lexeme); break;
        }
    }

    public static void main(String[] args) {
        try {
            FoolLexer lexer = new FoolLexer(new FileReader("test.fool"));
            int token;
            while ((token = lexer.yylex()) != -1) {
                String lexeme = lexer.yytext(); // Obtém o texto associado ao token
                printToken(token, lexeme);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
