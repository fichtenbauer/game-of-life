# Game of Life

This exercise was given as a hands-on example in the online course "Software Design with Unit Tests"
by [Dr. Venkat Subramaniam][03], [O'Reilly Media][01]. 


## Rules

The universe of the Game of Life is an infinite, two-dimensional orthogonal grid of square cells, 
each of which is in one of two possible states, live or dead (or populated and unpopulated, respectively). 
Every cell interacts with its eight neighbors, which are the cells that are horizontally, vertically, 
or diagonally adjacent. At each step in time, the following transitions occur:

1. Any live cell with fewer than two live neighbors dies, as if by underpopulation.
2. Any live cell with two or three live neighbors lives on to the next generation.
3. Any live cell with more than three live neighbors dies, as if by overpopulation.
4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.

The initial pattern constitutes the seed of the system. The first generation is created 
by applying the above rules simultaneously to every cell in the seed, live or dead; births 
and deaths occur simultaneously, and the discrete moment at which this happens is sometimes 
called a tick. Each generation is a pure function of the preceding one. The rules continue 
to be applied repeatedly to create further generations.

Retrieved from: [Wikipedia: Conway's Game of Life][02]

## System Requirements
* Java 17 or higher
* Maven 3.8.x or higher

## Executing program

```
mvn clean test
```

## Branches

* **imperative-limited**
    * imperative style 
    * limited universe, that means, the max dimensions of the universe are defined at startup and can not change at runtime
* **imperative-unlimited**
    * imperative style
    * unlimited universe
* **main**
    * functional style
    * unlimited universe

## License

GNU General Public License, Version 3, 29 June 2007



[01]: https://www.oreilly.com/online-learning/
[02]: https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
[03]: https://agiledeveloper.com/aboutus.html