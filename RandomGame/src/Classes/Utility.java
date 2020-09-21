package Classes;

import Abstract.AbstractObject;

import java.awt.Graphics2D;

import javax.swing.*;

public class Utility extends AbstractObject {
	public String name;
	public double damage, range;

		
	public Utility(int x, int y, String name, double damage, double range)
	{
		this.x = x;
		this.y = y;
		this.name = name;
		this.damage = damage;
		this.range = range;
	}

	public String getPath(String name)
	{
		String path = "Images/" + name + ".png";
		return path;
	}

	@Override
	public void draw(Graphics2D g, Shape[] bullets) {
		ImageIcon image = new ImageIcon(getPath(name));
		g.drawImage(image.getImage(), x * 40, y * 40, 40, 40, null);
	}

	public Utility Clone()
	{
		return new Utility(x, y, name, damage, range);
	}


	private Object Utility() {
		// TODO Auto-generated method stub
		return null;
	}
}
