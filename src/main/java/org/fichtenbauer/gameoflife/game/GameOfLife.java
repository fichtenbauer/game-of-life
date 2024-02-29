package org.fichtenbauer.gameoflife.game;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
        return universe.entrySet()
                .stream()
                .flatMap(e -> cellMatrix(e, universe))
                .map(e -> nextState(e, universe))
                .filter(e -> e.getValue() == ALIVE )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1));
    }

    public static Stream<Map.Entry<Cell, CellState>> cellMatrix(Map.Entry<Cell, CellState> e, Map<Cell, CellState> universe) {
        Map<Cell, CellState> matrix = new HashMap<>();
        Cell baseCell = e.getKey();
        for (int x = baseCell.getX() - 1; x <= baseCell.getX() + 1; x++) {
            for (int y = baseCell.getY() - 1; y <= baseCell.getY() + 1; y++) {
                Cell localCell = Cell.of(x, y);
                CellState localCellState = universe.get(localCell) != null ? ALIVE : DEAD;
                matrix.put(localCell, localCellState);
            }
        }
        return matrix.entrySet().stream();
    }

    public static Map.Entry<Cell, CellState> nextState(Map.Entry<Cell, CellState> e, Map<Cell, CellState> universe) {
        Cell currCell =  e.getKey();
        CellState currState = e.getValue();
        CellState nextState = nextState(currState, numberOfLiveNeighborsOf(universe, currCell));
        e.setValue(nextState);
        return e;
    }

    private static boolean isAliveAt(Map<Cell, CellState> universe, Cell cell) {
        return universe.get(cell) != null && universe.get(cell) == ALIVE;
    }

}
