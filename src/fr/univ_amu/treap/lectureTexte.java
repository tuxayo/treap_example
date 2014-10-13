package projet.application;

import java.util.Scanner;
import java.util.regex.Pattern;

public class lectureTexte {
	
	public static void main(String[] args) {
		readTextFromStdIn();
	}

	private static void readTextFromStdIn() {
		Pattern delim = Pattern.compile("[\\W_]",
				Pattern.UNICODE_CHARACTER_CLASS);
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter(delim);

		String word = new String();
		while (scan.hasNext()) {
			word = scan.next();
			if(word.length() != 0) {
				
				word = word.toLowerCase();
				
				addToTree(word);
			}
		}
		
		scan.close();
	}

	private static void addToTree(String word) {
		System.out.println(word);
	}
}
