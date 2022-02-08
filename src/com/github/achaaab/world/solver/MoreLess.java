package com.github.achaaab.world.solver;

/**
 * The simplest guess game imaginable.
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