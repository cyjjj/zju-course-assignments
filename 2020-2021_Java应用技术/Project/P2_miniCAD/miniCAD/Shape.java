package miniCAD;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Shape implements Serializable {

    private static final long serialVersionUID = 1L; // used for Serializable

    protected static final int delta = 15; // used for selected test

    public Color color; // color of the shape
    public float thickness; // thickness of the shape
    ArrayList<Point> v = new ArrayList<>(); // vertexes determining the shape
                                            // endponits of line, rectange/text box's diagonal, circle's diameter

    public Shape(Point p1, Point p2) {
        v.add(p1);
        v.add(p2);
        color = Color.BLACK; // default black
        thickness = 2.0f; // defaut thickness
    }

    protected abstract void Draw(Graphics2D g); // draw the shape

    protected abstract boolean isSelected(Point p); // whether the shape is selected or not

    public Point getCenterP() {
        int sumX = 0, sumY = 0;
        for (int i = 0; i < v.size(); i++) {
            sumX += v.get(i).x;
            sumY += v.get(i).y;
        }
        return new Point(sumX / v.size(), sumY / v.size());
    }

    // change the size of the shape(center point not change)
    // flag: 1-bigger, 0-smaller
    public void resize(int flag) {
        Point centerP = getCenterP(); // center point of the shapr
        for (int i = 0; i < v.size(); i++) {
            Point p = v.get(i);
            double dx = p.x - centerP.x;
            double dy = p.y - centerP.y;
            if (flag == 1) { // bigger
                dx *= 1.1;
                dy *= 1.1;
            } else { // smaller
                dx *= 0.9;
                dy *= 0.9;
            }
            v.set(i, new Point((int) dx + centerP.x, (int) dy + centerP.y));
        }
    }

    public Point[] adjustPoint() { // from let-top to right-down
        Point[] tmp = new Point[2];
        Point p1 = v.get(0);
        Point p2 = v.get(1);
        if (p1.x > p2.x && p1.y > p2.y) {
            tmp[0] = p2;
            tmp[1] = p1;
        } else if (p1.x < p2.x && p1.y < p2.y) {
            tmp[0] = p1;
            tmp[1] = p2;
        } else if (p1.x > p2.x && p1.y < p2.y) {
            tmp[0] = new Point(p2.x, p1.y);
            tmp[1] = new Point(p1.x, p2.y);
        } else {
            tmp[0] = new Point(p1.x, p2.y);
            tmp[1] = new Point(p2.x, p1.y);
        }
        return tmp;
    }
}

class Line extends Shape {

    private static final long serialVersionUID = 1L;

    public Line(Point p1, Point p2) {
        super(p1, p2);
    }

    @Override
    protected void Draw(Graphics2D g) {
        g.setStroke(new BasicStroke(thickness)); // set the thickness
        g.setColor(color); // set the color
        g.drawLine(v.get(0).x, v.get(0).y, v.get(1).x, v.get(1).y); // Draws a line between (x1, y1) and (x2, y2)
    }

    @Override
    protected boolean isSelected(Point p) {
        Point[] points = adjustPoint();
        if (p.x > points[0].x && p.y > points[0].y && p.x < points[1].x && p.y < points[1].y && getLineDist(p) <= delta)
            return true;
        return false;
    }

    double getLineDist(Point p) {
        Point p1 = v.get(0), p2 = v.get(1);
        if (p1.x == p2.x) // vertical
            return Math.abs(p.x - p1.x);
        if (p1.y == p2.y) // horizontal
            return Math.abs(p.y - p1.y);

        double k = (p2.y - p1.y) * 1.0 / (p2.x - p1.x);
        double b = p2.y - k * p2.x;
        double d = Math.abs((k * p.x - p.y + b) / (Math.sqrt(k * k + 1))); // distance from ponit to line
        return d;
    }
}

class Rect extends Shape {

    private static final long serialVersionUID = 1L;

    public Rect(Point p1, Point p2) {
        super(p1, p2);
    }

    @Override
    protected void Draw(Graphics2D g) {
        g.setStroke(new BasicStroke(thickness)); // set the thickness
        g.setColor(color); // set the color
        Point[] points = adjustPoint();
        g.drawRect(points[0].x, points[0].y, points[1].x - points[0].x, points[1].y - points[0].y);
    }

    @Override
    protected boolean isSelected(Point p) {
        Point[] points = adjustPoint();
        if (p.x > points[0].x && p.y > points[0].y && p.x < points[1].x && p.y < points[1].y)
            return true;
        return false;
    }
}

class Circle extends Shape {

    private static final long serialVersionUID = 1L;

    public Circle(Point p1, Point p2) {
        super(p1, p2);
    }

    @Override
    protected void Draw(Graphics2D g) {
        g.setStroke(new BasicStroke(thickness)); // set the thickness
        g.setColor(color); // set the color
        Point[] points = adjustPoint();
        g.drawOval(points[0].x, points[0].y, points[1].x - points[0].x, points[1].x - points[0].x);
    }

    @Override
    protected boolean isSelected(Point p) {
        Point[] points = adjustPoint();
        double centerX = (points[0].x + points[1].x) / 2;
        double centerY = (points[0].y + points[1].y) / 2;
        if (Math.sqrt(Math.pow(p.x - centerX, 2) + Math.pow(p.y - centerY, 2))
                - Math.sqrt(Math.pow(points[0].x - centerX, 2) + Math.pow(points[0].y - centerY, 2)) <= delta)
            return true;
        return false;
    }
}

class Text extends Shape {

    private static final long serialVersionUID = 1L;

    private String str;

    public Text(Point p1, Point p2, String s) {
        super(p1, p2);
        str = s;
    }

    @Override
    public void Draw(Graphics2D g) {
        g.setColor(color);
        Point points[] = adjustPoint();
        g.setFont(new Font("宋体", Font.PLAIN, (points[1].y - points[0].y) / 2));
        g.drawString(str, points[0].x, points[1].y);
    }

    @Override
    protected boolean isSelected(Point p) {
        Point[] points = adjustPoint();
        if (p.x > points[0].x && p.y > points[0].y && p.x < points[1].x && p.y < points[1].y)
            return true;
        return false;
    }

    public void setText(String s) {
        str = s;
    }
}