package Classes;

public class Memento {
    private final int health;

    public Memento(int hp)
    {
        this.health = hp;
    }

    public int getSavedHealth()
    {
        return health;
    }
}
