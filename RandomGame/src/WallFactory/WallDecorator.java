package WallFactory;

import java.awt.*;

public abstract class WallDecorator implements Wall {

    protected Wall decoratedWall;

    public WallDecorator(Wall decoratedWall)
    {
        this.decoratedWall = decoratedWall;
    }

    public void draw(Graphics2D g, int x, int y, int tsw)
    {
        decoratedWall.draw(g, x, y, tsw);
    }

}
