package Update;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.List;
import java.util.ArrayList;

public class Tokenizer {
    private final StreamTokenizer tokenizer;

    public Tokenizer(Reader reader) {
        tokenizer = new StreamTokenizer(reader);
        tokenizer.parseNumbers();
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('=');
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('A', 'Z');
        tokenizer.wordChars('_', '_');
    }

    public List<Token> tokenize() throws IOException {
        List<Token> tokens = new ArrayList<>();

        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    tokens.add(new Token(Token.Type.NUMBER, Double.toString(tokenizer.nval)));
                    break;
                case '+':
                    tokens.add(new Token(Token.Type.PLUS, "+"));
                    break;
                case '-':
                    tokens.add(new Token(Token.Type.MINUS, "-"));
                    break;
                case '*':
                    tokens.add(new Token(Token.Type.MULTIPLY, "*"));
                    break;
                case '/':
                    tokens.add(new Token(Token.Type.DIVIDE, "/"));
                    break;
                case '(':
                    tokens.add(new Token(Token.Type.LPAREN, "("));
                    break;
                case ')':
                    tokens.add(new Token(Token.Type.RPAREN, ")"));
                    break;
                case '=':
                    tokens.add(new Token(Token.Type.ASSIGN, "="));
                    break;
                case ';':
                    tokens.add(new Token(Token.Type.SEMICOLON, ";"));
                    break;
                case StreamTokenizer.TT_WORD:
                    String word = tokenizer.sval;
                    switch (word) {
                        case "DISPLAY":
                            tokens.add(new Token(Token.Type.DISPLAY, "DISPLAY"));
                            break;
                        case "READ":
                            tokens.add(new Token(Token.Type.READ, "READ"));
                            break;
                        default:
                            tokens.add(new Token(Token.Type.IDENTIFIER, word));
                            break;
                    }
                    break;
                default:
                    throw new IOException("Unexpected character: " + tokenizer.ttype);
            }
        }

        return tokens;
}}