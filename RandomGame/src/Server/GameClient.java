package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Abstract.DamageUtility;
import Abstract.HealthUtility;
import Abstract.Projectile;
import Classes.*;
import Handlers.*;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;


/**
 * A simple Swing-based client for the chat server. Graphically it is a frame with a text
 * field for entering messages and a textarea to see the whole dialog.
 *
 * The client follows the following Chat Protocol. When the server sends "SUBMITNAME" the
 * client replies with the desired screen name. The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are already in use. When the
 * server sends a line beginning with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all chatters connected to the
 * server. When the server sends a line beginning with "MESSAGE" then all characters
 * following this string should be displayed in its message area.
 */
public class GameClient {

    public static String serverAddress;
    Scanner in;
    PrintWriter out;
    JTextField textField = new JTextField(50);
    public Main main;
    String name;
    HubConnection connection;
    VisitHandler handler;
    private CommandHandler commandHandler = new MapColorHandler( new MyColorHandler(new OpponentColorHandler(new ProjectileSpeedHandler(null))));

    /**
     * Constructs the client by laying out the GUI and registering a listener with the
     * textfield so that pressing Return in the listener sends the textfield contents
     * to the server. Note however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED message from
     * the server.
     */
    public GameClient(String serverAddress) {
        handler = new VisitHandler("");
        main = new Main();
        this.serverAddress = serverAddress;

        connection = HubConnectionBuilder
                .create("http://localhost:60203/api/signalr")
                .build();

        connection.start();
        connection.onClosed((x) -> System.out.println(x));

        connection.on("ReceiveMap", (map) -> {
            main.startGame(map);
            name = getName(main.getGameWindow().window);
            connection.send("PlayerJoined", name);
        }, int[][].class);

        connection.on("ReceiveObjects", (position, tanks, utilities) -> {
            addPlayerToGame(position, name, true);
            addOpponentsToGame(tanks);
            spawnUtilities(utilities);
        }, int[].class, ObjectFragment[].class, ObjectFragment[].class);

        connection.on("ReceiveOpponentCoords", (name, position) -> {
            addPlayerToGame(position, name, false);
        }, String.class, int[].class);

        connection.on("OpponentLeft", (name) -> {
            removePlayer(name);
        }, String.class);

        connection.on("UpdatedCoords", (name, x, y) -> {
            updateTank(name, x, y);
        }, String.class, Integer.class, Integer.class);

        connection.on("SpawnedUtility", (name, position) -> {
            spawnUtility(position, name);
        }, String.class, int[].class);

        connection.on("PickedUtility", (tankName, utility) -> {
            useUtility(tankName, utility);
        }, String.class, ObjectFragment.class);

        textField.setEditable(false);
        //messageArea.setEditable(false);
        //frame.getContentPane().add(textField, BorderLayout.SOUTH);
        //frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        //frame.pack();

        connection.on("RemoveUtility", (x, y) -> {
            removeUtility(x, y);
        }, Integer.class, Integer.class);

        connection.on("SpawnProjectile", (x, y, type, direction) -> {
            spawnProjectile(x, y, type, direction);
        }, Integer.class, Integer.class, String.class, Integer.class);

        // Send on enter then clear to prepare for next message
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });
    }

    private String getName(JFrame frame) {
        return JOptionPane.showInputDialog(
           frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    private void run() throws IOException, InterruptedException {
        Thread.sleep(5000);
        connection.send("GetMap");


        while(true){
            readConsole();
        }
    }

    void readConsole(){
        Scanner scanner = new Scanner(System.in);
        String n = scanner.nextLine();
        commandHandler.handle(main.getGameWindow(), n);
    }

    private void useUtility(String tankName, ObjectFragment objFrag)
    {
        int index = main.getGameWindow().gp.findTank(tankName);

        if(objFrag.name.contains("ak") || (objFrag.name.contains("m4"))) {
            DamageUtility util = (DamageUtility)main.getGameWindow().gp.utilities.get(main.getGameWindow().gp.findUtility(objFrag.x, objFrag.y));
            util.accept(main.getGameWindow().gp.tanks.get(index), handler);
        }
        if(objFrag.name.contains("hp")) {
            HealthUtility util = (HealthUtility)main.getGameWindow().gp.utilities.get(main.getGameWindow().gp.findUtility(objFrag.x, objFrag.y));
            util.accept(main.getGameWindow().gp.tanks.get(index), handler);

        }
    }

    private void spawnProjectile(int x, int y, String type, int direction)
    {
        main.getGameWindow().gp.tanks.get(0).projectiles.add(new Projectile(x*40, y*40, type, main.getGameWindow().gp.tanks.get(0).shootSpeed, 1, direction));
    }

    private void updateTank(String name, int x, int y)
    {
        main.getGameWindow().gp.updateTank(x, y, name);
    }

    private void removeUtility(int x, int y)
    {
        main.getGameWindow().gp.utilities.remove(main.getGameWindow().gp.findUtility(x, y));
    }

    private void removePlayer(String name)
    {
        main.getGameWindow().gp.tanks.remove(main.getGameWindow().gp.findTank(name));
    }

    private void spawnUtilities(ObjectFragment[] objFrag)
    {
        for(int i = 0; i < objFrag.length; i++)
        {
            spawnUtility(new int[]{objFrag[i].x, objFrag[i].y}, objFrag[i].name);
        }
    }

    private void spawnUtility(int[] position, String name)
    {
            main.getGameWindow().gp.addUtility(position[1], position[0], name);
    }

    private void addPlayerToGame(int x, int y, String name, boolean isLocal)
    {
        main.getGameWindow().gp.tanks.add(new Tank(y, x, name, isLocal, connection));
    }

    private void addPlayerToGame(int[] position, String name, boolean isLocal)
    {
        main.getGameWindow().gp.tanks.add(new Tank(position[1], position[0], name, isLocal, connection));
    }

    private void addOpponentsToGame(ObjectFragment[] tanks)
    {
        for(int i = 0; i < tanks.length; i++)
        {
            addPlayerToGame(tanks[i].x, tanks[i].y, tanks[i].name, false);
        }
    }

    public static void main(String[] args) throws Exception {
        GameClient client = new GameClient("127.0.0.1");
        //client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //client.frame.setVisible(true);
        client.run();
    }
}