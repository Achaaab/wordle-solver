package com.github.achaaab.wordle.solver;

/**
 * A very simple guess game, for validation purpose. Scoring:
 * <ul>
 *    <li>'-': the solution is lesser than the guess</li>
 *    <li>'=': the solution is equal to the guess</li>
 *    <li>'+': the solution is greater than the guess</li>
 * </ul>
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class MoreLess implements GuessGame<Integer, Character> {

	@Override
	public Character getScore(Integer candidate, Integer solution) {

		char score;

		if (candidate.equals(solution)) {
			score = '=';
		} else if (candidate < solution) {
			score = '+';
		} else {
			score = '-';
		}

		return score;
	}
}