package com.github.achaaab.wordle.solver;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.Math.log;
import static java.util.Comparator.comparingDouble;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * This implementation is inspired by the following video:
 * <a href="https://www.youtube.com/watch?v=v68zYyaEmEA">Solving Wordle using information theory</a>
 *
 * This algorithm does not use the fact that some candidates could have a higher probability to be solution than others.
 * The name of this algorithm comes from the best first guess it finds with official wordle guess game.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class SoareAlgorithm implements GuessAlgorithm {

	private static final double LOG_2 = log(2);

	/**
	 * Computes the base 2 logarithm of the given number.
	 *
	 * @param number any number
	 * @return base 2 logarithm of the given number
	 * @since 0.0.0
	 */
	private static double log2(double number) {
		return log(number) / LOG_2;
	}

	@Override
	public <C, S> Optional<C> findBestGuess(GuessGame<C, S> game, Set<C> guesses, Set<C> candidates) {

		/*
		Here, we use some memoization. Otherwise the expected information of candidates will be computed several 
		times when seeking the candidate with maximum expected information.
		*/

		var expectedInformation = guesses.parallelStream().collect(toMap(identity(),
				guess -> getExpectedInformation(game, guess, candidates)));

		/*
		Pick the guess with the maximum expected information.
		In case of equality, pick the guess which is a candidate.
		 */

		return guesses.stream().max(comparingDouble(expectedInformation::get).
				thenComparing(candidates::contains));
	}

	/**
	 * Evaluates the expected information given by a candidate.
	 *
	 * @param game guess game to play
	 * @param guess guess to evaluate
	 * @param candidates candidates left
	 * @return expected information in bits
	 * @since 0.0.0
	 */
	private <C, S> double getExpectedInformation(GuessGame<C, S> game, C guess, Set<C> candidates) {

		var candidateCount = candidates.size();

		var candidatesByScore = candidates.stream().collect(
				groupingBy(candidate -> game.getScore(guess, candidate)));

		return candidatesByScore.values().stream(). // iterate over solutions having the same score
				mapToDouble(List::size). // count them
				map(count -> count / candidateCount). // compute probability of this score
				map(probability -> probability * -log2(probability)). // compute p * log2(1 / p)
				sum();
	}
}