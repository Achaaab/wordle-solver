package com.github.achaaab.wordle.solver;

import java.util.Optional;
import java.util.Set;

/**
 * algorithm to find the best guess in a guess game
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface GuessAlgorithm {

	/**
	 * Finds the best guess amongst remaining candidates.
	 *
	 * @param game game in which to guess
	 * @param candidates remaining candidates
	 * @param <C> type of the candidates
	 * @param <S> type of the scores
	 * @return best guess found
	 * @since 0.0.0
	 */
	<C, S> Optional<C> findBestGuess(GuessGame<C, S> game, Set<C> candidates);
}