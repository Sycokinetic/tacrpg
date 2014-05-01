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
	private static int tickTarget = 5; // Base UPS Rate of n ms per update

	private static int tickCount = 0;
	private static int timeElapse = 0;
	private static int ups = 0;
	private static MainWindow window;

	private static volatile List<Entity> entityList;

	private static String status;
	private static ScheduledExecutorService scheduler;

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
		entityList = new CopyOnWriteArrayList<Entity>();
		status = Constant.MAIN;

		window = new MainWindow();
		windowThread = new Thread(window);

		// Change to map coordinates after completion
		player = new Player(window.getWinSize().width / 2 - 50, window.getWinSize().height / 2 - 50);
		entityList.add(player);

		Random randGen = new Random();
		for (int i = 0; i < 100; i++) {
			entityList.add(new Nonplayer(randGen.nextInt(1920), randGen.nextInt(1080)));
		}
//		entityList.add(new Nonplayer(900, 900));

		windowThread.start();

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

				tickCount++;
				timeElapse += prevTickLength;
				if (tickCount % (tickTarget / 2) == 0) {
					ups = (int) (1_000_000_000 * ((double) tickCount / timeElapse));
					tickCount = 0;
					timeElapse = 0;
				}
			}
		};

		prevFrameStart = 0;
		curFrameStart = System.nanoTime();

		scheduler.scheduleAtFixedRate(run, 0, tickTarget, TimeUnit.MILLISECONDS);
	}

	public static void stop() {
		isRunning = false;
		scheduler.shutdown();
		window.stop();
		shutdown();
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
		if (status.compareTo(Constant.PLAY) != 0) {
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

	// MERGE WITH STOP
	// FIND CONSISTENT IMPLEMENTATION WITH MAINWINDOW
	/**
	 * Called after Game instance is completed and resets static variables in
	 * preparation for new Game instance.
	 */
	public static void shutdown() {
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