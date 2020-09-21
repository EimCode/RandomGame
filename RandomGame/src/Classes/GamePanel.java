package Classes;

import java.awt.*;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;

import Abstract.*;


import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, ActionListener, KeyListener {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    private Thread thread;
    private boolean running;

    private BufferedImage image;
    private Graphics2D g;

    private int FPS = 30;
    private int targetTime = 1000 / FPS;

    public TileMap tileMap;
    private Tank tank;
    public ArrayList<Tank> tanks = new ArrayList<>();

    public ArrayList<AbstractFactory> abstractFactories = new ArrayList<>();
    private Utility unit;
    private Utility unit1;
    public ArrayList<Utility> utilities = new ArrayList<>();
    public Shape[] bullets = {new Square(new RedColor()), new Square(new GreenColor()), new Rectangle(new RedColor()), new Rectangle(new GreenColor())};
    Image akImg;
    //utilList. new String[]{"Images/ak.jpg", "Images/hp.png", "Images/m4.png" });

    int[] rand;
    Random random = new Random();

    public GamePanel(int[][] map) {
        super();
        setTileMap(map);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);

        ImageIcon icon = new ImageIcon();
        akImg = icon.getImage();
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void run() {
        init();

        long startTime;
        long urdTime;
        long waitTime;


        while (running) {
            startTime = System.nanoTime();

            update();
            render();
            draw();
            urdTime = (System.nanoTime() - startTime) / 100000000;
            waitTime = targetTime - urdTime;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {

            }
        }
    }

    public void updateTank(int x, int y, String name) {
        int index = findTank(name);
        tanks.get(index).setPosition(x, y);
    }

    public void updateProjectile(int x, int y, String name) {
        int index = findTank(name);
        tanks.get(index).setPosition(x, y);
    }

    public void addUtility(int x, int y, String name) {
        if(name.contains("hp")){
            utilities.add(abstractFactories.get(1).createUtility(y, x, name));
        }
        else{
            utilities.add(abstractFactories.get(0).createUtility(y, x, name));
        }
    }

    public int findTank(String name) {
        for (int i = 0; i < tanks.size(); i++) {
            if (tanks.get(i).name.equals(name))
                return i;
        }
        return -1;
    }

    public int findUtility(int x, int y) {
        for (int i = 0; i < utilities.size(); i++) {
            if (utilities.get(i).x == x && utilities.get(i).y == y) {
                return i;
            }
        }
        return -1;
    }

    public void printTileMap() {
        for (int i = 0; i < tileMap.map.length; i++) {
            for (int j = 0; j < tileMap.map[i].length; i++) {
            }
        }
    }

    private void updateProjectiles()
    {
        ArrayList<Projectile> projectiles = tank.getProjectiles();
        for(int i = 0; i < projectiles.size(); i++)
        {
            Projectile projectile = projectiles.get(i);

            if(projectile.x > 0 && projectile.y > 0 && projectile.x < 1920 && projectile.y < 1080)
            {
                projectile.move();
            }
            else {
                projectiles.remove(i);
            }
        }
    }

    public void changeTankColor(String color)
    {
        for(int i = 1; i < tanks.size(); i++)
        {
            Tank tank = tanks.get(i);
            tank.setPhoto(color);
        }
    }

    private void updateTanks()
    {
        for(int i = 0; i < tanks.size(); i++)
        {
            Tank tank = tanks.get(i);
            tank.update();
        }
    }

    private void init() {
        abstractFactories.add(new DamageFactory());
        abstractFactories.add(new HealthFactory());
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        //tileMap = TileMap.getInstance();
        System.out.println(tileMap);
        //int[] rand = tileMap.randomPlace(10);
        //tank = Tank.getInstance(rand[1], rand[0], 2, true);
    }

    public void setTileMap(int[][] map) {
        this.tileMap = TileMap.getInstance(map);
    }

    private void update() {
        tileMap.update();
        for (Tank tank : tanks) {
            tank.update();
            ArrayList<Projectile> projectiles = tank.getProjectiles();
            for(int i = 0; i < projectiles.size(); i++)
            {
                Projectile projectile = projectiles.get(i);

                if(projectile.x > 0 && projectile.y > 0 && projectile.x < 1920 && projectile.y < 1080 && !tileMap.checkWall(projectile.x/40, projectile.y/40))
                {
                    projectile.move();
                }
                else {
                    projectiles.remove(i);
                }
            }
        }
        //System.out.println(tanks.size());
        //unit.update();
    }

    private void render() {
        tileMap.draw(g);
        for (Tank tank : tanks) {
            tank.draw(g);
            ArrayList<Projectile> projectiles = tank.getProjectiles();
            for(Projectile projectile : projectiles)
            {
                projectile.draw(g, bullets);
            }
        }
        for (Utility util : utilities) {
            util.draw(g, null);
        }
    }

    private void draw() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateTanks();
        updateProjectiles();
            repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        for (Tank tank : tanks) {
            tank.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        for (Tank tank : tanks) {
            tank.keyReleased(e);
        }
    }
}

