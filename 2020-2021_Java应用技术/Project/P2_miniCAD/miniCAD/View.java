package miniCAD;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class View extends JPanel {
    private static final long serialVersionUID = 1L;
    ArrayList<Shape> shapes = new ArrayList<>();

    public View() {
        setBackground(Color.WHITE);
        //setFocusable(true);
        addMouseListener(Control.pl);
        addMouseMotionListener(Control.pl);
        addKeyListener(Control.pl);
    }

    public void paintAll(ArrayList<Shape> s) {
        shapes = s;
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (!shapes.isEmpty()) {
            for (Shape shape : shapes)
                shape.Draw((Graphics2D) g);
        }
    }
}
