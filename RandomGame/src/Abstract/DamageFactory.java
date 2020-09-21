package Abstract;

import Classes.Tank;
import Classes.Utility;

public class DamageFactory extends AbstractFactory {

    public Utility prototype = new Utility(0, 0, "", 0, 0);

    public DamageFactory() {
    }

    @Override
    public Utility createUtility(int x, int y, String input) {
        Utility util = null;
        switch (input) {
            case "m4":
                util = prototype.Clone();
                util.x = x;
                util.y = y;
                util.name = "m4";
                break;
            case "ak":
                util = prototype.Clone();
                util.x = x;
                util.y = y;
                util.name = "ak";
                break;
            default:
                break;
        }
        return new DamageUtility(x, y, input, 100, 99);
    }

    @Override
    public Projectile createProjectile(int x, int y, String input, double damage, int range, int direction) {
        // TODO Auto-generated method stub
        return new DamageProjectile(x, y, input, damage, range, direction);
    }

}
