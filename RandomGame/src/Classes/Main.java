package Classes;


public class Main {
	static GameWindow game;
	
	public GameWindow getGameWindow()
	{
		return game;
	}

	public void startGame(int[][] map)
	{
		game = new GameWindow(map);
	}
}
