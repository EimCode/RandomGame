package Classes;

public class TankObserver extends Observer{
    
	   public TankObserver(Tank tank){
	      this.tank = tank;
	      //this.tank.attach(this);
	   }

	   @Override
	   public void update() {
	      //System.out.println("Taskeliai:" + " " + String.valueOf(tank.getState()));
	   }

}
