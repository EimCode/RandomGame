package Classes;

import java.awt.*;

public abstract class Shape {
    protected Color color;

    public Shape(Color c){
        this.color = c;
    }

    abstract public void applyStyle(Graphics2D g, int x, int y);
}
