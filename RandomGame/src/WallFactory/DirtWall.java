package WallFactory;

import java.awt.*;

public class DirtWall implements Wall {

    @Override
    public void draw(Graphics2D g, int x, int y, int tsw) {
        g.setColor(Color.darkGray);
        g.fillRect(x, y, tsw, tsw);
    }
}
