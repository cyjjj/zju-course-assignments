package miniCAD;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.*;

public class Control {
    public static Model model;
    public static paintListener pl = new paintListener();
    public static String state = "idle";

    public Control(Model m) {
        model = m;
    }

    public static void updateView() {
        Model.update();
    }

}

class paintListener implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
    private Shape selectedShape;
    private Point p1;
    private Point p2;
    private String text;
    private ArrayList<Point> initP = new ArrayList<>(); // position of endponits before moving

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) { // keyboard
        if (selectedShape != null) {
            if (e.getKeyChar() == '+' || e.getKeyChar() == '=')
                selectedShape.resize(1); // bigger
            else if (e.getKeyChar() == '_' || e.getKeyChar() == '-')
                selectedShape.resize(0); // bigger
            else if (e.getKeyChar() == '>' || e.getKeyChar() == '.')
                selectedShape.thickness ++; // thicker
            else if (e.getKeyChar() == '<' || e.getKeyChar() == ',') {
                if (selectedShape.thickness <= 1)
                    selectedShape.thickness -= 0; // minimum thickness is 1
                else
                    selectedShape.thickness --; // thinner
            } else if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
                Control.model.getAll().remove(selectedShape); // delete
            } else if (e.getKeyChar() == 'c') { // change the content of text
                if (selectedShape instanceof Text) {
                    Text tmp = (Text) selectedShape;
                    tmp.setText(JOptionPane.showInputDialog("Input new text content: "));
                }
            }
        }
        Control.updateView(); // repaint
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (Control.state.equals("idle"))
            return;
        if (Control.state.equals("select")) {
            if (selectedShape != null) {
                for (int i = 0; i < selectedShape.v.size(); i++) {
                    selectedShape.v.get(i).x = initP.get(i).x + e.getX() - p1.x;
                    selectedShape.v.get(i).y = initP.get(i).y + e.getY() - p1.y;
                    Control.updateView();
                }
            }
        } else {
            Shape s = Control.model.getCurrent();
            s.v.get(1).x = e.getX();
            s.v.get(1).y = e.getY();
            Control.updateView();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        p1 = new Point(e.getX(), e.getY());
        p2 = new Point(e.getX(), e.getY());
        switch (Control.state) {
            case "select":
                selectedShape = findSelected(p1); // find the select shape
                if (selectedShape != null) {
                    initP.clear();
                    for (int i = 0; i < selectedShape.v.size(); i++) {
                        initP.add(new Point(selectedShape.v.get(i).x, selectedShape.v.get(i).y));
                    }
                }
                break;
            case "line":
                Control.model.add(new Line(p1, p2));
                selectedShape = null;
                break;
            case "rect":
                Control.model.add(new Rect(p1, p2));
                selectedShape = null;
                break;
            case "circle":
                Control.model.add(new Circle(p1, p2));
                selectedShape = null;
                break;
            case "text":
                Control.model.add(new Text(p1, p2, text));
                selectedShape = null;
                break;
            default:
                selectedShape = null;
                break;
        }
    }

    private Shape findSelected(Point p) {
        for (Shape shape : Control.model.getAll()) {
            if (shape.isSelected(p))
                return shape;
        }
        return null;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnName = e.getActionCommand();
        switch (btnName) {
            case "Select":
                Control.state = "select";
                break;
            case "Line":
                Control.state = "line";
                break;
            case "Rectangle":
                Control.state = "rect";
                break;
            case "Circle":
                Control.state = "circle";
                break;
            case "Text":
                Control.state = "text";
                text = JOptionPane.showInputDialog("Input text content: ");
                break;
            case "k":
            case "e":
            case "c":
            case "d":
            case "g":
            case "n":
            case "l":
            case "p":
            case "o":
            case "r":
            case "w":
            case "y": // colors
                if (selectedShape != null) {
                    int i = 0;
                    for (i = 0; i < CADmain.colorName.length; i++) {
                        if (btnName.equals(CADmain.colorName[i]))
                            break;
                    }
                    selectedShape.color = CADmain.clr[i];
                }
                Control.updateView();
                break;
            case "New...":
                newFile();
                break;
            case "Open...":
                openFile();
                break;
            case "Save...":
                saveFile();
                break;
            case "Manual":
                showManual();
                break;
            default:
                Control.state = "idle";
                break;
        }

    }

    private void showManual() {
        String mymanual = "*Draw graphics:\n  You can draw lines, rectangles, circles and strings four basic graphics.\n  Hold down the left mouse button and drag until the left mouse button is released to determine the final shape.\n  If the drawing type is a string, the drag will bring up the Dialog Dialog box, and the user can enter a string\n  and then drag the mouse to determine the position and size.\n\n*Delete graphics on artboard:\n  First select a graphic on artboard and press the 'R' key to delete the graphic.\n\n*Moving graphics:\n  Select a graphic on the artboard and drag the mouse to move the position of the graphic.\n\n*Change graphic thickness:\n  Select a graphic on the artboard and press '>' to make the graph thicker,\n  press '<' Key to make the graph thinner.\n\n*Resize a graph:\n  Select a graph on the artboard and press the '+' key to make the graph bigger.\n  Press the '-' key to make the graph smaller.\n\n*Change graphic color:\n  Select a graphic on the artboard, click the color button in the toolbar,\n  and the graphic color changes to the color on the color button.\n\n*Create new, save and open a File:\n  Click the File menu with the left mouse button to choose to create a new blank file;\n  save the current content to *.cad file; open an existing file and import the contents of the file into the artboard.\n  The graphics imported into the artboard can still be manipulated.\n\n ---More fuctions waited to be updated---";
        JOptionPane.showMessageDialog(null, mymanual, "Manual", JOptionPane.PLAIN_MESSAGE);
    }

    private void newFile() {
        int value = JOptionPane.showConfirmDialog(null, "Whether to save current file first?", null, 0);
        if (value == 0)
            saveFile();
        else if (value == 1) {
            Control.model.removeAll();
            Control.updateView();
        }
    }

    @SuppressWarnings("unchecked")
    private void openFile() {
        int value = JOptionPane.showConfirmDialog(null, "Whether to save current file first?", null, 0);
        if (value == 0)
            saveFile();
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Open *.cad file");
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            if (file == null)
                JOptionPane.showMessageDialog(null, "Not choose file!");
            else {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                Control.model.setAll((ArrayList<Shape>) in.readObject());
                Control.updateView();
                in.close();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Fail to open!");
        }
    }

    private void saveFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        chooser.setDialogTitle("Save File");
        File file = chooser.getSelectedFile();

        if (file == null)
            JOptionPane.showMessageDialog(null, "Not choose file!");
        else {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(Control.model.getAll());
                JOptionPane.showMessageDialog(null, "Successfully saved!");
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Fail to save!");
            }
        }

    }
}