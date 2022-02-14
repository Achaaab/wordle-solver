package com.github.achaaab.wordle.solver;

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

		solutionScore = exactWeight - 1;
	}

	@Override
	public Integer getScore(String candidate, String solution) {

		var candidateLetters = candidate.toCharArray();
		var solutionLetters = solution.toCharArray();

		var score = 0;

		for (var position = 0; position < length; position++) {

			if (candidateLetters[position] == solutionLetters[position]) {

				score += exactWeights[position];
				candidateLetters[position] = SCORED;
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

	/**
	 * @return score obtained when finding the solution
	 * @since 0.0.0
	 */
	public int getSolutionScore() {
		return solutionScore;
	}
}