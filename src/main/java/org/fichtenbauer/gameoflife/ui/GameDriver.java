package org.fichtenbauer.gameoflife.ui;

import org.fichtenbauer.gameoflife.game.Cell;
import org.fichtenbauer.gameoflife.game.CellState;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.fichtenbauer.gameoflife.game.CellState.ALIVE;
import static org.fichtenbauer.gameoflife.game.GameOfLife.nextGeneration;
import static java.lang.Thread.sleep;

public class GameDriver {
    private static final int SIZE = 10;

    public static void main(String[] args) throws InterruptedException {
        Map<Cell, CellState> universe = initializeUniverse();

        while (true) {
            clearScreen();
            displayUniverse(universe);

            sleep(1000);

            universe = nextGeneration(universe);
        }
    }

    private static void displayUniverse(Map<Cell, CellState> universe) {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                System.out.print(universe.get(Cell.of(x, y)) == CellState.ALIVE ? "X " : "  ");
            }

            System.out.println("");
        }
    }

    private static void clearScreen() {
        System.out.println("\033[H\033[2J");
    }

    /**    universe
     *        0  1  2  3  4  5  6  7  8  9
     *     0
     *     1
     *     2
     *     3              X
     *     4              X
     *     5              X  X
     *     6
     *     7
     *     8
     *     9
     */
    private static Map<Cell, CellState> initializeUniverse() {
        Map<Cell, CellState> universe = createUniverse(
                Cell.of(4, 3),
                Cell.of(4, 4),
                Cell.of(4, 5),
                Cell.of(5, 5)
        );
        return universe;
    }

    public static Map<Cell, CellState> createUniverse(Cell... livePositions) {
        return Arrays.stream(livePositions)
                .collect(Collectors
                .toMap(c -> c, c -> ALIVE));
    }
}