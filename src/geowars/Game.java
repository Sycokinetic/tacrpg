/*
 * Classname: Game
 * Author: Sycokinetic
 * 
 * Notes:
 * - Only one Game instance per application can exist.
 */

package geowars;

import geowars.display.Controller;
import geowars.display.GamePanel;
import geowars.display.MainMenuPanel;
import geowars.display.MainWindow;
import geowars.entity.Entity;
import geowars.entity.person.Nonplayer;
import geowars.entity.person.Player;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

public class Game {
	// == Private attributes ==
	private static final String VERSION = "Geowars PC v0.1";
	private static boolean init = false;
	private static volatile boolean isRunning = false;
	private static volatile boolean isPaused = true;

	private static volatile Player player;
	private static Thread windowThread;

	private static long prevFrameTime;
	private static long frameMarker1 = 0;
	private static long frameMarker2 = 0;
	private static int FPSCap = 60;
	private static int tickTarget = 10; // Base UPS Rate of n ms per update

	private static int tickCount = 0;
	private static int timeElapse = 0;
	private static double ups = 0;

	private static List<Entity> entityList;

	private static volatile String status;
	private static volatile HashMap<String, JPanel> panelSet;

	// == Constructors ==
	/**
	 * Class constructor.
	 * <p>
	 * Determines if a Game instance has already been created and configures a
	 * new instance if one is not found.
	 * 
	 * @throws InstantiationException
	 *             Only one instance can exist at a time
	 */
	public Game() throws InstantiationException {
		if (init)
			throw new InstantiationException("Multiple Game instances");

		else {
			init = true;
			entityList = new CopyOnWriteArrayList<Entity>();
			
			panelSet = new HashMap<String, JPanel>();
			panelSet.put(Constant.PLAY, new GamePanel());
			panelSet.put(Constant.MAIN, new MainMenuPanel());
			
			status = Constant.PLAY;

			windowThread = new Thread(new MainWindow());
		}
	}

	// == Private methods ==
	/**
	 * Identifies collision by checking the radii of the circles inscribed in
	 * the image of each entity. If the distance between the center of each
	 * image is less than the sum off the radii, then they collide.
	 * 
	 * @param a
	 *            An entity to be checked for collision
	 * @param b
	 *            The entity to be compared against a.
	 * @return boolean True if a and b collide.
	 */
	private static boolean sphereCollide(Entity a, Entity b) {
		Point p1 = a.getCenter();
		Point p2 = b.getCenter();

		double d = p1.distance(p2);

		if (d < a.getRadius() + b.getRadius()) {
			return true;
		} else {
			return false;
		}
	}

	// ANALYZE FOR TIME STABILITY
	private void updateCap() {
		double curTime = System.nanoTime();
		double dt = curTime - prevFrameTime;
		double sleepTime = tickTarget * 1_000_000 - dt;

		if (sleepTime > 0) {
			try {
//				Thread.sleep(0);
//				System.out.println(sleepTime + ", " + (((long)sleepTime/1_000_000)*1_000_000 + (int)sleepTime % 1_000_000 + dt) + ", " + dt);
				Thread.sleep((long) sleepTime / 1_000_000, (int) sleepTime % 1_000_000);
			} catch (InterruptedException e) {
				System.out.println("Thread Interrupted");
			}
		}

//		System.out.println(System.nanoTime() - prevFrameTime);
	}

	private void tickMonitor() {
		frameMarker1 = frameMarker2;
		frameMarker2 = System.nanoTime();

		tickCount++;
		timeElapse += frameMarker2 - prevFrameTime;
		if (tickCount % 50 == 0) {
			ups = (1_000_000_000 * ((double) tickCount / timeElapse));
			tickCount = 0;
			timeElapse = 0;
		}
	}

	// == Public Methods ==
	/**
	 * @return the list of all entities in the game
	 */
	public static synchronized List<Entity> getEntityList() {
		return entityList;
	}

	/**
	 * @return the game's player entity
	 */
	public static synchronized Player getPlayer() {
		return player;
	}

	public static String getVersion() {
		return VERSION;
	}

	public static long getPrevFrameTime() {
		return prevFrameTime;
	}

	public static long getMarker1() {
		return frameMarker1;
	}

	public static long getMarker2() {
		return frameMarker2;
	}

	public static int getTickTarget() {
		return tickTarget;
	}

	public static int getTickCount() {
		return tickCount;
	}

	public static int getTimeElapse() {
		return timeElapse;
	}

	public static double getUPS() {
		return ups;
	}
	
	public static synchronized JPanel getCurPanel() {
		return panelSet.get(status);
	}

	/**
	 * Run game's primary loop if instance has not been shutdown.
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {
		if (init) {
			isRunning = true;

			player = new Player();
			// player = new Player(winSize.width/2 - 50, winSize.height/2 - 50);

			entityList.add(player);

			Random randGen = new Random();

			for (int i = 0; i < 100; i++) {
				entityList.add(new Nonplayer(randGen.nextInt(1920), randGen.nextInt(1080)));
			}

			windowThread.start();

			while (isRunning) {
				Controller.processKeys();

				while (!isPaused) {
					Controller.processKeys();

					for (Entity i : entityList) {
						i.update();

						if (i != player && sphereCollide(player, i)) {
							i.setAlive(Constant.DEAD);
						}

						if (i.getAlive() == Constant.DEAD) {
							entityList.remove(i);
						}
					}

					updateCap();
					tickMonitor();
					prevFrameTime = System.nanoTime();
				}
			}
			shutdown();
		} else {
			throw new Exception("Game has already been shut down.");
		}
	}

	/**
	 * Sets the game's running status to b
	 * 
	 * @param b
	 *            boolean where true means the game will cycle through its
	 *            primary loop.
	 */
	public static synchronized void setRunning(boolean b) {
		isRunning = b;
	}

	public static synchronized void setPaused(boolean b) {
		isPaused = b;
		
		if (b) {
//			status = Constant.PAUSE;
		}
		else {
//			status = Constant.PLAY;
		}
	}

	public static void togglePaused() {
		if (isPaused) {
			isPaused = false;
//			status = Constant.PLAY;
		} else {
			isPaused = true;
//			status = Constant.PAUSE;
		}
	}

	public static boolean isPaused() {
		return isPaused;
	}

	public static boolean isRunning() {
		return isRunning;
	}

	/**
	 * Called after Game instance is completed and resets static variables in
	 * preparation for new Game instance.
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