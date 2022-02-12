package com.github.achaaab.wordle.solver;

import static java.lang.Math.pow;

/**
 * wordle guess game
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Wordle implements GuessGame<String, Integer> {

	private static final int NOT_AT_THIS_POSITION = 1;
	private static final int AT_THIS_POSITION = 2;
	private static final int LETTER_SCORE_COUNT = 3;

	@Override
	public Integer getScore(String candidate, String solution) {

		var score = 0;
		var length = candidate.length();

		var solutionLetters = solution.toCharArray();

		for (var position = 0; position < length; position++) {

			score *= LETTER_SCORE_COUNT;

			var letter = candidate.charAt(position);

			if (solutionLetters[position] == letter) {

				score += AT_THIS_POSITION;
				solutionLetters[position] = 0;

			} else {

				var solutionPosition = 0;
				var found = false;

				while (solutionPosition < length && !found) {

					if (solutionLetters[solutionPosition] == letter) {

						score += NOT_AT_THIS_POSITION;
						solutionLetters[solutionPosition] = 0;
						found = true;
					}

					solutionPosition++;
				}
			}
		}

		return score;
	}

	/**
	 * @param wordLength word length
	 * @return score obtained when finding the solution
	 * @since 0.0.0
	 */
	public int getSolutionScore(int wordLength) {
		return (int) pow(LETTER_SCORE_COUNT, wordLength) - 1;
	}
}