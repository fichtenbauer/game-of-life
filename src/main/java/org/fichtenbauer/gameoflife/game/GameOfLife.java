package org.fichtenbauer.gameoflife.game;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.fichtenbauer.gameoflife.game.CellState.*;

public class GameOfLife {

    public static CellState nextState(CellState cellState, int numberOfLiveNeighbors) {
        return switch (numberOfLiveNeighbors) {
            case 2 -> cellState;
            case 3 -> ALIVE;
            default -> DEAD;
        };
    }

    public static int numberOfLiveNeighborsOf(Map<Cell, CellState> universe, Cell cell) {
        int count = (int) IntStream.rangeClosed(cell.x() - 1, cell.x() + 1)
                .flatMap(x -> IntStream.rangeClosed(cell.y() - 1, cell.y() + 1)
                .filter(y -> isAliveAt(universe, Cell.of(x, y))))
                .count();

        return universe.get(cell) == ALIVE ? count - 1 : count;
    }

    public static Map<Cell, CellState> nextGeneration(Map<Cell, CellState> universe) {
        return universe.entrySet()
                .stream()
                .flatMap(e -> expandToCellMatrix(e, universe))
                .map(e -> incrementCellState(e, universe))
                .filter(e -> e.getValue() == ALIVE )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1));
    }

    public static Stream<Map.Entry<Cell, CellState>> expandToCellMatrix(Map.Entry<Cell, CellState> e, Map<Cell, CellState> universe) {
        Cell baseCell = e.getKey();
        Map<Cell, CellState> matrix = Stream.iterate(baseCell.y() -1, n -> n <= baseCell.y() + 1 , n -> n + 1)
                .flatMap(y -> IntStream.rangeClosed(baseCell.x() - 1, baseCell.x() + 1)
                .mapToObj(x -> Cell.of(x, y)))
                .collect(Collectors.toMap(c -> c, c -> universe.get(c) != null ? ALIVE : DEAD));
        return matrix.entrySet().stream();
    }

    public static Map.Entry<Cell, CellState> incrementCellState(Map.Entry<Cell, CellState> e, Map<Cell, CellState> universe) {
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
