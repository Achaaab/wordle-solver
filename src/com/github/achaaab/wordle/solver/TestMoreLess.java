package com.github.achaaab.wordle.solver;

import static java.lang.System.out;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;

/**
 * {@link TaresAlgorithm} validation with {@link MoreLess} game
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TestMoreLess {

	/**
	 * Runs a simple test.
	 *
	 * @param arguments none
	 * @since 0.0.0
	 */
	public static void main(String... arguments) {

		var moreLess = new MoreLess();
		var algorithm = new TaresAlgorithm();

		var candidates = rangeClosed(0, 100).boxed().collect(toSet());
		var bestGuess = algorithm.findBestGuess(moreLess, candidates).orElseThrow();

		out.println(bestGuess);
	}
}