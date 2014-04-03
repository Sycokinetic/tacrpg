/*
 * Classname: Game
 * Author: Sycokinetic
 * 
 * Notes:
 * - Contains main method and is the entry point for the application
 * - Only one Game instance per application can exist.
 */

package geowars;

import geowars.Constant.LifeStatus;
import geowars.entity.Entity;
import geowars.entity.person.*;
import geowars.entity.world.*;
import geowars.graphics.*;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Game extends JFrame {
	// == Private attributes ==
	private static String version = "Geowars PC v0.1";
	private static boolean init = false;
	private static boolean isRunning = false;
	
	private static DisplayPanel display;
	private static InputListener eventListener;
	private static Player player;
	
	private double prevFrameTime;
	private int FPSCap = 60;
	
	
	// == Public attributes ==
	public static ArrayList<Entity> entityList;
	
	
	// == Constructors ==
	/**
	 * Class constructor.
	 * <p>
	 * Determines if a Game instance has already been created and configures a
	 * new instance if one is not found.
	 * 
	 * @throws InstantiationException	Only one instance can exist at a time
	 */
	public Game() throws InstantiationException {
		if (init) throw new InstantiationException("Multiple Game instances");
		
		else {
			init = true;
			
			entityList = new ArrayList<Entity>();
			display = new DisplayPanel();
			eventListener = new InputListener();
			
			setContentPane(display);
			addKeyListener(eventListener);
			addMouseListener(eventListener);
			addMouseMotionListener(eventListener);
			
			setExtendedState(MAXIMIZED_BOTH);
			setUndecorated(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			setTitle(version);
			setVisible(true);
		}
	}
	
	
	// == Private methods ==
	/**
	 * Calculates the difference between the timestamps of the end of the
	 * previous frame and the end of the current frame. If that difference
	 * is less than the time specified by the provided limit,
	 * then sleeps for the appropriate number of microseconds.
	 */
	private void frameCap() {
		double curTime = System.nanoTime();
		double sleepTime = (1_000_000_000/FPSCap - (curTime - prevFrameTime)) / 1_000_000;
		if (sleepTime > 0) {
			try {
                Thread.sleep((int)sleepTime);
            } catch (InterruptedException e) {
                System.out.println("Thread Interrupted");
            }
		}
		
		prevFrameTime = curTime;
	}
	
	/**
	 * Identifies collision by checking the radii of the circles inscribed in
	 * the image of each entity. If the distance between the center of each
	 * image is less than the sum off the radii, then they collide.
	 * 
	 * @param	a		An entity to be checked for collision
	 * @param	b		The entity to be compared against a.
	 * @return	boolean		True if a and b collide.
	 */
	private static boolean sphereCollide(Entity a, Entity b) {
		Point p1 = a.getCenter();
		Point p2 = b.getCenter();
		
		double d = p1.distance(p2);
		
		if (d < a.getRadius() + b.getRadius()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	// == Public Methods ==
	/**
	 * @return	the list of all entities in the game
	 */
	public static ArrayList<Entity> getEntityList() {
		return entityList;
	}
	
	/**
	 * @return	the game's player entity
	 */
	public static Player getPlayer() {
		return player;
	}
	
	/**
	 * Run game's primary loop if instance has not been shutdown.
	 */
	public void run() {
		if (init) {
			isRunning = true;
			
			player = new Player();
			entityList.add(player);
			
			entityList.add(new Nonplayer(1300, 120));
			entityList.add(new Nonplayer(700, 800));
			entityList.add(new Nonplayer(400, 200));
			
			prevFrameTime = System.nanoTime();
			
			while(isRunning) {
				InputListener.processKeys();
				
				for (Entity i: entityList) {
					i.update();
					if (i != player && sphereCollide(player, i)) {
						i.setAlive(LifeStatus.DEAD);
					}
				}
				
				for (int i = 0; i < entityList.size(); i++) {
					if (entityList.get(i).getAlive() == LifeStatus.DEAD) {
						entityList.remove(i);
						i--;
					}
				}
				
				this.repaint();
				frameCap();
			}
			
			this.dispose();
			shutdown();
		}
		else {
			System.out.println("Throw exception. Game has already been shutdown.");
		}
	}
	
	/**
	 * Sets the game's running status to b
	 * @param 	b	boolean where true means the game will cycle through
	 * 				its primary loop.
	 */
	public static void setRunning(boolean b) {
		isRunning = b;
	}
	
	/**
	 * Called after Game instance is completed and resets static variables
	 * in preparation for new Game instance.
	 */
	private static void shutdown() {
		eventListener = null;
		display = null;
		player = null;
		entityList = null;
		
		init = false;
	}
	
	
	// == Main method ==
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