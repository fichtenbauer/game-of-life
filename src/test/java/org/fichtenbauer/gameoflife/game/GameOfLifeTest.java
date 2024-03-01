package org.fichtenbauer.gameoflife.game;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


import static org.assertj.core.api.Assertions.assertThat;
import static org.fichtenbauer.gameoflife.game.CellState.ALIVE;
import static org.fichtenbauer.gameoflife.game.CellState.DEAD;
import static org.fichtenbauer.gameoflife.game.GameOfLife.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeTest {
    @Test
    void canary() {
        assertTrue(true);
    }

    @Test
    void deadCellBehavior() {
        assertThat(nextState(DEAD, 0)).isEqualTo(DEAD);
        assertThat(nextState(DEAD, 1)).isEqualTo(DEAD);
        assertThat(nextState(DEAD, 2)).isEqualTo(DEAD);
        assertThat(nextState(DEAD, 3)).isEqualTo(ALIVE);
        assertThat(nextState(DEAD, 4)).isEqualTo(DEAD);
        assertThat(nextState(DEAD, 5)).isEqualTo(DEAD);
        assertThat(nextState(DEAD, 6)).isEqualTo(DEAD);
        assertThat(nextState(DEAD, 7)).isEqualTo(DEAD);
        assertThat(nextState(DEAD, 8)).isEqualTo(DEAD);
    }

    @Test
    void liveCellBehavior() {
        assertThat(nextState(ALIVE, 0)).isEqualTo(DEAD);
        assertThat(nextState(ALIVE, 1)).isEqualTo(DEAD);
        assertThat(nextState(ALIVE, 2)).isEqualTo(ALIVE);
        assertThat(nextState(ALIVE, 3)).isEqualTo(ALIVE);
        assertThat(nextState(ALIVE, 4)).isEqualTo(DEAD);
        assertThat(nextState(ALIVE, 5)).isEqualTo(DEAD);
        assertThat(nextState(ALIVE, 6)).isEqualTo(DEAD);
        assertThat(nextState(ALIVE, 7)).isEqualTo(DEAD);
        assertThat(nextState(ALIVE, 8)).isEqualTo(DEAD);
    }

    /**       0  1  2  3  4  5  6  7  8  9
     *     0
     *     1
     *     2
     *     3
     *     4
     *     5                 O
     *     6
     *     7
     *     8
     *     9
     */
    @Test
    void numberOfLiveNeighborsIs0() {
        Map<Cell, CellState> universe = createUniverse();

        assertThat(numberOfLiveNeighborsOf(universe, Cell.of(5,5)))
                .isEqualTo(0);
    }

    /**       0  1  2  3  4  5  6  7  8  9
     *     0
     *     1
     *     2
     *     3
     *     4
     *     5                 O
     *     6                 X
     *     7
     *     8
     *     9
     */
    @Test
    void numberOfLiveNeighborsIs1() {
        Map<Cell, CellState> universe = createUniverse(Cell.of(5, 6));

        assertThat(numberOfLiveNeighborsOf(universe, Cell.of(5,5)))
                .isEqualTo(1);
    }

    /**       0  1  2  3  4  5  6  7  8  9
     *     0
     *     1
     *     2
     *     3
     *     4
     *     5                (X)
     *     6                 X
     *     7
     *     8
     *     9
     */
    @Test
    void numberOfLiveNeighborsIs1ForALiveCell() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(5, 5),
                Cell.of(5, 6)
        );

        assertThat(numberOfLiveNeighborsOf(universe, Cell.of(5, 5)))
                .isEqualTo(1);
    }

    /**       0  1  2  3  4  5  6  7  8  9
     *     0
     *     1
     *     2
     *     3
     *     4
     *     5              X  O
     *     6                 X
     *     7
     *     8
     *     9
     */
    @Test
    void numberOfLiveNeighborsIs2() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(4, 5),
                Cell.of(5, 6)
        );

        assertThat(numberOfLiveNeighborsOf(universe, Cell.of(5, 5)))
                .isEqualTo(2);
    }

    /**       0  1  2  3  4  5  6  7  8  9
     *     0
     *     1
     *     2
     *     3
     *     4                    X
     *     5              X  O
     *     6                 X
     *     7
     *     8
     *     9
     */
    @Test
    void numberOfLiveNeighborsIs3() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(4, 5),
                Cell.of(5, 6),
                Cell.of(6, 4)
        );

        assertThat(numberOfLiveNeighborsOf(universe, Cell.of(5, 5)))
            .isEqualTo(3);
    }

    /**       0  1  2  3  4  5  6  7  8  9
     *     0
     *     1
     *     2
     *     3
     *     4     X
     *     5               X O
     *     6                 X
     *     7
     *     8
     *     9
     */
    @Test
    void numberOfLiveNeighborsIs2WithAFarAwayNeighbor() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(1, 4),
                Cell.of(4, 5),
                Cell.of(5, 6)
        );

        assertThat(numberOfLiveNeighborsOf(universe, Cell.of(5, 5)))
                .isEqualTo(2);
    }

    /**       0  1  2  3  4  5  6  7  8  9
     *     0 (O)
     *     1  X
     *     2
     *     3
     *     4     X
     *     5
     *     6
     *     7
     *     8
     *     9
     */
    @Test
    void numberOfLiveNeibhborsForTopLeftCell() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(0, 0),
                Cell.of(0, 1),
                Cell.of(1, 4)
        );

        assertThat(numberOfLiveNeighborsOf(universe, Cell.of(0, 0)))
                .isEqualTo(1);
    }

    /**       0  1  2  3  4  5  6  7  8  9
     *     0
     *     1
     *     2
     *     3
     *     4
     *     5
     *     6
     *     7
     *     8  X  X
     *     9 (X)
     */
    @Test
    void numberOfLiveNeibhborsForTopRightCell() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(0, 8),
                Cell.of(0, 9),
                Cell.of(1, 8)
        );

        assertThat(numberOfLiveNeighborsOf(universe, Cell.of(0, 9)))
                .isEqualTo(2);
    }

    /**       0  1  2  3  4  5  6  7  8  9
     *     0                             O
     *     1                             X
     *     2
     *     3
     *     4
     *     5
     *     6
     *     7
     *     8  X  X
     *     9
     */
    @Test
    void numberOfLiveNeibhborsForBottomLeftCell() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(0, 8),
                Cell.of(1, 8),
                Cell.of(9, 1)
                );

        assertThat(numberOfLiveNeighborsOf(universe, Cell.of(9, 0)))
                .isEqualTo(1);
    }

    /**       0  1  2  3  4  5  6  7  8  9
     *     0
     *     1
     *     2
     *     3
     *     4
     *     5
     *     6
     *     7
     *     8     X                       X
     *     9                            (X)
     */
    @Test
    void numberOfLiveNeibhborsForBottomRightCell() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(1, 8),
                Cell.of(9, 8),
                Cell.of(9, 9)
                );

        assertThat(numberOfLiveNeighborsOf(universe, Cell.of(9, 9)))
                .isEqualTo(1);
    }

    /**    universe                               nextGeneration(universe)
     *        0  1  2  3  4  5  6  7  8  9           0  1  2  3  4  5  6  7  8  9
     *     0                                      0
     *     1                                      1
     *     2                                      2
     *     3                                      3
     *     4                                      4
     *     5                                      5
     *     6                                      6
     *     7                                      7
     *     8                                      8
     *     9                                      9
     */
    @Test
    void aDeadUniverseStaysDead() {
        Map<Cell, CellState> universe = createUniverse();

        assertThat(universe)
                .usingRecursiveComparison()
                .isEqualTo(nextGeneration(universe));
    }

    /**    universe                               nextGeneration(universe)
     *        0  1  2  3  4  5  6  7  8  9           0  1  2  3  4  5  6  7  8  9
     *     0                                      0
     *     1     X                                1
     *     2                                      2
     *     3                                      3
     *     4                                      4
     *     5                                      5
     *     6                                      6
     *     7                                      7
     *     8                                      8
     *     9                                      9
     */
    @Test
    void aUniverseWithALoneLiveCellBecomesDead() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(1, 1)
        );

        Map<Cell, CellState> expected = createUniverse();

        assertThat(nextGeneration(universe))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    /**    universe                               nextGeneration(universe)
     *        0  1  2  3  4  5  6  7  8  9           0  1  2  3  4  5  6  7  8  9
     *     0                                      0
     *     1     X                                1
     *     2     X                                2
     *     3                                      3
     *     4                                      4
     *     5                                      5
     *     6                                      6
     *     7                                      7
     *     8                                      8
     *     9                                      9
     */
    @Test
    void aUniverseWithTwoLiveCellsBecomesDead() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(1, 1),
                Cell.of(1, 2)
        );

        Map<Cell, CellState> expected = createUniverse();

        assertThat(nextGeneration(universe))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    /**    universe                               nextGeneration(universe)
     *        0  1  2  3  4  5  6  7  8  9           0  1  2  3  4  5  6  7  8  9
     *     0                                      0
     *     1     X                                1     X  X
     *     2     X  X                             2     X  X
     *     3                                      3
     *     4                                      4
     *     5                                      5
     *     6                                      6
     *     7                                      7
     *     8                                      8
     *     9                                      9
     */
    @Test
    void transformUniverseWithThreeLiveNeighbors() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(1, 1),
                Cell.of(1, 2),
                Cell.of(2, 2)
        );

        Map<Cell, CellState> expected = createUniverse(
                Cell.of(1, 1),
                Cell.of(1, 2),
                Cell.of(2, 1),
                Cell.of(2, 2)
        );

        assertThat(nextGeneration(universe))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    /**    universe                               nextGeneration(universe)
     *        0  1  2  3  4  5  6  7  8  9           0  1  2  3  4  5  6  7  8  9
     *     0                                      0
     *     1     X  X                             1     X  X
     *     2     X  X                             2     X  X
     *     3                                      3
     *     4                                      4
     *     5                                      5
     *     6                                      6
     *     7                                      7
     *     8                                      8
     *     9                                      9
     */
    @Test
    void aBlockRemainsABlock() {

        Map<Cell, CellState> universe = createUniverse(
                Cell.of(1,1),
                Cell.of(1,2),
                Cell.of(2,1),
                Cell.of(2,2)
        );

        Map<Cell, CellState> expected = createUniverse(
                Cell.of(1, 1),
                Cell.of(1, 2),
                Cell.of(2, 1),
                Cell.of(2, 2)
                );

        assertThat(nextGeneration(universe))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    /**    universe                               nextGeneration(universe)
     *        0  1  2  3  4  5  6  7  8  9           0  1  2  3  4  5  6  7  8  9
     *     0                                      0
     *     1                                      1
     *     2           X                          2
     *     3           X                          3        X  X  X
     *     4           X                          4
     *     5                                      5
     *     6                                      6
     *     7                                      7
     *     8                                      8
     *     9                                      9
     */
    @Test
    void aVerticalBlinkerBecomesAHorizontalBlinker() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(3,2),
                Cell.of(3,3),
                Cell.of(3,4)
        );

        Map<Cell, CellState> expected = createUniverse(
                Cell.of(2, 3),
                Cell.of(3, 3),
                Cell.of(4, 3)
        );

        assertThat(nextGeneration(universe))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    /**    universe                               nextGeneration(universe)
     *        0  1  2  3  4  5  6  7  8  9           0  1  2  3  4  5  6  7  8  9
     *     0                                      0
     *     1                                      1
     *     2                                      2           X
     *     3        X  X  X                       3           X
     *     4                                      4           X
     *     5                                      5
     *     6                                      6
     *     7                                      7
     *     8                                      8
     *     9                                      9
     */
    @Test
    void aHorizontalBlinkerBecomesAVerticalBlinker() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(2, 3),
                Cell.of(3, 3),
                Cell.of(4, 3)
        );

        Map<Cell, CellState> expected = createUniverse(
                Cell.of(3,2),
                Cell.of(3,3),
                Cell.of(3,4)
        );

        assertThat(nextGeneration(universe))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private Map<Cell, CellState> createUniverse(Cell... livePositions) {
        return Arrays.stream(livePositions)
                .collect(Collectors.toMap(c -> c, c -> ALIVE));
    }

}
