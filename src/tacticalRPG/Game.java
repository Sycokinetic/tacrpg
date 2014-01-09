package tacticalRPG;

import javax.swing.JFrame;

import tacticalRPG.resource.*;

public class Game extends JFrame implements Runnable {
	boolean isRunning = false;
	
	public Game() {
		isRunning = true;
	}
	
	@Override
	public synchronized void run() {
		while(isRunning) {
			System.out.println("Game Running!");
			
			Entity x = new Entity();
			
			System.out.println(x.sprite);
			isRunning = false;
		}
	}
}
