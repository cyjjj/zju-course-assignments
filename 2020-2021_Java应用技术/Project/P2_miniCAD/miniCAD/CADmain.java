package miniCAD;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class CADmain extends JFrame {

    private static final long serialVersionUID = 1L;
    public static String[] shape = { "Select", "Line", "Rectangle", "Circle", "Text" };
    public static String[] colorName = { "k", "e", "c", "d", "g", "n", "l", "p", "o", "r", "w", "y" };
    public static Color[] clr = { Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green,
            Color.lightGray, Color.pink, Color.orange, Color.red, Color.white, Color.yellow };

    public CADmain() {
        setTitle("MiniCAD");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        // Menu
        // file option
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("File");
        JMenuItem menuItem1 = new JMenuItem("New...");
        JMenuItem menuItem2 = new JMenuItem("Open...");
        JMenuItem menuItem3 = new JMenuItem("Save...");
        menuItem1.addActionListener(Control.pl);
        menuItem2.addActionListener(Control.pl);
        menuItem3.addActionListener(Control.pl);
        // menuItem1.setFocusable(true);
        // menuItem2.setFocusable(true);
        // menuItem3.setFocusable(true);
        menuItem1.addKeyListener(Control.pl);
        menuItem2.addKeyListener(Control.pl);
        menuItem3.addKeyListener(Control.pl);
        menu1.add(menuItem1);
        menu1.add(menuItem2);
        menu1.add(menuItem3);
        menuBar.add(menu1);
        // manual
        JMenu menu2 = new JMenu("Help");
        JMenuItem menuItem4 = new JMenuItem("Manual");
        // menuItem4.setFocusable(true);
        menuItem4.addKeyListener(Control.pl);
        menuItem4.addActionListener(Control.pl);
        menu2.add(menuItem4);
        menuBar.add(menu2);

        // Paint Choice
        JPanel choicePanel = new JPanel();
        choicePanel.setBackground(Color.black);
        choicePanel.setLayout(new GridLayout(6, 1));
        for (int i = 0; i < 5; i++) {
            JButton button = new JButton(shape[i]);
            button.setBackground(Color.white);
            button.setPreferredSize(new Dimension(20, 80));
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.addActionListener(Control.pl);
            // button.setFocusable(true);
            button.addKeyListener(Control.pl);
            choicePanel.add(button);
        }
        // Paint Colors
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.black);
        colorPanel.setLayout(new GridLayout(4, 3));
        for (int i = 0; i < 12; i++) {
            JButton button = new JButton(colorName[i]);
            button.addActionListener(Control.pl);
            // button.setFocusable(true);
            button.addKeyListener(Control.pl);
            button.setForeground(clr[i]); // not show the color name
            button.setBackground(clr[i]); // show the color
            colorPanel.add(button);
        }
        choicePanel.add(colorPanel);

        setJMenuBar(menuBar); // at the top -- file/help menu
        add(choicePanel, BorderLayout.EAST); // east -- choice menu
        this.setFocusable(true);
        this.addKeyListener(Control.pl);
    }

    public static void main(String[] args) {
        CADmain window = new CADmain();
        ArrayList<Shape> shapes = new ArrayList<>();
        View view = new View();
        Model model = new Model(shapes, view);
        window.add(view, BorderLayout.CENTER); // draw shapes here
        window.setVisible(true);
        @SuppressWarnings("unused")
        Control ctrl = new Control(model);
    }
}
