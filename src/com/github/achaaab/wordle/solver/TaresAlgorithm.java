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
 * initial idea from <a href="https://www.youtube.com/watch?v=v68zYyaEmEA">Solving Wordle using information theory</a>
 * <p>
 * This algorithm does not use the fact than only a fraction of accepted words are actually candidates.
 * It does not use either the fact that some words may be more frequently picked than others.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class TaresAlgorithm implements GuessAlgorithm {

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

	/**
	 * Evaluates the expected information given by a candidate.
	 *
	 * @param game guess game to play
	 * @param candidate candidate to evaluate
	 * @param candidates all candidates left
	 * @return expected information in bits
	 * @since 0.0.0
	 */
	private <C, S> double getExpectedInformation(GuessGame<C, S> game, C candidate, Set<C> candidates) {

		var candidateCount = candidates.size();

		var solutionsByScore = candidates.stream().collect(
				groupingBy(solution -> game.getScore(candidate, solution)));

		var expectedInformation = solutionsByScore.values().stream(). // iterate over solutions having the same score
				mapToDouble(List::size). // count them
				map(count -> count / candidateCount). // compute probability of this score
				map(probability -> probability * log2(1 / probability)). // compute p * log2(1 / p)
				sum();

		// System.out.println(candidate + ": " + expectedInformation);

		return expectedInformation;
	}

	@Override
	public <C, S> Optional<C> findBestGuess(GuessGame<C, S> game, Set<C> candidates) {

		var expectedInformations = candidates.stream().collect(toMap(
				identity(),
				candidate -> getExpectedInformation(game, candidate, candidates)));

		return candidates.stream().max(comparingDouble(expectedInformations::get));
	}
}