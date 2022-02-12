package com.github.achaaab.wordle.solver;

import java.util.Scanner;

import static com.github.achaaab.wordle.solver.Wordle.SCORE_BASE;
import static java.lang.Integer.parseInt;

/**
 * Minimalist, yet interactive wordle solver. It requires score input as a string of 5 digits, from left to right :
 * <ul>
 *     <li>0 means gray = not in the word</li>
 *     <li>1 means yellow = misplaced</li>
 *     <li>2 means green = exact</li>
 * </ul>
 * For example :
 * 11020 means that the first 2 letters are misplaced and the fourth is exact, the third and the fifth letters are not
 * in the word.
 *
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

		var dictionary = new Dictionary("allowed_words.txt");
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
			score = parseInt(scanner.next(), SCORE_BASE);
			algorithm.eliminateCandidates(wordle, candidates, bestGuess, score);
			System.out.println(candidates.size() + " candidate(s) left");

			if (candidates.size() < 20) {
				System.out.println(candidates);
			}

		} while (score < solutionScore);
	}
}