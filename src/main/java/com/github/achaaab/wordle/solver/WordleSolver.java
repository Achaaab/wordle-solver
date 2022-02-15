package com.github.achaaab.wordle.solver;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static com.github.achaaab.wordle.solver.Wordle.SCORE_BASE;
import static java.lang.Integer.parseInt;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.exit;
import static java.lang.System.in;
import static java.lang.System.out;
import static java.util.stream.Collectors.joining;

/**
 * Minimalist, yet interactive wordle solver. It requires score input as a string of 5 digits, from left to right:
 * <ul>
 *     <li>0 means gray = not in the word</li>
 *     <li>1 means yellow = misplaced</li>
 *     <li>2 means green = exact</li>
 * </ul>
 * For example:
 * 11020 means that the first 2 letters are misplaced and the fourth is exact, the third and the fifth letters are not
 * in the word.
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class WordleSolver {

	private static final int MAXIMUM_PRINTED_CANDIDATE_COUNT = 16;
	private static final int GUESS_SEPARATOR_LENGTH = 120;

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";

	/**
	 * Runs the interactive console solver.
	 *
	 * @param arguments none
	 * @since 0.0.0
	 */
	public static void main(String... arguments) {

		var solver = new WordleSolver();
		solver.solve();
	}

	private final Wordle wordle;
	private final Set<String> possibleWords;
	private final Set<String> guesses;
	private final int solutionScore;
	private final GuessAlgorithm algorithm;
	private final Scanner scanner;
	private final String guessSeparator;

	/**
	 * Creates a Wordle solver with limited console interaction.
	 *
	 * @since 0.0.0
	 */
	public WordleSolver() {

		var candidateDictionary = new Dictionary("possible_words.txt");
		var guessDictionary = new Dictionary("allowed_words.txt");

		possibleWords = candidateDictionary.getWords();
		guesses = guessDictionary.getWords();
		wordle = new Wordle(candidateDictionary.getWordLength());
		solutionScore = wordle.getSolutionScore();
		algorithm = new SoareAlgorithm();
		scanner = new Scanner(in);
		guessSeparator = "=".repeat(GUESS_SEPARATOR_LENGTH);
	}

	/**
	 * Solves 1 Wordle.
	 *
	 * @since 0.0.0
	 */
	public void solve() {

		var candidates = new HashSet<>(possibleWords);
		var candidateCount = candidates.size();

		int score;

		do {

			out.println(ANSI_BLUE + guessSeparator + ANSI_RESET);

			var before = currentTimeMillis();
			var bestGuess = algorithm.findBestGuess(wordle, guesses, candidates).orElseThrow();
			var after = currentTimeMillis();

			out.println(ANSI_BLUE + bestGuess.toUpperCase() + ANSI_RESET + " found in " + (after - before) + "ms");

			score = readScore();

			if (score != solutionScore) {

				algorithm.eliminateCandidates(wordle, candidates, bestGuess, score);
				candidateCount = candidates.size();
				out.println(candidateCount + " candidate" + (candidateCount >= 2 ? "s" : "") + " left");

				var ellipsis = candidateCount > MAXIMUM_PRINTED_CANDIDATE_COUNT;

				var printedCandidates = candidates.stream().
						limit(ellipsis ? MAXIMUM_PRINTED_CANDIDATE_COUNT : candidateCount).
						collect(joining(", ", "", ellipsis ? ", ..." : ""));

				out.println(printedCandidates);
			}

		} while (score != solutionScore && candidateCount > 0);

		if (candidateCount == 0) {

			out.println(ANSI_RED + "There is no candidate left." +
					" Check your scoring and make sure the solution is in the dictionary." + ANSI_RESET);
		}
	}

	/**
	 * Reads a valid Wordle score from the standard input. Prints scoring rules if a invalid score is entered.
	 * Loops until a valid score is entered.
	 * Exits with status {@code 1} if an unexpected error occurs while reading input.
	 *
	 * @return valid score entered
	 * @since 0.0.0
	 */
	private int readScore() {

		var score = -1;

		do {

			out.print("score? ");

			try {
				score = parseInt(scanner.next(), SCORE_BASE);
			} catch (NumberFormatException numberFormatException) {
				// we will display usage and retry...
			} catch (Exception exception) {
				exit(1);
			}

			if (score < 0 || score > solutionScore) {

				out.println(ANSI_YELLOW + "The score must be a string of 5 digits:");
				out.println(" - 0: the letter is not in the word in any spot");
				out.println(" - 1: the letter is in the word but in the wrong spot");
				out.println(" - 2: the letter is in the word and in the correct spot.");
				out.println("example: 01201" + ANSI_RESET);
			}

		} while (score < 0 || score > solutionScore);

		return score;
	}
}