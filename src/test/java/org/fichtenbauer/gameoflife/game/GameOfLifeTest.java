package org.fichtenbauer.gameoflife.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.fichtenbauer.gameoflife.game.GameOfLife.*;
import static org.fichtenbauer.gameoflife.game.GameOfLife.CellState.*;
import static org.fichtenbauer.gameoflife.game.GameOfLife.CellState.ALIVE;
import static org.fichtenbauer.gameoflife.game.GameOfLife.CellState.DEAD;
import static org.fichtenbauer.gameoflife.game.GameOfLife.nextGeneration;
import static org.fichtenbauer.gameoflife.game.GameOfLife.nextState;
import static org.fichtenbauer.gameoflife.game.GameOfLife.numberOfLiveNeighborsOf;
import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeTest {
  @Test
  void canary(){
    assertTrue(true);
  }

  /*
  Think about the design principles
  Think about Kent Beck's 4 rules for simple design:
  -Passes all the tests
  -Reveals intention
  -No duplication
  -Fewest elements

  Write expressive, minimum code
   */

  //a dead cell with 0 live neighbors stays dead
  //how should we implement this test and the code - design?

  //may be start with a grid and have a next function
  //initiate game
  //have a cell state dead or alive
  //set up 9 dead cells in the next generation validate the center cell is still dead
  //have the concept of neighbors of a cell

  //Good thoughts, let us take that further into the minimum we have to do to get this working
  //Let us distill this down to a function that takes two input and returns one output
  //state of a cell is one input
  //number of live neighbors

  @Test
  void deadCellBehavior(){
    assertAll(
      () -> assertEquals(DEAD, nextState(DEAD, 0)),
      () -> assertEquals(DEAD, nextState(DEAD, 1)),
      () -> assertEquals(DEAD, nextState(DEAD, 2)),
      () -> assertEquals(ALIVE, nextState(DEAD, 3)),
      () -> assertEquals(DEAD, nextState(DEAD, 4)),
      () -> assertEquals(DEAD, nextState(DEAD, 5)),
      () -> assertEquals(DEAD, nextState(DEAD, 6)),
      () -> assertEquals(DEAD, nextState(DEAD, 7)),
      () -> assertEquals(DEAD, nextState(DEAD, 8))

    );
  }

  @Test
  void liveCellBehavior(){
    assertAll(
      () -> assertEquals(DEAD, nextState(ALIVE, 0)),
      () -> assertEquals(DEAD, nextState(ALIVE, 1)),
      () -> assertEquals(ALIVE, nextState(ALIVE, 2)),
      () -> assertEquals(ALIVE, nextState(ALIVE, 3)),
      () -> assertEquals(DEAD, nextState(ALIVE, 4)),
      () -> assertEquals(DEAD, nextState(ALIVE, 5)),
      () -> assertEquals(DEAD, nextState(ALIVE, 6)),
      () -> assertEquals(DEAD, nextState(ALIVE, 7)),
      () -> assertEquals(DEAD, nextState(ALIVE, 8))
    );
  }

  //AAA
  //Arrange
  //Act
  //Assert

  @Test
  void numberOfLiveNeighborsIs0(){
    CellState[][] cells = createUniverse();

    assertEquals(0, numberOfLiveNeighborsOf(cells, 5, 5));
  }

  //The first few tests help us to design the skin (the interface). More tests that follow
  //help us to design the guts (the implementation).

  @Test
  void numberOfLiveNeighborsIs1(){
    CellState[][] cells = createUniverse(List.of(5, 6));

    assertEquals(1, numberOfLiveNeighborsOf(cells, 5, 5));
  }

  @Test
  void numberOfLiveNeighborsIs1ForALiveCell(){
    CellState[][] cells = createUniverse(List.of(5, 6), List.of(5, 5));

    assertEquals(1, numberOfLiveNeighborsOf(cells, 5, 5));
  }

  @Test
  void numberOfLiveNeighborsIs2(){
    CellState[][] cells = createUniverse(List.of(5, 6), List.of(4, 5));

    assertEquals(2, numberOfLiveNeighborsOf(cells, 5, 5));
  }

  @Test
  void numberOfLiveNeighborsIs3(){
    CellState[][] cells = createUniverse(List.of(5, 6), List.of(4, 5), List.of(6, 4));

    assertEquals(3, numberOfLiveNeighborsOf(cells, 5, 5));
  }

  @Test
  void numberOfLiveNeighborsIs2WithAFarAwayNeighbor(){
    CellState[][] cells = createUniverse(List.of(5, 6), List.of(4, 5), List.of(1, 4));

    assertEquals(2, numberOfLiveNeighborsOf(cells, 5, 5));
  }

  @Test
  void numberOfLiveNeibhborsForTopLeftCell(){
    CellState[][] cells = createUniverse(List.of(0, 0), List.of(0, 1), List.of(1, 4));

    assertEquals(1, numberOfLiveNeighborsOf(cells, 0, 0));
  }

  @Test
  void numberOfLiveNeibhborsForTopRightCell(){
    CellState[][] cells = createUniverse(List.of(0, 9), List.of(0, 8), List.of(1, 8));

    assertEquals(2, numberOfLiveNeighborsOf(cells, 0, 9));
  }

  @Test
  void numberOfLiveNeibhborsForBottomLeftCell(){
    CellState[][] cells = createUniverse(List.of(9, 1), List.of(0, 8), List.of(1, 8));

    assertEquals(1, numberOfLiveNeighborsOf(cells, 9, 0));
  }

  @Test
  void numberOfLiveNeibhborsForBottomRightCell(){
    CellState[][] cells = createUniverse(List.of(9, 9), List.of(9, 8), List.of(1, 8));

    assertEquals(1, numberOfLiveNeighborsOf(cells, 9, 9));
  }

  @Test
  void aDeadUniverseStaysDead(){
    CellState[][] universe = createUniverse();

    assertArrayEquals(universe, nextGeneration(universe));
  }

  @Test
  void aUniverseWithALoneLiveCellBecomesDead(){
    CellState[][] universe = createUniverse(List.of(1, 1));

    assertArrayEquals(createUniverse(), nextGeneration(universe));
  }

  @Test
  void aUniverseWithTwoLiveCellsBecomesDead(){
    CellState[][] universe = createUniverse(List.of(1, 1), List.of(1, 2));

    assertArrayEquals(createUniverse(), nextGeneration(universe));
  }

  @Test
  void transformUniverseWithThreeLiveNeighbors(){
    CellState[][] universe = createUniverse(List.of(1, 1), List.of(1, 2), List.of(2, 2));

    assertArrayEquals(createUniverse(List.of(1, 1), List.of(1, 2), List.of(2, 2), List.of(2, 1)), nextGeneration(universe));
  }

  @Test
  void aBlockRemainsABlock(){
    CellState[][] universe = createUniverse(List.of(1, 1), List.of(1, 2), List.of(2, 2), List.of(2, 1));

    assertArrayEquals(createUniverse(List.of(1, 1), List.of(1, 2), List.of(2, 2), List.of(2, 1)), nextGeneration(universe));
  }

  @Test
  void aHorizontalBlinkerBecomesAVerticalBlinker(){
    CellState[][] universe = createUniverse(List.of(3, 2), List.of(3, 3), List.of(3, 4));

    assertArrayEquals(createUniverse(List.of(2, 3), List.of(3, 3), List.of(4, 3)), nextGeneration(universe));
  }

  @Test
  void aVerticalBlinkerBecomesAHorizontaBlinker(){
    CellState[][] universe = createUniverse(List.of(2, 3), List.of(3, 3), List.of(4, 3));

    assertArrayEquals(createUniverse(List.of(3, 2), List.of(3, 3), List.of(3, 4)), nextGeneration(universe));
  }

  private CellState[][] createUniverse(List<Integer>... livePositions) {
    CellState[][] cells = new CellState[10][10];

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        cells[i][j] = DEAD;
      }
    }

    for(var livePosition: livePositions) {
      int row = livePosition.get(0);
      int column = livePosition.get(1);

      cells[row][column] = ALIVE;
    }

    return cells;
  }
}
