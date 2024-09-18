import java.io.FileReader;
import java.io.IOException;

public class TokenPrinter {

    // Método para imprimir tokens com base no código gerado pelo JFlex
    public static void printToken(final TokenType tokenType, final String lexeme) {
        System.out.println("[" + tokenType + "] [" + lexeme + "]");
    }

    // Método para mapear o inteiro retornado pelo JFlex para o TokenType correspondente
    public static TokenType mapIntToTokenType(int tokenType) {
        switch (tokenType) {
            case 1: return TokenType.CLASS;
            case 2: return TokenType.INT;
            case 3: return TokenType.BOOL;
            case 4: return TokenType.VOID;
            case 5: return TokenType.IF;
            case 6: return TokenType.ELSE;
            case 7: return TokenType.RETURN;
            case 8: return TokenType.TRUE;
            case 9: return TokenType.FALSE;
            case 10: return TokenType.NOT;
            case 11: return TokenType.AND;
            case 12: return TokenType.OR;
            case 13: return TokenType.WHILE;
            case 14: return TokenType.ASSIGN;
            case 15: return TokenType.EQUALS;
            case 16: return TokenType.LESS_THAN;
            case 17: return TokenType.GREATER_THAN;
            case 18: return TokenType.PLUS;
            case 19: return TokenType.TIMES;
            case 20: return TokenType.LPAREN;
            case 21: return TokenType.RPAREN;
            case 22: return TokenType.LBRACE;
            case 23: return TokenType.RBRACE;
            case 24: return TokenType.SEMICOLON;
            case 25: return TokenType.COMMA;
            case 26: return TokenType.MINUS;
            case 27: return TokenType.DIVIDE;
            case 28: return TokenType.NOT_EQUALS;
            case 29: return TokenType.IDENTIFIER;
            case 30: return TokenType.NUMBER;
            default: return TokenType.UNKNOWN;
        }
    }

    public static void main(final String[] args) {
        try {
            final FoolLexer lexer = new FoolLexer(new FileReader("test.fool"));
            int token;
            while ((token = lexer.yylex()) != -1) {
                final String lexeme = lexer.yytext(); // Obtém o texto associado ao token
                final TokenType tokenType = mapIntToTokenType(token); // Mapeia o inteiro para TokenType
                printToken(tokenType, lexeme);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
