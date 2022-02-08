package com.github.achaaab.world.solver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

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

		var candidates = loadWords("allowed_words.txt");
		var bestGuess = algorithm.findBestGuess(game, candidates).orElseThrow();

		System.out.println(bestGuess);
	}

	/**
	 * Loads a set of words from a resource containing 1 word per line.
	 *
	 * @param resourceName name of the resource from which to load words
	 * @return loaded words
	 * @since 0.0.0
	 */
	private static Set<String> loadWords(String resourceName) {

		var stream = getSystemResourceAsStream(resourceName);
		requireNonNull(stream);

		var reader = new BufferedReader(new InputStreamReader(stream));

		return reader.lines().collect(toSet());
	}
}