package com.github.achaaab.wordle.solver;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

/**
 * proof-test with the simplest guess game
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

		var candidates = range(0, 101).boxed().collect(toSet());
		var bestGuess = algorithm.findBestGuess(moreLess, candidates).orElseThrow();

		System.out.println(bestGuess);
	}
}