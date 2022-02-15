# Wordle solver

![build](https://github.com/Achaaab/wordle-solver/workflows/build/badge.svg)
![Coverage](.github/badges/jacoco.svg)

## Description
This project tries to provide an efficient way of solving the popular game named Wordle.
The "guess" algorithm is quite generic and could be implemented for many "guess" games (Mastermind, Hangman, Guess who?, ...).
The current implementation uses an algorithm based on the following [video](https://www.youtube.com/watch?v=v68zYyaEmEA) from 3Blue1Brown.
It finds "SOARE" as a best first guess for Wordle game. In average it takes less than 3.5 guesses to find the solution.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine
for development and testing purposes.

### Prerequisites
* Java 16
* Maven 3.6.1
* Git 2.17.1

### Installing
* Clone the repository from github.com:
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
================================
SOARE found in 657ms
score? 00102
40 candidate(s) left
================================
GAULT found in 32ms
score? 12000
2 candidate(s) left
[mange, badge]
================================
BADGE found in 16ms
score? 22222
```

## Contributing
I will gladly consider pull requests until Spring 2022.
After this date, I could have moved on something else.

## Authors
* **Jonathan Guéhenneux** - *Developer* - [Achaaab](https://github.com/Achaaab)

## License
This project is licensed under the GNU General Public License (GPL) - see the [LICENSE.md](LICENSE.md) for the details.

## Acknowledgments
I thank:
* [**Grant Sanderson**](https://www.youtube.com/c/3blue1brown) for his interesting videos
  [here](https://www.youtube.com/watch?v=v68zYyaEmEA) and [here](https://www.youtube.com/watch?v=fRed0Xmc2Wg)
  about Worlde and information theory.