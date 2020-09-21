package Handlers;

import Classes.GameWindow;
import Classes.ProjectileSpeedExpression;

public class ProjectileSpeedHandler extends CommandHandler {
    public ProjectileSpeedHandler(CommandHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    void doAction(GameWindow game, String command) {
        ProjectileSpeedExpression exp = new ProjectileSpeedExpression();
        exp.interpret(command);
        game.gp.tanks.get(0).setSpeed(exp.speed);
    }

    @Override
    boolean canHandle(String command) {
        return command.startsWith("setMySpeed");
    }
}
