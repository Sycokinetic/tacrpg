package geowars.display;

import geowars.Constant;
import geowars.Game;
import geowars.Constant.EventKey;
import geowars.Constant.ModKey;
import geowars.display.Controller.UserAction;
import geowars.entity.Entity;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import java.util.List;

import javax.swing.JFrame;


public class MainWindow extends JFrame implements Runnable {
	// == Private attributes ==
	private final String VERSION = Game.getVersion();
	
	private GraphicsDevice display;
	
	private GamePanel gameFrame;
//	private static InputListener eventListener;
	private Controller gameListener;
	private Controller menuListener;
	private Point camPos;
	private Dimension winSize;
	
	private double prevFrameTime;
	private int FPSCap = 60;
	
	private boolean isRunning;
	private boolean isPaused;
	private List<Entity> entityList;
	
	
	// == Constructors
	public MainWindow() {
		gameFrame = new GamePanel();
		MainMenuPanel menuFrame = new MainMenuPanel();
		gameListener = new Controller(gameFrame);
		menuListener = new Controller(menuFrame);
		setControls();
		setContentPane(menuFrame);
		
//		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//		eventListener = new InputListener();
//		manager.addKeyEventDispatcher(eventListener);
		
		display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		winSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setSize(winSize.width, winSize.height);
		setUndecorated(true);
//		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle(VERSION);
		
		display.setFullScreenWindow(this);
//		setVisible(true);
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
		//System.out.println(1_000_000_000/(curTime - prevFrameTime));
		
		prevFrameTime = curTime;
	}
	
	private void setControls() {
		gameListener.addAction(KeyEvent.VK_DOWN, new UserAction(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN), true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveDown(5);
				}
			}
		});
		gameListener.addAction(KeyEvent.VK_UP, new UserAction(Constant.concatKeys(EventKey.MOVE, ModKey.UP), true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveUp(5);
				}
			}
		});
		gameListener.addAction(KeyEvent.VK_LEFT, new UserAction(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT), true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveLeft(5);
				}
			}
		});
		gameListener.addAction(KeyEvent.VK_RIGHT, new UserAction(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT), true, true) {
			public void action() {
				if (!Game.isPaused()) {
					Game.getPlayer().moveRight(5);
				}
			}
		});
		gameListener.addAction(KeyEvent.VK_ESCAPE, new UserAction(Constant.concatKeys(EventKey.EXIT, ModKey.GAME), true, false) {
			public void action() {
				Game.setPaused(true);
				Game.setRunning(false);
			}
		});
		gameListener.addAction(KeyEvent.VK_P, new UserAction(Constant.concatKeys(EventKey.PAUSE, ModKey.GAME), true, false) {
			public void action() {
				Game.togglePaused();
			}
		});
		
		menuListener.addAction(KeyEvent.VK_ESCAPE, new UserAction(Constant.concatKeys(EventKey.EXIT, ModKey.GAME), true, false) {
			public void action() {
				Game.setPaused(true);
				Game.setRunning(false);
			}
		});
	}


	@Override
	public void run() {
		isRunning = true;
		
		while (isRunning) {
			isRunning = Game.isRunning();
			isPaused = Game.isPaused();
			
			if (!isPaused) {
				this.setContentPane(gameFrame);
				this.revalidate();
			}
			
			while (!isPaused) {
				repaint();
				isPaused = Game.isPaused();
				
				prevFrameTime = System.nanoTime();
				frameCap();
			}
		}
		
		this.dispose();
	}
}
