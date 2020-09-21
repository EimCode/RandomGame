package Handlers;

import Classes.GameWindow;
import Classes.MapColorExpression;
import Server.GameClient;

public class MapColorHandler extends CommandHandler {

    public MapColorHandler(CommandHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    void doAction(GameWindow game, String command) {
        MapColorExpression exp = new MapColorExpression();
        exp.interpret(command);
        game.gp.tileMap.setColor(exp.color);
    }

    @Override
    boolean canHandle(String command) {
        return command.startsWith("setMapColor");
    }
}