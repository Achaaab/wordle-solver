package com.github.com.achaaab.wordle.solver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.github.com.achaaab.wordle.solver.MoreLess.EQUAL;
import static com.github.com.achaaab.wordle.solver.MoreLess.GREATER;
import static com.github.com.achaaab.wordle.solver.MoreLess.LESSER;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * unit tests of {@link MoreLess}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
class TestMoreLess {

	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 25, 49 })
	void testGetScoreMore(int guess) {

		var moreLess = new MoreLess();
		assertEquals(GREATER, moreLess.getScore(guess, 50));
	}

	@ParameterizedTest
	@ValueSource(ints = { 51, 75, 99, 100 })
	void testGetScoreLess(int guess) {

		var moreLess = new MoreLess();
		assertEquals(LESSER, moreLess.getScore(guess, 50));
	}

	@Test
	void testGetScoreEqual() {

		var moreLess = new MoreLess();
		assertEquals(EQUAL, moreLess.getScore(50, 50));
	}
}