package com.github.achaaab.wordle.solver;

import java.util.Scanner;

import static com.github.achaaab.wordle.solver.Wordle.SCORE_BASE;
import static java.lang.Integer.parseInt;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

/**
 * Minimalist, yet interactive wordle solver. It requires score input as a string of 5 digits, from left to right:
 * <ul>
 *     <li>0 means gray = not in the word</li>
 *     <li>1 means yellow = misplaced</li>
 *     <li>2 means green = exact</li>
 * </ul>
 * For example:
 * 11020 means that the first 2 letters are misplaced and the fourth is exact, the third and the fifth letters are not
 * in the word.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class WordleSolver {

	/**
	 * Runs the interactive console solver.
	 *
	 * @param arguments none
	 * @since 0.0.0
	 */
	public static void main(String... arguments) {

		var scanner = new Scanner(System.in);

		var algorithm = new SoareAlgorithm();

		var possibleWords = new Dictionary("possible_words.txt");
		var allowedWords = new Dictionary("allowed_words.txt");

		var candidates = possibleWords.getWords();
		var guesses = allowedWords.getWords();

		var wordLength = possibleWords.getWordLength();
		var wordle = new Wordle(wordLength);
		var solutionScore = wordle.getSolutionScore();

		int score;

		do {

			out.println("================================");

			var before = currentTimeMillis();
			var bestGuess = algorithm.findBestGuess(wordle, guesses, candidates).orElseThrow();
			var after = currentTimeMillis();

			out.println(bestGuess.toUpperCase() + " found in " + (after - before) + "ms");

			out.print("score? ");
			score = parseInt(scanner.next(), SCORE_BASE);
			algorithm.eliminateCandidates(wordle, candidates, bestGuess, score);
			out.println(candidates.size() + " candidate(s) left");

			if (candidates.size() < 20) {
				out.println(candidates);
			}

		} while (score != solutionScore);
	}
}