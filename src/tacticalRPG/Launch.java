package tacticalRPG;

public class Launch extends Thread {	
	public static void main(String[] args) {
		Thread game = new Thread(new Game());
		game.start();	
	}
}