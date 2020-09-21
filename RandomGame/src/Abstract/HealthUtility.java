package Abstract;

import Classes.*;

public class HealthUtility extends Utility  implements Health, Visitable {
    private HealthProjectile adaptee;


    public HealthUtility(int x, int y, String name, double damage, double range) {
        super(x, y, name, damage, range);
        // TODO Auto-generated constructor stub
    }

    public void SetAdaptee(HealthProjectile projectile) {
        this.adaptee = projectile;
    }

    @Override
    public void Heal (Tank tank, int amount) {
        if (adaptee == null) {
            long startTime = System.currentTimeMillis();
            System.out.println(tank.health);
            if(tank.health >= 50)
                tank.restoreFromMemento(tank.defaultHealth);
            else
                tank.health += amount;
            System.out.println(tank.health);
        }
        else {
            adaptee.healMe(tank, tank.defaultHealth.getSavedHealth());
        }
    }

    @Override
    public void accept(Tank tank, Visitor visitor) {
        visitor.visitHealth(tank, this);
    }
}
