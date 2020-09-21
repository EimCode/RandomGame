package Abstract;

import Classes.Tank;

public class HealthProjectile extends Projectile {

    public HealthProjectile(int x, int y, String name, double damage,  int range, int direction) {
        super(x, y, name, range, damage, direction);
    }

    public void healMe(Tank tank, int amount){
        System.out.println("Lifesteal");
        tank.health += amount;
    }
}
