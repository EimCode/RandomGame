package Classes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import WallFactory.*;

import javax.swing.ImageIcon;
import java.util.Random; 
import java.awt.Color;
public class TileMap {
	
	private int x;
	private int y;
	
	private int tileSize;
	public int[][] map;
	private int mapWidth;
	private int mapHeight;
	public Color color;
	private static TileMap instance;
	private boolean canColor = false;
	WallFactory wallFactory = new WallFactory();
	
	public TileMap(int[][] map, int tileSize) {
		this.tileSize = tileSize;
		
		this.map = map;
		this.mapHeight = map.length;
		this.mapWidth = map[0].length;
		color = Color.WHITE;
	}
	
	public static TileMap getInstance(int [][] map){
        if(instance == null){
            instance = new TileMap(map, 40);
        }
        return instance;
    }
	
	public void update() {
	}
	
	public Boolean Contains(int number)
	{
		for(int i = 0; i < mapHeight; i++)
		{
			for(int j = 0; j < mapWidth; j++)
			{
				if(map[i][j] == number)
				{
					return true;
				}
			}
		}
		
		return false;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public Boolean PickUp(int number, int x, int y)
	{		
		if(map[y][x] == number)
		{
			map[y][x] = 0;			
			return true;
		}
		return false;
	}
	
	public int[] randomPlace(int weaponNumber)
	{
		Random rand = new Random();
		int col = 0, row = 0;
		int rc = -1;
		while(rc != 0) {
			col= rand.nextInt(48);
			row = rand.nextInt(27);
			rc = map[row][col];
		}
		map[row][col] = weaponNumber;
		return new int[] {row, col};
	}
	
	public boolean checkWall(int x, int y)
	{
		if(map[y][x] == 1 || map[y][x] == 2 || map[y][x] == 3 || map[y][x] == 4)
			return true;
		return false;
	}
	
	public void draw(Graphics2D g) {
		for(int row = 0; row < mapHeight; row++) {
			for(int col = 0; col < mapWidth; col++) {
				
				int rc = map[row][col];
				
				if(rc == 1) {
					Wall wall = new RedWallDecorator(wallFactory.getWall("GrassWall"));
					wall.draw(g, x + col * tileSize, y + row * tileSize, tileSize);
				}
				else if(rc == 2) {
					Wall wall = new BlueWallDecorator(wallFactory.getWall("DirtWall"));
					wall.draw(g, x + col * tileSize, y + row * tileSize, tileSize);
				}
				else if(rc == 3) {
					Wall wall = new YellowWallDecorator(wallFactory.getWall("StoneWall"));
					wall.draw(g, x + col * tileSize, y + row * tileSize, tileSize);
				}
				//else if (color != null){
				//	g.setColor(color);
				//	g.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
				//}
				else {
					g.setColor(color);
					g.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
				}
			}
		}
	}
}

