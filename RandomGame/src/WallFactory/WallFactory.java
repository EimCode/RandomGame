package WallFactory;

public class WallFactory {

    public Wall getWall(String name)
    {
        if(name == null)
            return null;

        if(name.equalsIgnoreCase("GrassWall"))
            return new GrassWall();
        else if(name.equalsIgnoreCase("StoneWall"))
            return new StoneWall();
        else if(name.equalsIgnoreCase("DirtWall"))
            return new DirtWall();
        return null;
    }
}
