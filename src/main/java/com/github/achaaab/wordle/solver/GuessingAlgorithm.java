package com.github.achaaab.wordle.solver;

import java.util.Optional;
import java.util.Set;

/**
 * algorithm to find the best guess in a guessing game
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public interface GuessingAlgorithm {

	/**
	 * Finds the best guess amongst remaining candidates.
	 *
	 * @param game game in which to guess
	 * @param guesses acceptable guesses
	 * @param candidates remaining candidates
	 * @param <C> type of the guesses and candidates
	 * @param <S> type of the scores
	 * @return best guess found
	 * @since 0.0.0
	 */
	<C, S> Optional<C> findBestGuess(GuessingGame<C, S> game, Set<C> guesses, Set<C> candidates);

	/**
	 * Eliminates candidates that would not match the given score if they were the solution.
	 * That means they cannot be the solution.
	 *
	 * @param <C> type of the candidates
	 * @param <S> type of the scores
	 * @param game game to play
	 * @param candidates remaining candidates to filter
	 * @param guess played guess
	 * @param score score obtained by the guess
	 * @since 0.0.0
	 */
	default <C, S> void eliminateCandidates(GuessingGame<C, S> game, Set<C> candidates, C guess, S score) {

		candidates.removeIf(candidate ->
				!game.getScore(guess, candidate).equals(score));
	}
}