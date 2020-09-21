package Abstract;

import Classes.Tank;
import Classes.Utility;

public abstract class AbstractFactory {
	
	public abstract Utility createUtility(int x, int y, String input);
	public abstract Projectile createProjectile(int x, int y, String input, double damage, int range, int direction);
	
}
