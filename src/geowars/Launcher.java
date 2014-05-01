package geowars;

public class Launcher {
	public static void main(String[] args) {
		try {
			Game.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
