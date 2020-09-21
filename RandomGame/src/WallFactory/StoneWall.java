package WallFactory;

import java.awt.*;

public class StoneWall implements Wall {

    @Override
    public void draw(Graphics2D g, int x, int y, int tsw) {
        g.setColor(Color.gray);
        g.fillRect(x, y, tsw, tsw);
    }
}
