package Classes;

public class ProjectileSpeedExpression implements  Expression{
    public int speed;

    public ProjectileSpeedExpression() {
        this.speed = 40;
    }

    @Override
    public void interpret(String context) {
        int temp;
        String[] contextSplit = context.split(" ");
        temp = Integer.parseInt(contextSplit[1]);
        if(contextSplit.length < 2) return;
        if (temp > 0) {
            this.speed = temp;
        }
    }
}

