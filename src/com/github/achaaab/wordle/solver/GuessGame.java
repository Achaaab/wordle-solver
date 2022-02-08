package com.github.achaaab.wordle.solver;

/**
 * guess game pure abstraction
 *
 * @param <C> type of candidates
 * @param <S> type of scores
 * @since 0.0.0
 */
public interface GuessGame<C, S> {

	/**
	 * Evaluates a candidate against a solution.
	 *
	 * @param candidate candidate to evaluate
	 * @param solution solution, or presumed solution
	 * @return evaluated score
	 * @since 0.0.0
	 */
	S getScore(C candidate, C solution);
}