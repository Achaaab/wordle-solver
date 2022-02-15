package com.github.com.achaaab.wordle.solver;

import com.github.achaaab.wordle.solver.GuessAlgorithm;
import com.github.achaaab.wordle.solver.SoareAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.github.com.achaaab.wordle.solver.MoreLess.EQUAL;
import static com.github.com.achaaab.wordle.solver.MoreLess.GREATER;
import static com.github.com.achaaab.wordle.solver.MoreLess.LESSER;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * unit tests of {@link GuessAlgorithm}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
class TestGuessAlgorithm {

	@Test
	void testEliminateCandidates() {

		var game = new MoreLess();
		var algorithm = new SoareAlgorithm();
		var candidates = rangeClosed(0, 62).boxed().collect(toSet());

		algorithm.eliminateCandidates(game, candidates, 31, GREATER);
		assertEquals(rangeClosed(32, 62).boxed().collect(toSet()), candidates);

		algorithm.eliminateCandidates(game, candidates, 47, GREATER);
		assertEquals(rangeClosed(48, 62).boxed().collect(toSet()), candidates);

		algorithm.eliminateCandidates(game, candidates, 55, LESSER);
		assertEquals(Set.of(48, 49, 50, 51, 52, 53, 54), candidates);

		algorithm.eliminateCandidates(game, candidates, 51, LESSER);
		assertEquals(Set.of(48, 49, 50), candidates);

		algorithm.eliminateCandidates(game, candidates, 49, EQUAL);
		assertEquals(singleton(49), candidates);
	}
}