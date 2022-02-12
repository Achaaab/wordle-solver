package com.github.achaaab.wordle.solver;

import static java.lang.Math.pow;

/**
 * wordle guess game
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Wordle implements GuessGame<String, Integer> {

	public static final int MISPLACED = 1;
	public static final int EXACT = 2;
	public static final int SCORE_BASE = 3;

	private static final int SCORED = 0;

	@Override
	public Integer getScore(String candidate, String solution) {

		var length = candidate.length();

		var candidateLetters = candidate.toCharArray();
		var solutionLetters = solution.toCharArray();

		var greenScore = 0;

		for (var position = 0; position < length; position++) {

			greenScore *= SCORE_BASE;

			if (candidateLetters[position] == solutionLetters[position]) {

				greenScore += EXACT;
				candidateLetters[position] = SCORED;
				solutionLetters[position] = SCORED;
			}
		}

		var yellowScore = 0;

		for (var position = 0; position < length; position++) {

			yellowScore *= SCORE_BASE;

			if (candidateLetters[position] != SCORED) {

				for (var solutionPosition = 0; solutionPosition < length; solutionPosition++) {

					if (solutionLetters[solutionPosition] != SCORED &&
							candidateLetters[position] == solutionLetters[solutionPosition]) {

						yellowScore += MISPLACED;
						solutionLetters[solutionPosition] = SCORED;
						break;
					}
				}
			}
		}

		return yellowScore + greenScore;
	}

	/**
	 * @param wordLength word length
	 * @return score obtained when finding the solution
	 * @since 0.0.0
	 */
	public int getSolutionScore(int wordLength) {
		return (int) pow(SCORE_BASE, wordLength) - 1;
	}
}