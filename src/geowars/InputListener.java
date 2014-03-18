package geowars;

import geowars.Constant.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

public class InputListener implements KeyListener, MouseListener, MouseMotionListener {
	private static HashMap<String, Integer> controls;
	private static HashMap<Integer, Boolean> status;
	
	public InputListener() {
		controls = new HashMap<String, Integer>();
		status = new HashMap<Integer, Boolean>();
		
		controls.put(Constant.concatKeys(EventKey.EXIT, ModKey.GAME), KeyEvent.VK_ESCAPE);
		controls.put(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN), KeyEvent.VK_DOWN);
		controls.put(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT), KeyEvent.VK_LEFT);
		controls.put(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT), KeyEvent.VK_RIGHT);
		controls.put(Constant.concatKeys(EventKey.MOVE, ModKey.UP), KeyEvent.VK_UP);
		
		for (String i: controls.keySet()) {
			status.put(controls.get(i), false);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		Integer key = evt.getKeyCode();
		status.put(key, true);
		
		//System.out.println(key);
	}
	
	@Override
	public void keyReleased(KeyEvent evt) {
		Integer key = evt.getKeyCode();
		status.put(key, false);
		
		//System.out.println(key + " released");
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
	
	public static void processKeys() {
		for (Integer i: status.keySet()) {
			if (status.get(i)) {
				if (i == controls.get(Constant.concatKeys(EventKey.EXIT, ModKey.GAME))) {
					Game.setRunning(false);
				}
				else if (i == controls.get(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN))) {
					Game.getPlayer().moveDown(10);
				}
				else if (i == controls.get(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT))) {
					Game.getPlayer().moveLeft(10);
				}
				else if (i == controls.get(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT))) {
					Game.getPlayer().moveRight(10);
				}
				else if (i == controls.get(Constant.concatKeys(EventKey.MOVE, ModKey.UP))) {
					Game.getPlayer().moveUp(10);
				}
			}
			else {
				
			}
		}
	}
}
