package Abstract;

import Abstract.Projectile;
import Classes.Tank;
import Classes.Utility;
import Classes.Visitable;
import Classes.Visitor;

public class DamageUtility extends Utility implements Visitable {
	private int idk;
	
	public DamageUtility(int x, int y, String name, double damage, double range) {
		super(x, y, name, damage, range);
		idk = 5;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Tank tank, Visitor visitor) {

		visitor.visitDamage(tank, this);
	}
}
