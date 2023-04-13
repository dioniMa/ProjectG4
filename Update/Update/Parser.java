package Update;

import java.lang.reflect.Field;
import java.util.*;
import java.io.*;
public class Parser {
    private final List<Token> tokens;
    private int position;
   
    private  Map<String, Double> variables;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
        this.variables = new HashMap<>();
    
    }

    public void parse() {
        while (position < tokens.size()) {
            statement();
        }
    }

    
    private void statement() {
        Token token = peek();

        switch (token.type) {
            case DISPLAY:
                display();
                break;
            case READ:
                read();
                break;
            case IDENTIFIER:
                assignment();
                break;
            default:
                throw new RuntimeException("Invalid statement at position " + position);
        }
    }

    private void display() {
        consume(Token.Type.DISPLAY);

        Token token = peek();
      
        if (token.type == Token.Type.IDENTIFIER) {
//            String variableName = token.lexeme;
//        	if (!variables.containsKey(variableName)) {
//        		System.out.println(variables.values().contains(variableName));
//        	}
       
        	System.out.println(token.lexeme.substring(0, token.lexeme.length()));
            consume(Token.Type.IDENTIFIER);
       
        } else {
            double value = expr();
            System.out.println(value);
        }

        consume(Token.Type.SEMICOLON);
    }

    private void read() {
        consume(Token.Type.READ);

        Token variableToken = consume(Token.Type.IDENTIFIER);
        String variableName = variableToken.lexeme;
        double value;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.printf("Enter value for %s: ", variableName);
            value = scanner.nextDouble();

        	}

      
        consume(Token.Type.SEMICOLON);
    }

    private void assignment() {
        Token variableToken = consume(Token.Type.IDENTIFIER);
        String variableName = variableToken.lexeme;

        consume(Token.Type.ASSIGN);

        double value = expr();

        variables.put(variableName, value);
// System.out.println(value); //per shmb x=2+5
        consume(Token.Type.SEMICOLON);
    }

    private double expr() {
        double value = term();

        while (position < tokens.size()) {
            Token token = peek();

            switch (token.type) {
                case PLUS:
                    consume(Token.Type.PLUS);
                    value += term();
                    break;
                case MINUS:
                    consume(Token.Type.MINUS);
                    value -= term();
                    break;
                default:
                    return value;
            }
        }

        return value;
    }

    private double term() {
        double value = factor();

        while (position < tokens.size()) {
            Token token = peek();

            switch (token.type) {
                case MULTIPLY:
                    consume(Token.Type.MULTIPLY);
                    value *= factor();
                    break;
                case DIVIDE:
                    consume(Token.Type.DIVIDE);
                    value /= factor();
                    break;
                default:
                    return value;
            }
        }

        return value;
    }

    private double factor() {
        Token token = consume(Token.Type.NUMBER, Token.Type.IDENTIFIER, Token.Type.LPAREN);

        switch (token.type) {
            case NUMBER:
                return Double.parseDouble(token.lexeme);
            case IDENTIFIER:
                String variableName = token.lexeme;
                if (variables.containsKey(variableName)) {
                    return variables.get(variableName);
                } else {
                    throw new RuntimeException("Variable " + variableName + " not defined");
                }
            case LPAREN:
               double value = expr();
                
               consume(Token.Type.RPAREN);
               Token token2= consume(Token.Type.DIVIDE,Token.Type.MINUS,Token.Type.PLUS,Token.Type.MULTIPLY);
               switch(token2.type) {
               
                case DIVIDE:
                
                	value=value/term();
                	return value;
                	
                case MINUS:
                 
                	value=value-expr();
                	return value;
               
                case PLUS:
                 	
                	value=value+expr();
                	return value;
                
                case MULTIPLY:
                
                	value=value*term();
                	return value;
                  }
               
//               value=value+expr();
             
               
              
                
            default:
                throw new RuntimeException("Invalid token at position " + position);
        }
    }

    private Token consume(Token.Type... expectedTypes) {
        Token token = peek();
        for (Token.Type expectedType : expectedTypes) {
            if (token.type == expectedType) {
                position++;
                return token;
            }
        }
        throw new RuntimeException("Expected one of " + Arrays.toString(expectedTypes) + " at position " + position + ", found " + token.type);
    }
    private Token peek() {
        if (position >= tokens.size()) {
            throw new RuntimeException("Unexpected end of input");
        }

        return tokens.get(position);
    }
}
 
    
