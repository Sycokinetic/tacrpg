package geowars;

import java.util.ArrayList;
import javax.swing.JFrame;

import geowars.entity.*;
import geowars.graphics.DisplayPanel;

public class Game extends JFrame {	
	private static String version = "Geowars PC v0.1";
	private static boolean isRunning = false;
	
	private static DisplayPanel display;
	public static InputListener eventListener;
	private static ArrayList<Entity> entityList;
	
	public Game() {	
		entityList = new ArrayList<Entity>();
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
			//System.out.println("Game Running!");
			
			for (Entity i: entityList) {
				i.update();
			}
			
			repaint();
			
			try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Interrupted thread!");
            }
			//this.isRunning = false;
		}
		
		dispose();
	}
	
	public static void setRunning(boolean b) {
		isRunning = b;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}
}