package Handlers;

import Classes.Expression;
import Classes.GameWindow;
import Classes.MyColorExpression;

import java.awt.*;

public class MyColorHandler extends CommandHandler {
    public MyColorHandler(CommandHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    void doAction(GameWindow game, String command) {
        MyColorExpression exp = new MyColorExpression();
        exp.interpret(command);
        game.gp.tanks.get(0).setPhoto(exp.photo);
    }

    @Override
    boolean canHandle(String command) {
        return command.startsWith("setMyColor");
    }
}
