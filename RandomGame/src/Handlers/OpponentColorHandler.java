package Handlers;

import Classes.GameWindow;
import Classes.OpponentColorExpression;

public class OpponentColorHandler extends CommandHandler {

    public OpponentColorHandler(CommandHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    void doAction(GameWindow game, String command) {
        OpponentColorExpression exp = new OpponentColorExpression();
        exp.interpret(command);
        game.gp.changeTankColor(exp.photo);
    }

    @Override
    boolean canHandle(String command) {
        return command.startsWith("setOpponentColor");
    }
}
