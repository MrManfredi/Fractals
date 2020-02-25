package kpi.manfredi.gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import kpi.manfredi.Direction;
import kpi.manfredi.Figure;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;

import static kpi.manfredi.Direction.*;

public class Controller {
    @FXML
    private Canvas canvas;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    void initialize() {
        initSpinner();
        drawQuadraticKochIsland(spinner.getValue());
    }

    private void initSpinner() {
        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, 2);
        spinner.setValueFactory(valueFactory);

        // When spinner change value.
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            drawQuadraticKochIsland(newValue);
        });
    }

    private void drawQuadraticKochIsland(int depth) {
        List<Direction> axiom = List.of(
                FORTH,
                LEFT, FORTH,
                LEFT, FORTH,
                LEFT, FORTH
        );
        HashMap<Direction, List<Direction>> rules = new HashMap<>();
        rules.put(
                FORTH,
                List.of(
                        FORTH,
                        LEFT, FORTH,
                        RIGHT, FORTH,
                        RIGHT, FORTH, FORTH,
                        LEFT, FORTH,
                        LEFT, FORTH,
                        RIGHT, FORTH
                ));
        int startLength = 400;
        Figure figure = new Figure(
                axiom,
                rules,
                new Point2D.Double(this.canvas.getWidth() / 2 - startLength * 1.4 / 2, this.canvas.getWidth() / 2),
                startLength,
                4,
                depth,
                Math.PI / 4,
                Math.PI / 2
        );
        drawFigure(figure.getPoints());
    }

    private void drawFigure(List<Point2D> points) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setLineWidth(1.0);
        context.beginPath();
        for (Point2D point : points) {
            context.lineTo(point.getX(), point.getY());
        }
        context.stroke();
    }
}
