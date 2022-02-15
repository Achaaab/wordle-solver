package com.github.com.achaaab.wordle.solver;

import com.github.achaaab.wordle.solver.SoareAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static com.github.com.achaaab.wordle.solver.MoreLess.GREATER;
import static com.github.com.achaaab.wordle.solver.MoreLess.LESSER;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * unit tests of {@link SoareAlgorithm}
 * 
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
class TestSoareAlgorithm {

	@Test
	void testFindBestGuess() {

		// simulates solution = 49

		var game = new MoreLess();
		var algorithm = new SoareAlgorithm();
		var candidates = rangeClosed(0, 62).boxed().collect(toSet());
		var guesses = new HashSet<>(candidates);

		var guess = algorithm.findBestGuess(game, guesses, candidates).orElseThrow();
		assertEquals(31, guess);
		algorithm.eliminateCandidates(game, candidates, guess, GREATER);

		guess = algorithm.findBestGuess(game, guesses, candidates).orElseThrow();
		assertEquals(47, guess);
		algorithm.eliminateCandidates(game, candidates, guess, GREATER);

		guess = algorithm.findBestGuess(game, guesses, candidates).orElseThrow();
		assertEquals(55, guess);
		algorithm.eliminateCandidates(game, candidates, guess, LESSER);

		guess = algorithm.findBestGuess(game, guesses, candidates).orElseThrow();
		assertEquals(51, guess);
		algorithm.eliminateCandidates(game, candidates, guess, LESSER);

		guess = algorithm.findBestGuess(game, guesses, candidates).orElseThrow();
		assertEquals(49, guess);
	}
}
