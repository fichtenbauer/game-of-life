package org.fichtenbauer.gameoflife.ui;

import org.fichtenbauer.gameoflife.game.Cell;
import org.fichtenbauer.gameoflife.game.CellState;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.fichtenbauer.gameoflife.game.CellState.ALIVE;
import static org.fichtenbauer.gameoflife.game.GameOfLife.createUniverse;

public class InitialShape {

    public static Map<Cell, CellState> of(String name, int rows, int columns) {

        return switch (name) {
            case "blinker" -> createUniverse(
                    Cell.of(3, 2), Cell.of(3, 3), Cell.of(3, 4)
            );
            case "toad" -> createUniverse(
                    Cell.of(4, 2), Cell.of(2, 3), Cell.of(5, 3),
                    Cell.of(2, 4), Cell.of(5, 4), Cell.of(3, 5)
            );
            case "beacon" -> createUniverse(
                    Cell.of(2, 2), Cell.of(3, 2), Cell.of(2, 3),
                    Cell.of(3, 3), Cell.of(4, 4), Cell.of(5, 4),
                    Cell.of(4, 5), Cell.of(5, 5)
            );
            case "pulsar" -> createUniverse(
                    Cell.of(5, 3), Cell.of(6, 3), Cell.of(7, 3),
                    Cell.of(11, 3), Cell.of(12, 3), Cell.of(13, 3),
                    Cell.of(3, 5), Cell.of(8, 5), Cell.of(10, 5),
                    Cell.of(15, 5), Cell.of(3, 6), Cell.of(8, 6),
                    Cell.of(10, 6), Cell.of(15, 6), Cell.of(3, 7),
                    Cell.of(8, 7), Cell.of(10, 7), Cell.of(15, 7),
                    Cell.of(5, 8), Cell.of(6, 8), Cell.of(7, 8),
                    Cell.of(11, 8), Cell.of(12, 8), Cell.of(13, 8),
                    Cell.of(5, 10), Cell.of(6, 10), Cell.of(7, 10),
                    Cell.of(11, 10), Cell.of(12, 10), Cell.of(13, 10),
                    Cell.of(3, 11), Cell.of(8, 11), Cell.of(10, 11),
                    Cell.of(15, 11), Cell.of(3, 12), Cell.of(8, 12),
                    Cell.of(10, 12), Cell.of(15, 12), Cell.of(3, 13),
                    Cell.of(8, 13), Cell.of(10, 13), Cell.of(15, 13),
                    Cell.of(5, 15), Cell.of(6, 15), Cell.of(7, 15),
                    Cell.of(11, 15), Cell.of(12, 15), Cell.of(13, 15)
            );
            case "pentadecathlon" -> createUniverse(
                    Cell.of(5, 5), Cell.of(6, 5), Cell.of(7, 5),
                    Cell.of(4, 6), Cell.of(8, 6), Cell.of(3, 7),
                    Cell.of(9, 7), Cell.of(2, 9), Cell.of(10, 9),
                    Cell.of(2, 10), Cell.of(10, 10), Cell.of(3, 12),
                    Cell.of(9, 12), Cell.of(4, 13), Cell.of(8, 13),
                    Cell.of(5, 14), Cell.of(6, 14), Cell.of(7, 14)
            );
            case "pattern" -> createUniverse(
                    Cell.of(5, 5)
            );
            case "random" -> IntStream.rangeClosed(5, rows - 6)
                    .boxed()
                    .flatMap(y -> IntStream.rangeClosed(5, columns - 6)
                    .mapToObj(x -> Cell.of(x, y)))
                    .filter(e -> Math.random() < 0.5)
                    .collect(Collectors.toMap(c -> c, c -> ALIVE));
            default -> new HashMap<>();
        };
    }
}

