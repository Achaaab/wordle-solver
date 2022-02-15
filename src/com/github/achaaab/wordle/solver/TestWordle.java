package com.github.achaaab.wordle.solver;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

/**
 * This test is a {@link TaresAlgorithm} validation with {@link Wordle}, we expect to get "tares" as a best guess.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TestWordle {

	/**
	 * Runs a simple test using allowed words in wordle and the simplest algorithm.
	 *
	 * @param arguments none
	 * @since 0.0.0
	 */
	public static void main(String... arguments) {

		var dictionary = new Dictionary("allowed_words.txt");
		var wordLength = dictionary.getWordLength();
		var candidates = dictionary.getWords();
		out.println(candidates.size() + " word(s) in the dictionary");

		var game = new Wordle(wordLength);
		var algorithm = new TaresAlgorithm();

		var before = currentTimeMillis();
		var bestGuess = algorithm.findBestGuess(game, candidates).orElseThrow();
		var after = currentTimeMillis();

		out.println(bestGuess + " found in " + (after - before) + "ms");
	}
}