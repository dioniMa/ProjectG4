package Update;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Main {
 public static void main(String[] args) throws Exception {
		 
		 Scanner sc=new Scanner(System.in);
		  System.out.print("Enter file path: ");
	        String filePath = sc.nextLine();
	        List<String>values= new ArrayList<>();
	        
	        List<String> expressions = new ArrayList<>();
	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                expressions.add(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        for (String expression : expressions) {
	            Tokenizer tokenizer = new Tokenizer(new StringReader(expression));
	            List<Token> tokens = tokenizer.tokenize();
	            Parser parser = new Parser(tokens);
	            parser.parse();
	            
	            	
	          
	        }
	    }
}
