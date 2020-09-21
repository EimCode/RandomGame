package Abstract;

import Classes.Shape;

import java.awt.*;

public abstract class AbstractObject {
    public int x, y;

    public abstract void draw(Graphics2D g, Shape[] bullets);

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
