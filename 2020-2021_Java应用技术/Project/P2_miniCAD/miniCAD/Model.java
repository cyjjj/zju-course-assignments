package miniCAD;

import java.util.ArrayList;

public class Model {
    protected static View view;
    private static ArrayList<Shape> shapes; // save all shapes drawn

    Model(ArrayList<Shape> s, View v) {
        view = v;
        shapes = s;
    }

    public ArrayList<Shape> getAll() {
        return shapes;
    }

    public Shape getCurrent() {
        return shapes.get(shapes.size() - 1);
    }

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public void setAll(ArrayList<Shape> s) { // used for opening the file
        shapes = s;
    }

    public void removeAll() {
        shapes.clear();
    }

    public static void update() {
        view.paintAll(shapes);
    }
}
