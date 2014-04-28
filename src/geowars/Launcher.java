package geowars;

public class Launcher {
	public static void main(String[] args) {
		try {
			Game game = new Game();
			game.run();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
