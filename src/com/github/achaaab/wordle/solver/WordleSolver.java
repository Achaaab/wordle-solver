package com.github.achaaab.wordle.solver;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class WordleSolver {

	/**
	 * @param arguments none
	 * @since 0.0.0
	 */
	public static void main(String... arguments) {

		var scanner = new Scanner(System.in);

		var wordle = new Wordle();
		var algorithm = new TaresAlgorithm();

		var dictionary = new Dictionary("possible_words.txt");
		var candidates = dictionary.getWords();
		var wordLength = dictionary.getWordLength();
		var solutionScore = wordle.getSolutionScore(wordLength);

		int score;

		do {

			System.out.println("================================");

			var before = System.currentTimeMillis();
			var bestGuess = algorithm.findBestGuess(wordle, candidates).orElseThrow();
			var after = System.currentTimeMillis();

			System.out.println(bestGuess.toUpperCase() + " found in " + (after - before) + "ms");

			System.out.print("score ? ");
			score = parseInt(scanner.next(), 3);
			algorithm.eliminateCandidates(wordle, candidates, bestGuess, score);
			System.out.println(candidates.size() + " candidate(s) left");

		} while (score < solutionScore);
	}
}