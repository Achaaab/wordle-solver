package com.github.achaaab.wordle.solver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

/**
 * set of words
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Dictionary {

	private final Set<String> words;

	/**
	 * Creates a new dictionary from a resource containing 1 word per line.
	 *
	 * @param resourceName name of the resource
	 * @since 0.0.0
	 */
	public Dictionary(String resourceName) {

		var stream = getSystemResourceAsStream(resourceName);
		requireNonNull(stream);

		var reader = new BufferedReader(new InputStreamReader(stream));

		words = reader.lines().collect(toSet());
	}

	/**
	 * @return words contained in this dictionary
	 * @since 0.0.0
	 */
	public Set<String> getWords() {
		return words;
	}

	/**
	 * Checks if every word in this dictionary has the same length and returns the common length.
	 *
	 * @return common word length in this dictionary
	 * @throws RuntimeException if the dictionary is empty or if words have different lengths
	 * @since 0.0.0
	 */
	public int getWordLength() {

		if (words.isEmpty()) {

			throw new RuntimeException("Cannot figure out the common word length " +
					"because there is no word in the dictionary.");
		}

		var lengths = words.stream().map(String::length).collect(toSet());

		if (lengths.size() > 1) {

			throw new RuntimeException("Cannot figure out the common word length " +
					"because there are different word lengths : " + lengths);
		}

		return lengths.iterator().next();
	}
}