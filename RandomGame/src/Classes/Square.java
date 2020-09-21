package Classes;

import java.awt.*;

public class Square extends Shape {

    public Square(Color c){
        super(c);
    }

    @Override
    public void applyStyle(Graphics2D g, int x, int y) {
        color.applyColor(g);
        g.fillRect(x, y, 20, 20);
    }
}
