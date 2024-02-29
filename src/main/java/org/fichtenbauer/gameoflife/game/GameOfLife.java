package org.fichtenbauer.gameoflife.game;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static org.fichtenbauer.gameoflife.game.CellState.*;

public class GameOfLife {

    public static CellState nextState(CellState cellState, int numberOfLiveNeighbors) {
        return numberOfLiveNeighbors == 3 || cellState == ALIVE && numberOfLiveNeighbors == 2 ? ALIVE : DEAD;
    }

    public static int numberOfLiveNeighborsOf(Map<Cell, CellState> universe, Cell cell) {
        int count = (int) IntStream.rangeClosed(cell.getX() - 1, cell.getX() + 1)
                .flatMap(x -> IntStream.rangeClosed(cell.getY() - 1, cell.getY() + 1)
                .filter(y -> isAliveAt(universe, Cell.of(x, y))))
                .count();

        return universe.get(cell) == ALIVE ? count - 1 : count;
    }

    public static Map<Cell, CellState> nextGeneration(Map<Cell, CellState> universe) {
        Map<Cell, CellState> nextGenerationUniverse = new HashMap<>();

        for (Map.Entry<Cell, CellState> entry : universe.entrySet()) {
            Cell baseCell = entry.getKey();
            for (int x = baseCell.getX() - 1; x <= baseCell.getX() + 1; x++) {
                for (int y = baseCell.getY() - 1; y <= baseCell.getY() + 1; y++) {
                    Cell localCell = Cell.of(x, y);
                    CellState localCellState = universe.get(localCell) != null ? ALIVE : DEAD;
                    CellState nextState = nextState(localCellState, numberOfLiveNeighborsOf(universe, localCell));
                    if (nextState == ALIVE) {
                        nextGenerationUniverse.put(localCell, nextState);
                    }
                }
            }
        }

        return nextGenerationUniverse;
    }

    private static boolean isAliveAt(Map<Cell, CellState> universe, Cell cell) {
        return universe.get(cell) != null && universe.get(cell) == ALIVE;
    }

}
