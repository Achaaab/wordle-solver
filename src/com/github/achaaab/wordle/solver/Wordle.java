package com.github.achaaab.wordle.solver;

/**
 * wordle guess game
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Wordle implements GuessGame<String, Integer> {

	private static final int WORD_LENGTH = 5;

	private static final int NOT_AT_THIS_POSITION = 1;
	private static final int AT_THIS_POSITION = 2;
	private static final int LETTER_SCORE_COUNT = 3;

	@Override
	public Integer getScore(String candidate, String solution) {

		int score = 0;

		var candidateLetters = candidate.toCharArray();
		var solutionLetters = solution.toCharArray();

		for (var position = 0; position < WORD_LENGTH; position++) {

			score *= LETTER_SCORE_COUNT;

			var letter = candidateLetters[position];

			if (solutionLetters[position] == letter) {

				score += AT_THIS_POSITION;
				solutionLetters[position] = 0;

			} else {

				for (var solutionPosition = 0; solutionPosition < WORD_LENGTH; solutionPosition++) {

					if (solutionLetters[solutionPosition] == letter) {

						score += NOT_AT_THIS_POSITION;
						solutionLetters[solutionPosition] = 0;
					}
				}
			}
		}

		return score;
	}
}