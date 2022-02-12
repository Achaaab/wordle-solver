package com.github.achaaab.wordle.solver;

/**
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

		var game = new Wordle();
		var algorithm = new TaresAlgorithm();
		var dictionary = new Dictionary("allowed_words.txt");
		var candidates = dictionary.getWords();

		var before = System.currentTimeMillis();
		var bestGuess = algorithm.findBestGuess(game, candidates).orElseThrow();
		var after = System.currentTimeMillis();

		System.out.println(bestGuess + " found in " + (after - before) + "ms");
	}
}