package Classes;

import Abstract.DamageUtility;
import Abstract.HealthUtility;

public interface Visitor {

    void visitHealth(Tank tank, Visitable visitable);
    void visitDamage(Tank tank, Visitable visitable);
}
