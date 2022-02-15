package com.github.achaaab.wordle.solver;

/**
 * A very simple guess game for the sake of validation, where the goal is to find a number with consecutive guesses.
 * Scoring:
 * <ul>
 *    <li>{@link #LESSER}: the solution is lesser than the guess</li>
 *    <li>{@link #EQUAL}: the solution is equal to the guess</li>
 *    <li>{@link #GREATER}: the solution is greater than the guess</li>
 * </ul>
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MoreLess implements GuessGame<Integer, Character> {

	public static final char LESSER = '-';
	public static final char EQUAL = '=';
	public static final char GREATER = '+';

	@Override
	public Character getScore(Integer candidate, Integer solution) {

		var score = EQUAL;

		if (solution < candidate) {
			score = LESSER;
		} else if (solution > candidate) {
			score = GREATER;
		}

		return score;
	}

	@Override
	public Character getSolutionScore() {
		return EQUAL;
	}
}