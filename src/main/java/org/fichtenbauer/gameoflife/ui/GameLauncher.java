package org.fichtenbauer.gameoflife.ui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.fichtenbauer.gameoflife.game.Cell;
import org.fichtenbauer.gameoflife.game.CellState;
import org.fichtenbauer.gameoflife.game.GameOfLife;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.fichtenbauer.gameoflife.game.CellState.ALIVE;

public class GameLauncher extends Application {

    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 500;
    public static final int CELL_SIZE = 10;
    public static final int ROWS = (int) Math.floor(CANVAS_HEIGHT / CELL_SIZE);
    public static final int COLUMNS = (int) Math.floor(CANVAS_WIDTH / CELL_SIZE);
    public static final Color BACKGROUND_COLOR = Color.rgb(255, 255, 255, 1);
    public static final Color FOREGROUND_COLOR = Color.rgb(0, 0, 0, 1);
    public static final long TICK_DELAY_NANOSECONDS = 500L;

    private Map<Cell, CellState> universe;
    private GraphicsContext graphics;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Conway's Game of Life");

        VBox root = new VBox(10);
        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT + 90);
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        Button blinker = new Button("Blinker");
        Button toad = new Button("Toad");
        Button beacon = new Button("Beacon");
        Button pulsar = new Button("Pulsar");
        Button pentadecathlon = new Button("Pentadecathlon");
        Button random = new Button("Random");

        Button run = new Button("Run");
        Button stop = new Button("Stop");
        Button step = new Button("Step");

        root.getChildren().addAll(canvas, new HBox(10, run, stop, step), new HBox(10, blinker, toad, beacon, pulsar, pentadecathlon, random));
        primaryStage.setScene(scene);
        primaryStage.show();

        graphics = canvas.getGraphicsContext2D();
        initialize("random");

        AnimationTimer runAnimation = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos(TICK_DELAY_NANOSECONDS)) {
                    tick();
                    lastUpdate = now;
                }
            }
        };

        blinker.setOnAction(event -> initialize("blinker"));
        toad.setOnAction(event -> initialize("toad"));
        beacon.setOnAction(event -> initialize("beacon"));
        pulsar.setOnAction(event -> initialize("pulsar"));
        pentadecathlon.setOnAction(event -> initialize("pentadecathlon"));
        random.setOnAction(event -> initialize("random"));

        run.setOnAction(event -> runAnimation.start());
        step.setOnAction(event -> tick());
        stop.setOnAction(event -> runAnimation.stop());
    }

    private void initialize(String shape) {
        universe = InitialShape.of(shape, ROWS, COLUMNS);
        draw();
    }

    private void tick() {
        universe = GameOfLife.nextGeneration(universe);
        draw();
    }

    private void draw() {
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                Cell cell = Cell.of(column, row);
                if (universe.get(cell) != null & universe.get(cell) == ALIVE) {
                    graphics.setFill(FOREGROUND_COLOR);
                } else {
                    graphics.setFill(BACKGROUND_COLOR);
                }
                graphics.fillRect(CELL_SIZE * column, CELL_SIZE * row, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
