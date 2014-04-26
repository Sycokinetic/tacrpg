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
import geowars.display.*;
import geowars.display.Controller;
import geowars.entity.Entity;
import geowars.entity.person.*;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Random;


public class Game {
	// == Private attributes ==
	private static final String VERSION = "Geowars PC v0.1";
	private static boolean init = false;
	private static volatile boolean isRunning = false;
	private static volatile boolean isPaused = true;
	
	private static volatile Player player;
	private static Thread windowThread;
	
	private double prevFrameTime;
	private int FPSCap = 60;
	
	// == Public attributes ==
	public static List<Entity> entityList;
	
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
			entityList = new CopyOnWriteArrayList<Entity>();
			
			windowThread = new Thread(new MainWindow());
			windowThread.start();
		}
	}
	
	
	// == Private methods ==	
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
		//System.out.println(1_000_000_000/(curTime - prevFrameTime));
		
		prevFrameTime = curTime;
	}
	
	
	// == Public Methods ==
	/**
	 * @return	the list of all entities in the game
	 */
	public static synchronized List<Entity> getEntityList() {
		return entityList;
	}
	
	/**
	 * @return	the game's player entity
	 */
	public static synchronized Player getPlayer() {
		return player;
	}
	
	public static String getVersion() {
		return VERSION;
	}
	
	/**
	 * Run game's primary loop if instance has not been shutdown.
	 */
	public void run() {
		if (init) {
			isRunning = true;
			
//			winSize = this.getContentPane().getSize();
//			if (this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
//				winSize = Toolkit.getDefaultToolkit().getScreenSize();
//			}
			
			player = new Player();
//			player = new Player(winSize.width/2 - 50, winSize.height/2 - 50);
			
			entityList.add(player);
			
			Random randGen = new Random();
			
			for (int i = 0; i < 100; i++) {
				entityList.add(new Nonplayer(randGen.nextInt(1920), randGen.nextInt(1080)));
			}
			
			while(isRunning) {
				Controller.processKeys();
				
//				if (!isPaused) {
//					this.setContentPane(gameFrame);
//					this.revalidate();
//				}
				
				while(!isPaused) {
					Controller.processKeys();
					
					for (Entity i: entityList) {
						i.update();
						
						if (i != player && sphereCollide(player, i)) {
							i.setAlive(LifeStatus.DEAD);
						}
						
						if (i.getAlive() == LifeStatus.DEAD) {
							entityList.remove(i);
						}
					}
					
					prevFrameTime = System.nanoTime();
					frameCap();
				}
			}
			shutdown();
		}
		else {
			System.out.println("Throw exception. Game has already been shut down.");
		}
	}
	
	/**
	 * Sets the game's running status to b
	 * @param 	b	boolean where true means the game will cycle through
	 * 				its primary loop.
	 */
	public static synchronized void setRunning(boolean b) {
		isRunning = b;
	}
	
	public static synchronized void setPaused(boolean b) {
		isPaused = b;
	}
	
	public static synchronized void togglePaused() {
		if (isPaused) {
			isPaused = false;
		}
		else {
			isPaused = true;
		}
	}
	
	public static boolean isPaused() {
		return isPaused;
	}
	
	public static boolean isRunning() {
		return isRunning;
	}
	
	/**
	 * Called after Game instance is completed and resets static variables
	 * in preparation for new Game instance.
	 */
	private static void shutdown() {		
		try {
			windowThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		player = null;
		entityList = null;
		
		init = false;
	}
}