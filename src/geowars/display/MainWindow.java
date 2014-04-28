package geowars.display;

import geowars.Constant;
import geowars.Game;
import geowars.display.Controller.UserAction;
import geowars.entity.Entity;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import java.util.List;

import javax.swing.JFrame;


public class MainWindow extends JFrame implements Runnable {
	// == Private attributes ==
	private final String VERSION = Game.getVersion();
	
	private GraphicsDevice display;
	
	private GamePanel gameFrame;
	private Controller gameListener;
	private Controller menuListener;
	private Point camPos;
	private Rectangle winSize;
	
	private double prevFrameTime;
	private int FPSCap = 60;
	private int frameCount = 0;
	private int timeElapse = 0;
	
	
	private boolean isRunning;
	private boolean isPaused;
	private boolean fullscreen;
	
	
	// == Constructors ==
	public MainWindow() {
		gameFrame = new GamePanel();
		MainMenuPanel menuFrame = new MainMenuPanel();
		gameListener = new Controller(gameFrame);
		menuListener = new Controller(menuFrame);
		setControls();
		setContentPane(menuFrame);
		
		display = getGraphicsConfiguration().getDevice();
		winSize = getGraphicsConfiguration().getBounds();
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(VERSION);
		
		setFullscreen(false);
	}
	
	
	// == Private methods ==
	private void toggleFullscreen() {
		if (fullscreen) {
			this.dispose();
			
			display.setFullScreenWindow(null);
			setUndecorated(false);
			setSize(400, 400);
			setVisible(true);
			
			fullscreen = false;
		}
		else {
			this.dispose();
			
			setSize(winSize.width, winSize.height);
			setUndecorated(true);
			display.setFullScreenWindow(this);
			
			fullscreen = true;
		}
	}
	
	// FIX TO ALLOW MULTIPLE CALLS
	private void setFullscreen(boolean b) {
		fullscreen = b;
		
		if (fullscreen) {
			setBounds(getGraphicsConfiguration().getBounds());
			setSize(winSize.width, winSize.height);
			setUndecorated(true);
			display.setFullScreenWindow(this);
		}
		else {
			display.setFullScreenWindow(null);
			setUndecorated(false);
			setSize(400, 400);
			setVisible(true);
		}
	}
	
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
//				Thread.sleep(0);
                Thread.sleep((int)sleepTime);
            } catch (InterruptedException e) {
                System.out.println("Thread Interrupted");
            }
		}
		
		frameCount++;
		curTime = System.nanoTime();
		timeElapse += curTime - prevFrameTime;
		if (frameCount % 60 == 0) {
			int fps = (int)(1_000_000_000 * ((double)frameCount / timeElapse));
			
//			System.out.println("UPS: " + Game.getUPS() + ", FPS: " + fps);
			
			frameCount = 0;
			timeElapse = 0;
		}
		
		prevFrameTime = curTime;
	}
	
	private void setControls() {
		gameListener.addAction(KeyEvent.VK_DOWN, new UserAction(Constant.MOVE + Constant.DOWN, true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveDown();
				}
			}
		});
		gameListener.addAction(KeyEvent.VK_UP, new UserAction(Constant.MOVE + Constant.UP, true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveUp();
				}
			}
		});
		gameListener.addAction(KeyEvent.VK_LEFT, new UserAction(Constant.MOVE + Constant.LEFT, true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveLeft();
				}
			}
		});
		gameListener.addAction(KeyEvent.VK_RIGHT, new UserAction(Constant.MOVE + Constant.RIGHT, true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveRight();
				}
			}
		});
		gameListener.addAction(KeyEvent.VK_ESCAPE, new UserAction(Constant.EXIT + Constant.GAME, true, false) {
			public void action() {
				Game.setPaused(true);
				Game.setRunning(false);
			}
		});
		gameListener.addAction(KeyEvent.VK_P, new UserAction(Constant.PAUSE + Constant.GAME, true, false) {
			public void action() {
				Game.togglePaused();
			}
		});
		gameListener.addAction(KeyEvent.VK_F, new UserAction(Constant.EXIT + Constant.GAME, true, false) {
			public void action() {
				toggleFullscreen();
			}
		});
		
		menuListener.addAction(KeyEvent.VK_ESCAPE, new UserAction(Constant.EXIT + Constant.GAME, true, false) {
			public void action() {
				Game.setPaused(true);
				Game.setRunning(false);
			}
		});
		menuListener.addAction(KeyEvent.VK_F, new UserAction(Constant.EXIT + Constant.GAME, true, false) {
			public void action() {
				toggleFullscreen();
			}
		});
	}


	@Override
	public void run() {
		while (Game.isRunning()) {
			if (!Game.isPaused()) {
				this.setContentPane(gameFrame);
				this.revalidate();
			}
			
			while (!Game.isPaused()) {
				repaint();
				
				frameCap();
			}
		}
		
		this.dispose();
	}
}
