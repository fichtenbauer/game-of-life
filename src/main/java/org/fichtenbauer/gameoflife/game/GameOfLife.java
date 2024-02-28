package org.fichtenbauer.gameoflife.game;

import java.util.stream.IntStream;

public class GameOfLife {
  public static final int SIZE = 10;
  public enum CellState { DEAD, ALIVE }

  public static CellState nextState(CellState cellState, int numberOfLiveNeighbors) {
//    if(numberOfLiveNeighbors == 3) {
//      return CellState.ALIVE;
//    }
//
//    if(numberOfLiveNeighbors == 2 && cellState == CellState.ALIVE) {
//      return CellState.ALIVE;
//    }
//
//    return CellState.DEAD;

    return numberOfLiveNeighbors == 3 || cellState == CellState.ALIVE && numberOfLiveNeighbors == 2 ?
      CellState.ALIVE : CellState.DEAD;
  }

  public static int numberOfLiveNeighborsOf(CellState[][] cells, int row, int column) {
//    int count = 0;
//
//    for(int i = row - 1; i <= row + 1; i++) {
//      for(int j = column - 1; j <= column + 1; j++) {
//        if(isAliveAt(cells, i, j)) {
//          count++;
//        }
//      }
//    }
//
//    return cells[row][column] == CellState.ALIVE ? count - 1 : count;

    //if you prefer imperative style, keep the above. If you prefer a functional style, then consider the following:

    int count = (int) IntStream.rangeClosed(row - 1, row + 1)
      .flatMap(i -> IntStream.rangeClosed(column - 1, column + 1)
         .filter(j -> isAliveAt(cells, i, j)))
      .count();

    return cells[row][column] == CellState.ALIVE ? count - 1 : count;
  }

  public static CellState[][] nextGeneration(CellState[][] universe) {
    CellState[][] nextGenerationUniverse = new CellState[SIZE][SIZE];

    for(int i = 0; i < SIZE; i++) {
      for(int j = 0; j < SIZE; j++) {
        nextGenerationUniverse[i][j] = nextState(universe[i][j], numberOfLiveNeighborsOf(universe, i, j));
      }
    }

    return nextGenerationUniverse;
  }

  private static boolean isAliveAt(CellState[][] cells, int row, int column) {
    return row >= 0 && row < SIZE && column >= 0 && column < SIZE && cells[row][column] == CellState.ALIVE;
  }
}
