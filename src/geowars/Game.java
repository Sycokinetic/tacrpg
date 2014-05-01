/*
 * Classname: Game
 * Author: Sycokinetic
 * 
 * Notes:
 * - Only one Game instance per application can exist.
 */

package geowars;

import geowars.display.Controller;
import geowars.display.MainWindow;
import geowars.entity.Entity;
import geowars.entity.person.Nonplayer;
import geowars.entity.person.Player;

import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
	// == Private attributes ==
	private static final String VERSION = "Geowars PC v0.1";
	private static boolean init = false;
	private static volatile boolean isRunning = false;

	private static volatile Player player;
	private static Thread windowThread;

	private static long prevFrameStart;
	private static long curFrameStart;
	private static long prevTickLength;
	private static int tickTarget = 10; // Base UPS Rate of n ms per update

	private static int tickCount = 0;
	private static int timeElapse = 0;
	private static int ups = 0;

	private static volatile List<Entity> entityList;

	private static String status;
	public static ScheduledExecutorService scheduler;

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
		if (init) {
			throw new InstantiationException("Multiple Game instances");
		}

		else {
			init = true;
			entityList = new CopyOnWriteArrayList<Entity>();

			status = Constant.MAIN;

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
	private static void updateCap() {
		long curTime = System.nanoTime();
		long dt = curTime - curFrameStart;
		double sleepTime = tickTarget * 1_000_000 - dt;

		if (sleepTime > 0) {
			try {
//				System.out.println(sleepTime + ", " + (((long)sleepTime/1_000_000)*1_000_000 + (int)sleepTime % 1_000_000 + dt) + ", " + dt);
				Thread.sleep((long) sleepTime / 1_000_000, (int) sleepTime % 1_000_000);
			} catch (InterruptedException e) {
				System.out.println("Thread Interrupted");
			}
		}

		timeElapse += prevTickLength;
		if (tickCount % 50 == 0) {
			ups = (int) (1_000_000_000 * ((double) tickCount / timeElapse));
			tickCount = 0;
			timeElapse = 0;
		}
		tickCount++;
		prevTickLength = System.nanoTime() - curFrameStart;
	}

//	private void tickMonitor() {
//		frameMarker1 = frameMarker2;
//		frameMarker2 = System.nanoTime();
//
//		tickCount++;
//		timeElapse += frameMarker2 - prevFrameStart;
//		if (tickCount % 50 == 0) {
//			ups = (int) (1_000_000_000 * ((double) tickCount / timeElapse));
//			tickCount = 0;
//			timeElapse = 0;
//		}
//	}

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

	public static long getPrevTickLength() {
		return prevTickLength;
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

	public static int getUPS() {
		return ups;
	}

	public static String getStatus() {
		return new String(status);
	}

	public static void start() {
		isRunning = true;
		
		windowThread = new Thread(new MainWindow());
		entityList = new CopyOnWriteArrayList<Entity>();

		status = Constant.MAIN;
		
		player = new Player();
		// player = new Player(winSize.width/2 - 50, winSize.height/2 - 50);
		entityList.add(player);

		Random randGen = new Random();
		for (int i = 0; i < 100; i++) {
			entityList.add(new Nonplayer(randGen.nextInt(1920), randGen.nextInt(1080)));
		}
//		entityList.add(new Nonplayer(900, 900));

		windowThread.start();

		prevFrameStart = 0;
		curFrameStart = System.nanoTime();

		scheduler = Executors.newScheduledThreadPool(1);
		Runnable run = new Runnable() {
			@Override
			public void run() {
				prevFrameStart = curFrameStart;
				curFrameStart = System.nanoTime();
				prevTickLength = curFrameStart - prevFrameStart;
				
				Controller.processKeys();
				
				if (status.compareTo(Constant.PLAY) == 0) {
					for (Entity i : entityList) {
						i.update();

						if (i != player && sphereCollide(player, i)) {
							i.setAlive(Constant.DEAD);
						}

						if (i.getAlive() == Constant.DEAD) {
							entityList.remove(i);
						}
					}
				}
			}
		};

		scheduler.scheduleAtFixedRate(run, 0, tickTarget, TimeUnit.MILLISECONDS);
	}
	
	public static void stop() {
		isRunning = false;
		scheduler.shutdown();
	}
	
	/**
	 * Run game's primary loop if instance has not been shutdown.
	 * 
	 * @throws Exception
	 */
	public static void run() {
//		isRunning = true;
//
//		player = new Player();
//		// player = new Player(winSize.width/2 - 50, winSize.height/2 - 50);
//
//		entityList.add(player);
//
//		Random randGen = new Random();
//
//		for (int i = 0; i < 100; i++) {
//			entityList.add(new Nonplayer(randGen.nextInt(1920), randGen.nextInt(1080)));
//		}
////		entityList.add(new Nonplayer(900, 900));
//
//		windowThread.start();
//
//		prevFrameStart = 0;
//		curFrameStart = System.nanoTime();
//		while (isRunning) {
		Controller.processKeys();

		prevFrameStart = curFrameStart;
		curFrameStart = System.nanoTime();

		if (status.compareTo(Constant.PLAY) == 0) {
			for (Entity i : entityList) {
				i.update();

				if (i != player && sphereCollide(player, i)) {
					i.setAlive(Constant.DEAD);
				}

				if (i.getAlive() == Constant.DEAD) {
					entityList.remove(i);
				}
			}
		}

//			updateCap();
//			tickMonitor();
//		}
//		shutdown();
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
		if (b) {
			status = Constant.PAUSE;
		} else {
			status = Constant.PLAY;
		}
	}

	public static void togglePaused() {
		if (status.compareTo(Constant.PAUSE) == 0) {
			status = Constant.PLAY;
		} else {
			status = Constant.PAUSE;
		}
	}

	public static boolean isPaused() {
		if (status.compareTo(Constant.PAUSE) == 0) {
			return true;
		} else {
			return false;
		}
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