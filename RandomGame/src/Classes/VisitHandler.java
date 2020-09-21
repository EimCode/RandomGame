package Classes;

import Abstract.DamageUtility;
import Abstract.HealthUtility;

public class VisitHandler implements Visitor {

    private String name;
    public VisitHandler(String name){
        super();
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public void visitHealth(Tank tank, Visitable visitable) {
        HealthUtility a = (HealthUtility) visitable;
        a.Heal(tank, 20);
    }

    @Override
    public void visitDamage(Tank tank, Visitable visitable) {
//        DamageUtility a = (DamageUtility) visitable;
        tank.range += 20;
        System.out.println("Range up: " + tank.range);
    }
}
