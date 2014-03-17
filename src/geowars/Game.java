package geowars;

import geowars.entity.*;
import geowars.graphics.*;

import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends JFrame {
	private static String version = "Geowars PC v0.1";
	private static boolean isRunning = false;
	
	private static DisplayPanel display;
	private static InputListener eventListener;
	private static ArrayList<Entity> entityList;
	private static Player player;
	
	public Game() {
		player = new Player();
		entityList = new ArrayList<Entity>();
		entityList.add(player);
		
		display = new DisplayPanel(entityList);	
		eventListener = new InputListener();
		
		setContentPane(display);
		addKeyListener(eventListener);
		addMouseListener(eventListener);
		addMouseMotionListener(eventListener);
		
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(version);
		setVisible(true);
	}
	
	public void run() {
		isRunning = true;
		entityList.add(new WorldObject());
		
		while(isRunning) {
			for (Entity i: entityList) {
				i.update();
			}
			
			repaint();
			
			try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                System.out.println("Interrupted thread!");
            }
		}
		
		dispose();
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public static void setRunning(boolean b) {
		isRunning = b;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
		
//		Constant.splitKeyString("MAIN_UP_DOWN");
	}
}