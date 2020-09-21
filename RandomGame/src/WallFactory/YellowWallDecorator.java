package WallFactory;

import java.awt.*;

public class YellowWallDecorator extends WallDecorator {

    public YellowWallDecorator(Wall decoratedWall) {
        super(decoratedWall);
    }

    @Override
    public void draw(Graphics2D g, int x, int y, int tsw)
    {
        decoratedWall.draw(g, x, y, tsw);
        g.setColor(Color.yellow);
        float thickness = 2;
        Stroke oldStroke = g.getStroke();
        g.setStroke(oldStroke);
        g.setStroke(new BasicStroke(thickness));
        g.drawRect(x, y, tsw, tsw);
    }

}
