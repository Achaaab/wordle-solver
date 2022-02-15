package com.github.com.achaaab.wordle.solver;

import com.github.achaaab.wordle.solver.Wordle;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * unit tests of {@link Wordle}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
class TestWordle {

	@Test
	void testGetSolutionScore() {

		var wordle = new Wordle(1);
		assertEquals(2, wordle.getSolutionScore());

		wordle = new Wordle(2);
		assertEquals(8, wordle.getSolutionScore());

		wordle = new Wordle(5);
		assertEquals(242, wordle.getSolutionScore());
	}

	@Test
	void testGetScore1() {

		var wordle = new Wordle(1);

		assertEquals(0, wordle.getScore("A", "B"));
		assertEquals(2, wordle.getScore("B", "B"));
	}

	@Test
	void testGetScore2() {

		var wordle = new Wordle(2);

		assertEquals(0, wordle.getScore("AB", "CD"));
		assertEquals(1, wordle.getScore("AC", "CD"));
		assertEquals(2, wordle.getScore("AD", "CD"));
		assertEquals(3, wordle.getScore("DA", "CD"));
		assertEquals(6, wordle.getScore("CA", "CD"));
		assertEquals(8, wordle.getScore("CD", "CD"));

		assertEquals(0, wordle.getScore("AA", "CD"));
		assertEquals(2, wordle.getScore("DD", "CD"));
		assertEquals(6, wordle.getScore("CC", "CD"));
		assertEquals(2, wordle.getScore("AC", "CC"));
		assertEquals(6, wordle.getScore("CA", "CC"));
	}

	@Test
	void testGetScore5() {

		var wordle = new Wordle(5);

		assertEquals(parseInt("21102", 3), wordle.getScore("CRANE", "CARRE"));
		assertEquals(parseInt("22222", 3), wordle.getScore("CARRE", "CARRE"));
		assertEquals(parseInt("00000", 3), wordle.getScore("MONTH", "CARRE"));
	}
}