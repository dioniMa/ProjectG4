package Update;

public class Token {
    public enum Type {
        NUMBER,
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        LPAREN,
        RPAREN,
        ASSIGN,
        DISPLAY,
        READ,
        IDENTIFIER, SEMICOLON
    }

    public final Type type;
    public final String lexeme;
    

    public Token(Type type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return String.format("%s('%s')", type.name(), lexeme);
    }
}
