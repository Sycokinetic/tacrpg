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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

public class InputListener implements KeyListener, MouseListener, MouseMotionListener {
	// == Private attributes ==
	private static HashMap<String, Integer> controls;
	private static HashMap<Integer, Boolean> status;
	
	
	// == Constructors ==
	/**
	 * Class constructor
	 * <p>
	 * Initializes controls and status and creates the control scheme
	 */
	public InputListener() {
		controls = new HashMap<String, Integer>();
		status = new HashMap<Integer, Boolean>();
		
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
		controls.put(Constant.concatKeys(EventKey.EXIT, ModKey.GAME), KeyEvent.VK_ESCAPE);
		controls.put(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN), KeyEvent.VK_DOWN);
		controls.put(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT), KeyEvent.VK_LEFT);
		controls.put(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT), KeyEvent.VK_RIGHT);
		controls.put(Constant.concatKeys(EventKey.MOVE, ModKey.UP), KeyEvent.VK_UP);
		
		for (String i: controls.keySet()) {
			status.put(controls.get(i), false);
		}
	}
	
	
	// == Public methods ==
	/**
	 * Loops over status and identifies those keys which are activated.
	 * If a key is activated, the appropriate function is called.
	 * <p>
	 * Contains hard link between EventKeys/ModKeys and actions.
	 */
	public static void processKeys() {
		for (Integer i: status.keySet()) {
			if (status.get(i)) {
				if (i == controls.get(Constant.concatKeys(EventKey.EXIT, ModKey.GAME))) {
					Game.setRunning(false);
				}
				else if (i == controls.get(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN))) {
					Game.getPlayer().moveDown(5);
				}
				else if (i == controls.get(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT))) {
					Game.getPlayer().moveLeft(5);
				}
				else if (i == controls.get(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT))) {
					Game.getPlayer().moveRight(5);
				}
				else if (i == controls.get(Constant.concatKeys(EventKey.MOVE, ModKey.UP))) {
					Game.getPlayer().moveUp(5);
				}
			}
			else {
				
			}
		}
	}
	
	/**
	 * If a key is pressed and has been set by setControls(),
	 * then that key's status is set to true.
	 */
	@Override
	public void keyPressed(KeyEvent evt) {
		Integer key = evt.getKeyCode();
		if (status.containsKey(key)) {
			status.put(key, true);
		}
	}
	
	/**
	 * If a key is released and has been set by setControls(),
	 * then that key's status is set to false.
	 */
	@Override
	public void keyReleased(KeyEvent evt) {
		Integer key = evt.getKeyCode();
		if (status.containsKey(key)) {
			status.put(key, false);
		}
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		
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
