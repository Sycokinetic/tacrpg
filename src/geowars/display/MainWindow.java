package geowars.display;

import geowars.Constant;
import geowars.Game;
import geowars.display.Controller.UserAction;
import geowars.entity.Entity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import java.util.HashMap;
import java.util.List;

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

	private long prevFrameTime;
	private int FPSCap = 60;
	private int frameCount = 0;
	private int timeElapse = 0;

	private boolean fullscreen;

	private static volatile HashMap<String, JPanel> panelSet;
	private JPanel curPanel;
	private String status;

	// == Constructors ==
	public MainWindow() {
		this.setResizable(false);

		panelSet = new HashMap<String, JPanel>();
		panelSet.put(Constant.PLAY, new GamePanel());
		panelSet.put(Constant.MAIN, new MainMenuPanel());
		panelSet.put(Constant.PAUSE, panelSet.get(Constant.MAIN));

		hostPanel = new JPanel(new BorderLayout());
//		gamePanel = new GamePanel();
//		menuPanel = new MainMenuPanel();
		keyListener = new Controller(hostPanel);
//		gameListener = new Controller(gamePanel);
//		menuListener = new Controller(menuPanel);
		setControls();
		setContentPane(hostPanel);
//		setContentPane(menuPanel);

		display = getGraphicsConfiguration().getDevice();
		winSize = getGraphicsConfiguration().getBounds();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(VERSION);

		setFullscreen(true);
	}

	// == Private methods ==
	private void setFullscreen(boolean b) {
		fullscreen = b;

		if (fullscreen) {
			setBounds(getGraphicsConfiguration().getBounds());
			setSize(winSize.width, winSize.height);
			setUndecorated(true);
			display.setFullScreenWindow(this);
		} else {
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

	// ANALYZE FOR TIME STABILITY
	private void frameCap() {
		long curTime = System.nanoTime();
		long dt = curTime - prevFrameTime;
		long sleepTime = (1_000_000_000 / FPSCap - (curTime - prevFrameTime));

		if (sleepTime > 0) {
			try {
//				Thread.sleep(0);
				Thread.sleep((long) sleepTime / 1_000_000, (int) sleepTime % 1_000_000);
			} catch (InterruptedException e) {
				System.out.println("Thread Interrupted");
			}
		}

//		System.out.println(System.nanoTime() - prevFrameTime);
	}

	private void frameMonitor() {
		frameCount++;
		timeElapse += System.nanoTime() - prevFrameTime;
		if (frameCount % 60 == 0) {
			int fps = (int) (1_000_000_000 * ((double) frameCount / timeElapse));
			System.out.println(Game.getPlayer().getCurPos());
			System.out.println("UPS: " + Game.getUPS() + ", FPS: " + fps);

			frameCount = 0;
			timeElapse = 0;
		}
	}

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
				Game.setPaused(true);
				Game.setRunning(false);
			}
		});
		keyListener.addAction(KeyEvent.VK_P, new UserAction(Constant.PAUSE + Constant.GAME, true, false) {
			public void action() {
				Game.togglePaused();
			}
		});
	}

	// HAS BUG WITH EXIT
	@Override
	public void run() {
		status = Game.getStatus();
		curPanel = panelSet.get(status);
		hostPanel.add(curPanel, BorderLayout.CENTER);
		this.revalidate();

		while (Game.isRunning()) {
			if (status.compareTo(Game.getStatus()) != 0) {
				status = Game.getStatus();

				hostPanel.remove(curPanel);
				curPanel = panelSet.get(status);
				hostPanel.add(curPanel, BorderLayout.CENTER);

				this.revalidate();
			}

			if (status.compareTo(Constant.PLAY) == 0) {
				repaint();
			}
			frameCap();
			frameMonitor();
			prevFrameTime = System.nanoTime();
		}

		this.dispose();
	}
}
