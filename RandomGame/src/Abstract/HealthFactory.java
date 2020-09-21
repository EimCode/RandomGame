package Abstract;

import Classes.Tank;
import Classes.Utility;

public class HealthFactory extends AbstractFactory {

    public Utility prototype = new Utility(0, 0, "", 0, 0);

    public HealthFactory() {
    }

    @Override
    public Utility createUtility(int x, int y, String input) {
        Utility util = null;
        switch(input)
        {
            case "hp":
                util = prototype.Clone();
                util.x = x;
                util.y = y;
                util.name = "hp";
                break;
            default:
                break;
        }

	return new HealthUtility(x, y, input, 100, 99);
    }

    @Override
    public Projectile createProjectile(int x, int y, String input, double damage, int range, int direction) {
	return new HealthProjectile(x, y, input, damage, range, direction);
    }
}
