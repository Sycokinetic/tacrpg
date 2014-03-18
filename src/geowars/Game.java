package geowars;

import geowars.entity.*;
import geowars.entity.person.Player;
import geowars.entity.world.WorldObject;
import geowars.graphics.*;

import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends JFrame {
	private static String version = "Geowars PC v0.1";
	private static boolean init = false;
	private static boolean isRunning = false;
	
	private static DisplayPanel display;
	private static InputListener eventListener;
	private static ArrayList<Entity> entityList;
	private static Player player;
	
	public Game() throws InstantiationException {
		if (init) throw new InstantiationException("Multiple Game instances");
		
		else {
			init = true;
			
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
	}
	
	public void run() {
		isRunning = true;
		entityList.add(new WorldObject());
		
		while(isRunning) {
			InputListener.processKeys();
			
			for (Entity i: entityList) {
				i.update();
			}
			
			repaint();
			
			try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                System.out.println("Thread Interrupted");
            }
		}
		
		dispose();
		init = false;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public static void setRunning(boolean b) {
		isRunning = b;
	}
	
	public static void main(String[] args) {
		try {
			Game game = new Game();
			game.run();
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}