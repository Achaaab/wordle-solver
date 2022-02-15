package com.github.com.achaaab.wordle.solver;

import com.github.achaaab.wordle.solver.Dictionary;
import com.github.achaaab.wordle.solver.GuessAlgorithm;
import com.github.achaaab.wordle.solver.SoareAlgorithm;
import com.github.achaaab.wordle.solver.Wordle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static java.lang.Double.NaN;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.IntStream.range;

/**
 * wordle benchmark aiming to measure the average guess count for an algorithm
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class BenchWordle {

	private static final Random RANDOM = new Random();

	/**
	 * Runs several rounds and display the average number of guesses to find the solution.
	 *
	 * @param arguments none
	 * @since 0.0.0
	 */
	public static void main(String... arguments) {

		var bench = new BenchWordle();
		bench.runRounds(1000);
	}

	/**
	 * Prints a progress bar in the current line of the standard output, showing the current and total value.
	 *
	 * @param startTime start time in milliseconds
	 * @param total total value
	 * @param current current value
	 * @since 0.0.0
	 */
	private static void printProgress(long startTime, int total, int current) {

		String estimatedTimeLeftString;

		if (current == 0) {

			estimatedTimeLeftString = "N/A";

		} else {

			var currentTime = currentTimeMillis();
			var estimatedTimeLeft = (currentTime - startTime) * (total - current) / current;

			estimatedTimeLeftString = format("%02d:%02d:%02d",
					MILLISECONDS.toHours(estimatedTimeLeft),
					MILLISECONDS.toMinutes(estimatedTimeLeft) % HOURS.toMinutes(1),
					MILLISECONDS.toSeconds(estimatedTimeLeft) % MINUTES.toSeconds(1));
		}

		var percent = current * 100 / total;
		var percentString = percent + "%";
		var percentLength = percentString.length();
		var currentString = Integer.toString(current);
		var currentLength = currentString.length();
		var totalString = Integer.toString(total);
		var totalLength = totalString.length();

		out.print('\r');

		out.print(" ".repeat(4 - percentLength) + percentString +
				" [" + "#".repeat(percent) + " ".repeat(100 - percent) + "] " +
				" ".repeat(totalLength - currentLength) + currentString + "/" + totalString +
				", ETA: " + estimatedTimeLeftString);
	}

	private final Wordle wordle;
	private final GuessAlgorithm algorithm;
	private final Set<String> candidates;
	private final Set<String> guesses;
	private final int solutionScore;
	private final String bestFirstGuess;
	private final List<String> candidateList;

	/**
	 * Creates a new benchmark.
	 *
	 * @since 0.0.0
	 */
	public BenchWordle() {

		algorithm = new SoareAlgorithm();

		var candidateDictionary = new Dictionary("possible_words.txt");
		var guessDictionary = new Dictionary("allowed_words.txt");

		candidates = candidateDictionary.getWords();
		guesses = guessDictionary.getWords();

		var wordLength = candidateDictionary.getWordLength();
		wordle = new Wordle(wordLength);
		solutionScore = wordle.getSolutionScore();

		bestFirstGuess = algorithm.findBestGuess(wordle, guesses, candidates).orElseThrow();
		candidateList = new ArrayList<>(candidates);
	}

	/**
	 * Run {@code count} rounds.
	 *
	 * @param count number of rounds to run
	 * @since 0.0.0
	 */
	public void runRounds(int count) {

		var startTime = currentTimeMillis();

		var averageGuessCount = range(0, count).
				peek(index -> printProgress(startTime, count, index + 1)).
				map(this::runRound).
				average().orElse(NaN);

		out.println("\naverage guess count after " + count + " rounds: " + averageGuessCount);
	}

	/**
	 * Runs 1 round.
	 *
	 * @param index round index
	 * @return number of guesses made to find the solution
	 * @since 0.0.0
	 */
	private int runRound(int index) {

		var candidateCount = candidateList.size();

		var solution = candidateList.get(RANDOM.nextInt(candidateCount));
		var roundCandidates = new HashSet<>(candidates);

		var guessCount = 0;
		var score = 0;

		do {

			var bestGuess = guessCount == 0 ?
					bestFirstGuess :
					algorithm.findBestGuess(wordle, guesses, roundCandidates).orElseThrow();

			score = wordle.getScore(bestGuess, solution);

			guessCount++;

			if (score != solutionScore) {
				algorithm.eliminateCandidates(wordle, roundCandidates, bestGuess, score);
			}

		} while (score != solutionScore);

		return guessCount;
	}
}