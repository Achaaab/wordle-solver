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

	/**
	 * Eliminates candidates that would not match the given score if they were the solution.
	 * That means they cannot be the solution.
	 *
	 * @param game game to play
	 * @param candidates remaining candidates to filter
	 * @param candidate played candidate
	 * @param score score obtained by the candidate
	 * @param <C> type of the candidates
	 * @param <S> type of the scores
	 * @since 0.0.0
	 */
	<C, S> void eliminateCandidates(GuessGame<C, S> game, Set<C> candidates, C candidate, S score);
}