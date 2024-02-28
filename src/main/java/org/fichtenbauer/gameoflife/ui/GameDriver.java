package org.fichtenbauer.gameoflife.ui;

import org.fichtenbauer.gameoflife.game.GameOfLife.CellState;

import static org.fichtenbauer.gameoflife.game.GameOfLife.nextGeneration;
import static java.lang.Thread.sleep;

public class GameDriver {
  private static final int SIZE = 10;

  public static void main(String[] args) throws InterruptedException {
    CellState[][] universe = initializeUniverse();

    while(true) {
      clearScreen();
      displayUniverse(universe);

      sleep(1000);

      universe = nextGeneration(universe);
    }
  }

  private static void displayUniverse(CellState[][] universe) {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        System.out.print(universe[i][j] == CellState.ALIVE ? "X" : " ");
      }

      System.out.println("");
    }
  }

  private static void clearScreen() {
    System.out.println("\033[H\033[2J");
  }

  private static CellState[][] initializeUniverse() {
    CellState[][] universe = new CellState[SIZE][SIZE];

    universe[4][3] = CellState.ALIVE;
    universe[4][4] = CellState.ALIVE;
    universe[4][5] = CellState.ALIVE;
    universe[5][5] = CellState.ALIVE;
    //universe[5][6] = CellState.ALIVE;

    return universe;
  }
}