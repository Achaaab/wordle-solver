package com.github.achaaab.wordle.solver;

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

		System.out.println(words.size() + " word(s) in the dictionary");
	}

	/**
	 * @return words contained in this dictionary
	 * @since 0.0.0
	 */
	public Set<String> getWords() {
		return words;
	}

	/**
	 * @return common word length in this dictionary
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