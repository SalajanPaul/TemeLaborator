import java.awt.*;

abstract class CustomShape {
    int startX, startY, endX, endY;
    Color color;

    public CustomShape(int startX, int startY, Color color) {
        this.startX = startX;
        this.startY = startY;
        this.color = color;
    }

    public void setEnd(int endX, int endY) {
        this.endX = endX;
        this.endY = endY;
    }

    public abstract void draw(Graphics2D g);
}

class CustomCircle extends CustomShape {
    public CustomCircle(int startX, int startY, Color color) {
        super(startX, startY, color);
    }

    @Override
    public void draw(Graphics2D g) {
        int diameter = Math.max(Math.abs(endX - startX), Math.abs(endY - startY));
        g.drawOval(Math.min(startX, endX), Math.min(startY, endY), diameter, diameter);
    }
}

class CustomRectangle extends CustomShape {
    public CustomRectangle(int startX, int startY, Color color) {
        super(startX, startY, color);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
    }
}