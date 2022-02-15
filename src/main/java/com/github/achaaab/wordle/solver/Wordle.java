package com.github.achaaab.wordle.solver;

/**
 * Wordle is a guess game where the goal is to find a 5-letter word with consecutive guesses which are also
 * 5-letter words.
 *
 * In the real game, scoring is a sequence of colors: 1 color per letter, from left to right.
 * <ul>
 *     <li>green: this letter is in the word, at this position</li>
 *     <li>yellow: this letter is in the word but misplaced</li>
 *     <li>gray: this letter is not in the word</li>
 * </ul>
 * In this implementation, for the sake of performance, we represent those sequences of colors with a base-3 number:
 * <ul>
 *     <li>{@code [gray, gray, gray, gray, gray]} is represented by 00000<sub>3</sub> = 0<sub>10</sub></li>
 *     <li>{@code [gray, gray, gray, gray, yellow]} is represented by 00001<sub>3</sub> = 1<sub>10</sub></li>
 *     <li>{@code [gray, gray, gray, gray, green]} is represented by 00002<sub>3</sub> = 2<sub>10</sub></li>
 *     <li>{@code [gray, gray, gray, yellow, gray]} is represented by 00010<sub>3</sub> = 3<sub>10</sub></li>
 *     <li>{@code [gray, gray, gray, yellow, green]} is represented by 00012<sub>3</sub> = 5<sub>10</sub></li>
 *     <li>{@code [green, gray, gray, green, yellow]} is represented by 20021<sub>3</sub> = 170<sub>10</sub></li>
 *     <li>{@code [green, green, green, green, green]} is represented by 22222<sub>3</sub> = 242<sub>10</sub></li>
 *     <li>...</li>
 * </ul>
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Wordle implements GuessGame<String, Integer> {

	public static final int MISPLACED = 1;
	public static final int EXACT = 2;
	public static final int SCORE_BASE = 3;

	private static final int SCORED = 0;

	private final int length;
	private final int[] exactWeights;
	private final int[] misplacedWeights;
	private final int solutionScore;

	/**
	 * Creates a new wordle game for a fixed word length.
	 *
	 * @param length number of letters of words in this game
	 * @since 0.0.0
	 */
	public Wordle(int length) {

		this.length = length;

		// optimization: we pre-compute the weight of EXACT and MISPLACED scores for each position

		exactWeights = new int[length];
		misplacedWeights = new int[length];

		var exactWeight = EXACT;
		var misplacedWeight = MISPLACED;

		var position = length;

		while (position-- > 0) {

			exactWeights[position] = exactWeight;
			misplacedWeights[position] = misplacedWeight;

			exactWeight *= SCORE_BASE;
			misplacedWeight *= SCORE_BASE;
		}

		solutionScore = misplacedWeight - 1;
	}

	@Override
	public Integer getScore(String guess, String solution) {

		var candidateLetters = guess.toCharArray();
		var solutionLetters = solution.toCharArray();

		var score = 0;

		for (var position = 0; position < length; position++) {

			if (candidateLetters[position] == solutionLetters[position]) {

				score += exactWeights[position];
				candidateLetters[position] = SCORED;
				solutionLetters[position] = SCORED;
			}
		}

		for (var position = 0; position < length; position++) {

			if (candidateLetters[position] != SCORED) {

				for (var solutionPosition = 0; solutionPosition < length; solutionPosition++) {

					if (candidateLetters[position] == solutionLetters[solutionPosition]) {

						score += misplacedWeights[position];
						solutionLetters[solutionPosition] = SCORED;
						break;
					}
				}
			}
		}

		return score;
	}

	@Override
	public Integer getSolutionScore() {
		return solutionScore;
	}
}