# Wordle solver

![build](https://github.com/Achaaab/wordle-solver/workflows/build/badge.svg)
![Coverage](.github/badges/jacoco.svg)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

## Description
This project aims to provide a <ins>readable</ins>, <ins>efficient</ins> and <ins>reusable</ins> way of solving the
Wordle game. The current algorithm is based on the following [video](https://www.youtube.com/watch?v=v68zYyaEmEA)
from 3Blue1Brown channel. The implementation is short enough (about 10 lines of code). Surprisingly enough,
Wordle scoring function is a bit longer. It finds "SOARE" as the best first guess in about 1 second on an mid-range CPU
in 2022 and takes less than 3.5 guesses in average to find the solution. It can be easily reused for other guessing
games such as [Mastermind](https://en.wikipedia.org/wiki/Mastermind_(board_game)),
[Hangman](https://en.wikipedia.org/wiki/Hangman_(game)) or [Guess who?](https://en.wikipedia.org/wiki/Guess_Who%3F).

## Getting Started
These instructions will get you a copy of the project up and running on your local machine
for development and testing purposes.

### Prerequisites
* Java 16
* Maven 3.6.1
* Git 2.17.1

### Installing
* Clone the repository from GitHub:
```shell
git clone https://github.com/Achaaab/wordle-solver.git
```
* Build the project with Maven:
```shell
cd wordle-solver
mvn package
```

### Running
```shell
java -jar target/wordle-solver-x.y.z.jar
```

### Example
```
========================================================================================================================
SOARE found in 1277ms
score? 01110
27 candidates left
razor, arrow, groan, cargo, arbor, manor, major, croak, organ, baron, armor, radio, rayon, labor, valor, ratio, ...
========================================================================================================================
MARON found in 63ms
score? 11110
1 candidate left
aroma
========================================================================================================================
AROMA found in 46ms
score? 22222
```

## Improvements
* Try to improve Wordle scoring time with memoization without using too much memory.
* Computes expected information with depth > 1. It seems that "SOARE" is only the 14<sup>th</sup> best guess
  with a depth of 2.

## Contributing
I will gladly consider pull requests until Spring 2022. After this date, I could have moved on something else.

## Authors
* **Jonathan Guéhenneux** - *Developer* - [Achaaab](https://github.com/Achaaab)

## License
This project is licensed under the GNU General Public License (GPL) - see the [LICENSE.md](LICENSE.md) for the details.

## Acknowledgments
I thank:
* [**Grant Sanderson**](https://www.youtube.com/c/3blue1brown) for his interesting videos
  [here](https://www.youtube.com/watch?v=v68zYyaEmEA) and [here](https://www.youtube.com/watch?v=fRed0Xmc2Wg)
  about Wordle and information theory.
