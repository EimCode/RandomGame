package Abstract;

import Classes.Shape;
import Classes.GreenColor;
import Classes.RedColor;
import Classes.Square;
import Classes.Rectangle;
import WallFactory.BlueWallDecorator;
import WallFactory.Wall;
import WallFactory.WallFactory;

import javax.swing.*;
import java.awt.*;

public class Projectile extends AbstractObject {

	String name;
	double damage;
	public int speed;
	int direction;
	Shape square1, square2, rect1, rect2;

	public Projectile(int x, int y, String name, int speed, double damage, int direction)
	{
		this.x = x;
		this.y = y;
		this.name = name;
		this.speed = speed;
		this.damage = damage;
		this.direction = direction;
	}

	@Override
	public void draw(Graphics2D g, Shape[] bullets) {
		//g.setColor(Color.blue);
		//g.fillRect(x, y, 20, 20);
		if(this.name.startsWith("u")){
//			Shape square = new Square(new RedColor());
			bullets[0].applyStyle(g, x, y);
		}
		if(this.name.startsWith("d")){
//			Shape square = new Square(new GreenColor());
			bullets[1].applyStyle(g, x, y);
		}
		if(this.name.startsWith("l")){
//			Shape rect = new Rectangle(new RedColor());
			bullets[2].applyStyle(g, x, y);
		}
		if(this.name.startsWith("r")){
//			Shape rect = new Rectangle(new GreenColor());
			bullets[3].applyStyle(g, x, y);
		}
	}

	public void move()
	{
		if(direction == 0)
			y -= this.speed;
		if(direction == 1)
			x += this.speed;
		if(direction == 2)
			y += this.speed;
		if(direction == 3)
			x -= this.speed;
	}
}
