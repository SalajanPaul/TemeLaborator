import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GeometricFigures extends JFrame {
    private JToggleButton circleButton;
    private JToggleButton rectangleButton;
    private JToggleButton freeformButton;
    private JButton colorButton;
    private JButton deleteButton; // Added delete button
    private Color currentColor = Color.BLACK;
    private CustomShapePanel shapePanel;

    public GeometricFigures() {
        setTitle("Geometric Figures");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel controlPanel = new JPanel();
        circleButton = new JToggleButton("Circle");
        rectangleButton = new JToggleButton("Rectangle");
        freeformButton = new JToggleButton("Mouse drag");
        colorButton = new JButton("Color");
        deleteButton = new JButton("Delete"); // Initialize delete button

        ButtonGroup group = new ButtonGroup();
        group.add(circleButton);
        group.add(rectangleButton);
        group.add(freeformButton);

        controlPanel.add(circleButton);
        controlPanel.add(rectangleButton);
        controlPanel.add(freeformButton);
        controlPanel.add(colorButton);
        controlPanel.add(deleteButton); // Add delete button to control panel

        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose a color", currentColor);
            if (newColor != null) {
                currentColor = newColor;
            }
        });

        deleteButton.addActionListener(e -> {
            shapePanel.clearShapes(); // Clear all shapes
        });

        shapePanel = new CustomShapePanel();
        add(controlPanel, BorderLayout.EAST);
        add(shapePanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GeometricFigures frame = new GeometricFigures();
            frame.setVisible(true);
        });
    }

    private class CustomShapePanel extends JPanel implements MouseListener, MouseMotionListener {
        private ArrayList<CustomShape> shapes = new ArrayList<>();
        private CustomShape currentShape = null;
        private FreeformCurve currentCurve = null;

        public CustomShapePanel() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            for (CustomShape shape : shapes) {
                g2d.setColor(shape.color);
                shape.draw(g2d);
            }
            if (currentShape != null) {
                g2d.setColor(currentShape.color);
                currentShape.draw(g2d);
            }
            if (currentCurve != null) {
                g2d.setColor(currentCurve.color);
                currentCurve.draw(g2d);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (circleButton.isSelected()) {
                currentShape = new CustomCircle(e.getX(), e.getY(), currentColor);
            } else if (rectangleButton.isSelected()) {
                currentShape = new CustomRectangle(e.getX(), e.getY(), currentColor);
            } else if (freeformButton.isSelected()) {
                currentCurve = new FreeformCurve(currentColor);
                currentCurve.addPoint(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (currentShape != null) {
                currentShape.setEnd(e.getX(), e.getY());
                shapes.add(currentShape);
                currentShape = null;
                repaint();
            } else if (currentCurve != null) {
                currentCurve.addPoint(e.getX(), e.getY());
                shapes.add(currentCurve);
                currentCurve = null;
                repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (currentShape != null) {
                currentShape.setEnd(e.getX(), e.getY());
                repaint();
            } else if (currentCurve != null) {
                currentCurve.addPoint(e.getX(), e.getY());
                repaint();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
        @Override
        public void mouseMoved(MouseEvent e) {}

        public void clearShapes() {
            shapes.clear();
            repaint();
        }
    }

    private abstract class CustomShape {
        protected Color color;

        public CustomShape(Color color) {
            this.color = color;
        }

        public abstract void draw(Graphics2D g2d);
        public abstract void setEnd(int x, int y);
    }

    private class CustomCircle extends CustomShape {
        private int x, y, width, height;

        public CustomCircle(int x, int y, Color color) {
            super(color);
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Graphics2D g2d) {
            g2d.drawOval(x, y, width, height);
        }

        @Override
        public void setEnd(int x, int y) {
            width = Math.abs(x - this.x);
            height = Math.abs(y - this.y);
        }
    }

    private class CustomRectangle extends CustomShape {
        private int x, y, width, height;

        public CustomRectangle(int x, int y, Color color) {
            super(color);
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Graphics2D g2d) {
            g2d.drawRect(x, y, width, height);
        }

        @Override
        public void setEnd(int x, int y) {
            width = Math.abs(x - this.x);
            height = Math.abs(y - this.y);
        }
    }

    private class FreeformCurve extends CustomShape {
        private ArrayList<Point> points = new ArrayList<>();

        public FreeformCurve(Color color) {
            super(color);
        }

        public void addPoint(int x, int y) {
            points.add(new Point(x, y));
        }

        @Override
        public void draw(Graphics2D g2d) {
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        @Override
        public void setEnd(int x, int y) {
            addPoint(x, y);
        }
    }
}
