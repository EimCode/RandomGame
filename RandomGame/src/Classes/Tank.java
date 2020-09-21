package Classes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;

import Abstract.AbstractFactory;
import Abstract.DamageFactory;
import Abstract.Projectile;
import com.microsoft.signalr.HubConnection;

public class Tank {
    int x, y;
    public int[] prevPos;
    private int xVel = 0;
    private int yVel = 0;
    public int[][] temp;
    private TileMap tileMap = TileMap.getInstance(temp);
    String name;
    boolean isLocal;
    int points;
    public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    public int health;
    public int range;
    Graphics2D g;
    BufferedImage image;
    Rectangle bullet;
    boolean lDir,rDir,dDir, uDir = false;
    long lastShotTime = System.currentTimeMillis();
    public Memento defaultHealth;
    HubConnection connection;
    String photo;
    public Projectile proj;
    public int shootSpeed;


    public Tank(int x, int y, String name, boolean isLocal, HubConnection connection) {
        image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        this.range = 3;
        this.x = x;
        this.y = y;
        this.prevPos = new int[]{this.x, this.y};
        this.name = name;
        this.isLocal = isLocal;
        this.points = 0;
        this.health = 100;
        this.connection = connection;
        this.shootSpeed = 40;
        this.defaultHealth = new Memento(this.health);
        if(isLocal)
            photo = "Images/green.png";
        else
            photo = "Images/red.png";
    }

    public void sendCoordinates(HubConnection connection)
	{
		connection.send("UpdateCoords", name, x, y);
    }

    public void shootCoordinates(HubConnection connection)
    {
        connection.send("ProjectileCoords", x, y);
    }


    public void setSpeed(int speed)
    {
        this.shootSpeed = speed;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public int[] getPosition() {
        return new int[]{this.x, this.y};
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*public void createProjectile(Graphics2D g)
    {
        g.setColor(Color.blue);
        g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
    }*/

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public void fire()
    {
        int temp;
        if(uDir)
        {
            projectiles.add(new Projectile(x*40, y*40, "up", this.shootSpeed, 0, 0));
            temp = 0;
            connection.send("ProjectileCoords", x, y, "up", temp);
        }
        if(rDir)
        {
            projectiles.add(new Projectile(x*40, y*40, "right", this.shootSpeed, 0, 1));
            temp = 1;
            connection.send("ProjectileCoords", x, y, "right", temp);
        }
        if(dDir)
        {
            projectiles.add(new Projectile(x*40, y*40, "down", this.shootSpeed, 0, 2));
            temp = 2;
            connection.send("ProjectileCoords", x, y, "down", temp);
        }
        if(lDir)
        {
            projectiles.add(new Projectile(x*40, y*40, "left", this.shootSpeed, 0, 3));
            temp = 3;
            connection.send("ProjectileCoords", x, y, "left", temp);
        }
    }

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

    public boolean posChanged(int x, int y) {
        if (this.x == x && this.y == y)
            return false;
        return true;
    }

    public void update() {
    	int startX = x;
    	int startY = y;

        x += xVel;
        y += yVel;

        if (x < 0) {
            x = 0;
        } else if (y < 0) {
            y = 0;
        } else if (x > 47) {
            x = 47;
        } else if (y > 26) {
            y = 26;
        } else if (tileMap.checkWall(x, y)) {
			x -= xVel;
			y -= yVel;
		}
        else if ((x != startX || y != startY) && isLocal){
			this.sendCoordinates(connection);
			//System.out.println("siuncia coords");
		}
//        else if (tileMap.PickUp(5, x, y)) {
//            state += 1;
//            System.out.println("State changed " + getState());
//            setState(state);
//        }



    }

    public void draw(Graphics2D g) {
        ImageIcon image = new ImageIcon(photo);
        g.drawImage(image.getImage(), x * 40, y * 40, 40, 40, null);
    }

    public void keyPressed(KeyEvent e) {
        if (!this.isLocal)
            return;
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            yVel = -1;
            uDir = true;
        } else if (key == KeyEvent.VK_S) {
            yVel = 1;
            dDir = true;
        } else if (key == KeyEvent.VK_A) {
            xVel = -1;
            lDir = true;
        } else if (key == KeyEvent.VK_D) {
            xVel = 1;
            rDir = true;
        } else if (key == KeyEvent.VK_SPACE) {
            if(System.currentTimeMillis() - lastShotTime > 100)
            {
                fire();
                lastShotTime = System.currentTimeMillis();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (!this.isLocal)
            return;
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            yVel = 0;
            uDir = false;
        } else if (key == KeyEvent.VK_S) {
            yVel = 0;
            dDir = false;
        } else if (key == KeyEvent.VK_A) {
            xVel = 0;
            lDir = false;
        } else if (key == KeyEvent.VK_D) {
            xVel = 0;
            rDir = false;
        } /*else if (key == KeyEvent.VK_SPACE)
        {
            readyToFire = false;
            if(bullet.y <= 0 || bullet.x <= 0 || bullet.y >= 1080 || bullet.x >= 1920)
            {
                bullet = new Rectangle(0,0,0,0);
                shot = false;
                readyToFire = true;
            }
        }*/

    }

    public void restoreFromMemento(Memento memento)
    {
        health = memento.getSavedHealth();
        System.out.println("Health restored from Memento: " + health);
    }
}
