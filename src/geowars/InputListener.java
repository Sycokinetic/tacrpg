/*
 * Classname: InputListener
 * Author: Sycokinetic
 * 
 * Notes:
 * - Constitutes the KeyListener, MouseListener, and MouseMotionListener
 *   for instances of Game
 */

package geowars;

import geowars.Constant.*;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

public class InputListener implements KeyEventDispatcher, MouseListener, MouseMotionListener {
	// == Private attributes ==
	private static HashMap<String, Integer> heldControls;
	private static HashMap<Integer, Boolean> heldStatus;
	
	private static HashMap<String, Integer> tapControls;
	private static HashMap<Integer, Boolean> tapStatus;
	
	// == Constructors ==
	/**
	 * Class constructor
	 * <p>
	 * Initializes controls and status and creates the control scheme
	 */
	public InputListener() {
		heldControls = new HashMap<String, Integer>();
		heldStatus = new HashMap<Integer, Boolean>();
		
		tapControls = new HashMap<String, Integer>();
		tapStatus = new HashMap<Integer, Boolean>();
		
		setControls();
	}
	
	
	// == Private methods ==
	/**
	 * Associates EventKeys and ModKeys with particular KeyEvents
	 * to create the controls for the game. These EventKeys and
	 * ModKeys are also associated with a boolean to identify when
	 * each action is activated.
	 */
	private static void setControls() {
		heldControls.put(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN), KeyEvent.VK_DOWN);
		heldControls.put(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT), KeyEvent.VK_LEFT);
		heldControls.put(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT), KeyEvent.VK_RIGHT);
		heldControls.put(Constant.concatKeys(EventKey.MOVE, ModKey.UP), KeyEvent.VK_UP);
		
		tapControls.put(Constant.concatKeys(EventKey.EXIT, ModKey.GAME), KeyEvent.VK_ESCAPE);
		tapControls.put(Constant.concatKeys(EventKey.PAUSE, ModKey.GAME), KeyEvent.VK_P);
		
		for (String i: heldControls.keySet()) {
			heldStatus.put(heldControls.get(i), false);
		}
		
		for (String i: tapControls.keySet()) {
			tapStatus.put(tapControls.get(i), false);
		}
	}
	
	
	// == Public methods ==
	@Override
	public boolean dispatchKeyEvent(KeyEvent evt) {
		System.out.println(evt.getID());
		Integer key = evt.getKeyCode();
		
		if (evt.getID() == KeyEvent.KEY_PRESSED && tapStatus.containsKey(key)) {
			tapStatus.put(key, true);
			processTappedKey(key);
		}
		else if (evt.getID() == KeyEvent.KEY_RELEASED && tapStatus.containsKey(key)){
			tapStatus.put(key, false);
		}
		else if (evt.getID() == KeyEvent.KEY_PRESSED && heldStatus.containsKey(key)) {
			heldStatus.put(key, true);
		}
		else if (evt.getID() == KeyEvent.KEY_RELEASED && heldStatus.containsKey(key)) {
			heldStatus.put(key, false);
		}
		
		return false;
	}
	
	/**
	 * Loops over status and identifies those keys which are activated.
	 * If a key is activated, the appropriate function is called.
	 * <p>
	 * Contains hard link between EventKeys/ModKeys and actions.
	 */
	public static void processHeldKeys() {
		//System.out.println("Checked");
		for (Integer i: heldStatus.keySet()) {
			if (heldStatus.get(i)) {
				if (i == heldControls.get(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN))) {
					Game.getPlayer().moveDown(5);
				}
				else if (i == heldControls.get(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT))) {
					Game.getPlayer().moveLeft(5);
				}
				else if (i == heldControls.get(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT))) {
					Game.getPlayer().moveRight(5);
				}
				else if (i == heldControls.get(Constant.concatKeys(EventKey.MOVE, ModKey.UP))) {
					Game.getPlayer().moveUp(5);
				}
			}
		}
	}
	
	public static void processTappedKey(int key) {
		if (key == tapControls.get(Constant.concatKeys(EventKey.EXIT, ModKey.GAME))) {
			Game.setPaused(true);
			Game.setRunning(false);
		}
		else if (key == tapControls.get(Constant.concatKeys(EventKey.PAUSE, ModKey.GAME))) {
			Game.togglePaused();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent evt) {
		
	}
	
	@Override
	public void mouseReleased(MouseEvent evt) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent evt) {
		
	}
	
	@Override
	public void mouseEntered(MouseEvent evt) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent evt) {
		
	}
	
	@Override
	public void mouseDragged(MouseEvent evt) {
		
	}
	
	@Override
	public void mouseMoved(MouseEvent evt) {
		
	}
}
