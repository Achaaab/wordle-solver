package com.github.achaaab.wordle.solver;

/**
 * A guess game is a game where the goal is to find a solution among candidates with successive guesses.
 * Each guess is rewarded with a score. A game has a known solution score.
 * If the guess is not a solution, the score is not equal to the solution score.
 * If the guess is a solution, the score is equal to the solution score.
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

	/**
	 * The solution score is the score when the guess is equal to the solution, meaning the game is ended.
	 *
	 * @return solution score
	 * @since 0.0.0
	 */
	S getSolutionScore();
}