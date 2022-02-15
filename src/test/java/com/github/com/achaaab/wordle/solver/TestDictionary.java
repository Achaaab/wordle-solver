package com.github.com.achaaab.wordle.solver;

import com.github.achaaab.wordle.solver.Dictionary;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * unit tests of {@link Dictionary}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
class TestDictionary {

	@Test
	void testGetWords() {

		var dictionary = new Dictionary("days_of_week.txt");
		var words = dictionary.getWords();

		assertEquals(
				Set.of("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"),
				words);
	}

	@Test
	void testGetWordsEmpty() {

		var dictionary = new Dictionary("empty.txt");
		var words = dictionary.getWords();

		assertTrue(words.isEmpty());
	}

	@Test
	void testNotFound() {
		assertThrows(NullPointerException.class, () -> new Dictionary("non_existent_dictionary.txt"));
	}

	@Test
	void testGetWordLengthEmpty() {

		var dictionary = new Dictionary("empty.txt");
		assertThrows(RuntimeException.class, dictionary::getWordLength);
	}

	@Test
	void testGetWordLength7() {

		var dictionary = new Dictionary("7_letter_words.txt");
		assertEquals(7, dictionary.getWordLength());
	}

	@Test
	void testGetWordLengthDifferent() {

		var dictionary = new Dictionary("days_of_week.txt");
		assertThrows(RuntimeException.class, dictionary::getWordLength);
	}
}