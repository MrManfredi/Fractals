package kpi.manfredi;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Figure {
    private List<Direction> axiom;
    private HashMap<Direction, List<Direction>> rules;
    private Point2D startPoint;
    private int startLength;
    private int similarityFactor;
    private int depth;
    private double guideAngle;
    private final double angle;

    public Figure(List<Direction> axiom, HashMap<Direction, List<Direction>> rules, Point2D startPoint, int startLength, int similarityFactor, int depth, double guideAngle, double angle) {
        this.axiom = axiom;
        this.rules = rules;
        this.startPoint = startPoint;
        this.startLength = startLength;
        this.similarityFactor = similarityFactor;
        this.depth = depth;
        this.guideAngle = guideAngle;
        this.angle = angle;
    }

    public List<Point2D> getPoints() {
        List<Point2D> points = new ArrayList<>();
        List<Direction> guideVectors = decompose(axiom, depth);
        double length = startLength / Math.pow(similarityFactor, depth);
        points.add(startPoint);
        Point2D lastPoint = startPoint;
        for (Direction direction : guideVectors) {
            switch (direction) {
                case LEFT:
                    guideAngle -= angle;
                    break;
                case RIGHT:
                    guideAngle += angle;
                    break;
                case FORTH:
                    lastPoint = new Point2D.Double(length * Math.cos(guideAngle) + lastPoint.getX(), length * Math.sin(guideAngle) + lastPoint.getY());
                    points.add(lastPoint);
                    break;
            }
        }
        return points;
    }
    
    private List<Direction> decompose(List<Direction> axiom, int depth) {
        if (depth < 1) {
            return axiom;
        }
        List<Direction> resultList = new LinkedList<>();
        for (Direction direction : axiom) {
            List<Direction> replacement = rules.get(direction);
            if (replacement != null) {
                resultList.addAll(replacement);
            } else {
                resultList.add(direction);
            }
        }
        resultList = decompose(resultList, depth - 1);
        return resultList;
    }
}
