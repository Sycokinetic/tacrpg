package geowars.display;

import geowars.Constant;
import geowars.Game;
import geowars.display.Controller.UserAction;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements Runnable {
	// == Private attributes ==
	private final String VERSION = Game.getVersion();

	private GraphicsDevice display;

	private JPanel hostPanel;

	private Controller keyListener;
	private Point camPos;
	private Rectangle winSize;
	private int width = 900;
	private int height = width / 16 * 9;

	private long prevFrameStart;
	private long curFrameStart;
	private long prevTickLength;
	private int FPSCap = 120;
	private int fps = 0;
	private int frameCount = 0;
	private int timeElapse = 0;

	private boolean fullscreen;

	private static volatile HashMap<String, JPanel> panelSet;
	private JPanel curPanel;
	private String status;

	private ScheduledExecutorService scheduler;

	// == Constructors ==
	public MainWindow() {
		this.setResizable(false);

		panelSet = new HashMap<String, JPanel>();
		panelSet.put(Constant.PLAY, new GamePanel());
		panelSet.put(Constant.MAIN, new MainMenuPanel());
		panelSet.put(Constant.PAUSE, panelSet.get(Constant.MAIN));

		hostPanel = new JPanel(new BorderLayout());
		keyListener = new Controller(hostPanel);
		setControls();
		setContentPane(hostPanel);

		display = getGraphicsConfiguration().getDevice();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(VERSION);

		setFullscreen(false);
	}

	// == Private methods ==
	private void setFullscreen(boolean b) {
		fullscreen = b;

		if (fullscreen) {
			winSize = getGraphicsConfiguration().getBounds();
			setBounds(getGraphicsConfiguration().getBounds());
			setUndecorated(true);
			display.setFullScreenWindow(this);
		} else {
			winSize = new Rectangle(width, height);
			display.setFullScreenWindow(null);
			setUndecorated(false);
			setSize(width, height);
			setVisible(true);
		}
	}

	/**
	 * Calculates the difference between the timestamps of the end of the
	 * previous frame and the end of the current frame. If that difference is
	 * less than the time specified by the provided limit, then sleeps for the
	 * appropriate number of microseconds.
	 */
//	private void frameCap() {
//		long curTime = System.nanoTime();
//		long sleepTime = (1_000_000_000 / FPSCap - (curTime - prevFrameTime)) / 1_000_000;
//		if (sleepTime > 0) {
//			try {
//				// Thread.sleep(0);
//				Thread.sleep((int) sleepTime);
//			} catch (InterruptedException e) {
//				System.out.println("Thread Interrupted");
//			}
//		}
//
//		frameCount++;
//		curTime = System.nanoTime();
//		timeElapse += curTime - prevFrameTime;
//		if (frameCount % 60 == 0) {
//			int fps = (int) (1_000_000_000 * ((double) frameCount / timeElapse));
//
//			System.out.println("UPS: " + Game.getUPS() + ", FPS: " + fps);
//
//			frameCount = 0;
//			timeElapse = 0;
//		}
//
//		prevFrameTime = curTime;
//	}

//	// ANALYZE FOR TIME STABILITY
//	private void frameCap() {
//		long curTime = System.nanoTime();
//		long dt = curTime - prevFrameTime;
//		long sleepTime = (1_000_000_000 / FPSCap - (curTime - prevFrameTime));
//
//		if (sleepTime > 0) {
//			try {
////				Thread.sleep(0);
//				Thread.sleep((long) sleepTime / 1_000_000, (int) sleepTime % 1_000_000);
//			} catch (InterruptedException e) {
//				System.out.println("Thread Interrupted");
//			}
//		}
//	}
//
//	private void frameMonitor() {
//		frameCount++;
//		timeElapse += System.nanoTime() - prevFrameTime;
//		if (frameCount % 60 == 0) {
//			int fps = (int) (1_000_000_000 * ((double) frameCount / timeElapse));
//			System.out.println("UPS: " + Game.getUPS() + ", FPS: " + fps);
//
//			frameCount = 0;
//			timeElapse = 0;
//		}
//	}

	private void setControls() {
		keyListener.addAction(KeyEvent.VK_DOWN, new UserAction(Constant.MOVE + Constant.DOWN, true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveDown();
				}
			}
		});
		keyListener.addAction(KeyEvent.VK_UP, new UserAction(Constant.MOVE + Constant.UP, true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveUp();
				}
			}
		});
		keyListener.addAction(KeyEvent.VK_LEFT, new UserAction(Constant.MOVE + Constant.LEFT, true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveLeft();
				}
			}
		});
		keyListener.addAction(KeyEvent.VK_RIGHT, new UserAction(Constant.MOVE + Constant.RIGHT, true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveRight();
				}
			}
		});
		keyListener.addAction(KeyEvent.VK_ESCAPE, new UserAction(Constant.EXIT + Constant.GAME, true, false) {
			public void action() {
				Game.stop();
			}
		});
		keyListener.addAction(KeyEvent.VK_P, new UserAction(Constant.PAUSE + Constant.GAME, true, false) {
			public void action() {
				Game.togglePaused();
			}
		});
	}

	public void start() {
		status = Game.getStatus();
		curPanel = panelSet.get(status);
		hostPanel.add(curPanel, BorderLayout.CENTER);
		this.revalidate();

		scheduler = Executors.newScheduledThreadPool(1);
		Runnable run = new Runnable() {
			@Override
			public void run() {
				prevFrameStart = curFrameStart;
				curFrameStart = System.nanoTime();
				prevTickLength = curFrameStart - prevFrameStart;

				if (status.compareTo(Game.getStatus()) != 0) {
					status = Game.getStatus();

					hostPanel.remove(curPanel);
					curPanel = panelSet.get(status);
					hostPanel.add(curPanel, BorderLayout.CENTER);

					revalidate();
				}

				if (status.compareTo(Constant.PLAY) == 0) {
					repaint();
				}
				prevFrameStart = System.nanoTime();

				frameCount++;
				timeElapse += prevTickLength;
				if (frameCount % (FPSCap / 2) == 0) {
					fps = (int) (1_000_000_000 * ((double) frameCount / timeElapse));
					frameCount = 0;
					timeElapse = 0;
					
					System.out.println("UPS: " + Game.getUPS() + ", FPS: " + fps);
				}
			}
		};

		prevFrameStart = 0;
		curFrameStart = System.nanoTime();

		scheduler.scheduleAtFixedRate(run, 0, 1_000_000_000/this.FPSCap, TimeUnit.NANOSECONDS);
	}

	@Override
	public void run() {
		start();
	}

	public void stop() {
		scheduler.shutdown();
		this.dispose();
	}

	// Probably will be able to remove this after map implementation
	public Rectangle getWinSize() {
		return this.winSize;
	}

//	@Override
//	public void run() {
//		status = Game.getStatus();
//		curPanel = panelSet.get(status);
//		hostPanel.add(curPanel, BorderLayout.CENTER);
//		this.revalidate();
//
//		timer.start();
//	}

//	@Override
//	public void run() {
//		status = Game.getStatus();
//		curPanel = panelSet.get(status);
//		hostPanel.add(curPanel, BorderLayout.CENTER);
//		this.revalidate();
//
//		while (Game.isRunning()) {
//			if (status.compareTo(Game.getStatus()) != 0) {
//				status = Game.getStatus();
//
//				hostPanel.remove(curPanel);
//				curPanel = panelSet.get(status);
//				hostPanel.add(curPanel, BorderLayout.CENTER);
//
//				this.revalidate();
//			}
//
//			if (status.compareTo(Constant.PLAY) == 0) {
//				repaint();
//			}
//			frameCap();
////		frameMonitor();
//			prevFrameTime = System.nanoTime();
//		}
//
//		this.dispose();
//	}
}
