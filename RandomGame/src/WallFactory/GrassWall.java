package WallFactory;

import java.awt.*;

public class GrassWall implements Wall {

    @Override
    public void draw(Graphics2D g, int x, int y, int tsw) {
        g.setColor(Color.green);
        g.fillRect(x, y, tsw, tsw);
    }
}
