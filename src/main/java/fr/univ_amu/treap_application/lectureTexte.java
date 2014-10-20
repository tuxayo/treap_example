package fr.univ_amu.treap_application;

import java.util.Scanner;
import java.util.regex.Pattern;

import fr.univ_amu.treap.Treap;

public class lectureTexte {

	public static void main(String[] args) {
		Treap<String, Integer> treap = new Treap<String, Integer>();
		System.out.println("Begin filling treap");
		readTextFromStdIn(treap);
		System.out.println("Number of unique words:");
		System.out.println(treap.countNodes());
	}

	private static void readTextFromStdIn(Treap<String, Integer> treap) {
		Pattern delim = Pattern.compile("[\\W_]",
				Pattern.UNICODE_CHARACTER_CLASS);
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter(delim);

		String word = new String();
		while (scan.hasNext()) {
			word = scan.next();
			if(word.length() != 0) {

				word = word.toLowerCase();

				addToTree(word, treap);
			}
		}
		scan.close();
	}



	private static void addToTree(String word, Treap<String, Integer> treap) {
		Integer result = treap.find(word);
		if(result == null) {
			treap.insert(word, 1);
		} else {
			treap.insert(word, result + 1);
		}
	}
}
