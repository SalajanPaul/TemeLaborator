import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GeometricFigures extends JFrame {
    private JToggleButton circleButton;
    private JToggleButton rectangleButton;
    private JButton colorButton;
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
        colorButton = new JButton("Color");

        ButtonGroup group = new ButtonGroup();
        group.add(circleButton);
        group.add(rectangleButton);

        controlPanel.add(circleButton);
        controlPanel.add(rectangleButton);
        controlPanel.add(colorButton);

        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose a color", currentColor);
            if (newColor != null) {
                currentColor = newColor;
            }
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
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (circleButton.isSelected()) {
                currentShape = new CustomCircle(e.getX(), e.getY(), currentColor);
            } else if (rectangleButton.isSelected()) {
                currentShape = new CustomRectangle(e.getX(), e.getY(), currentColor);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (currentShape != null) {
                currentShape.setEnd(e.getX(), e.getY());
                shapes.add(currentShape);
                currentShape = null;
                repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (currentShape != null) {
                currentShape.setEnd(e.getX(), e.getY());
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
    }
}

