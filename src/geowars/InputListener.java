package geowars;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.Field;
import java.util.HashMap;

public class InputListener implements KeyListener, MouseListener, MouseMotionListener {
	private static enum KeyFunction {
		QUIT,
		MOVE_DOWN,
		MOVE_LEFT,
		MOVE_RIGHT,
		MOVE_UP,
		JUMP
	}
	
	private static HashMap<KeyFunction, Integer> controls;
	private static HashMap<Integer, Boolean> status;
	
	public InputListener() {
		controls = new HashMap<KeyFunction, Integer>();
		status = new HashMap<Integer, Boolean>();
		
		controls.put(KeyFunction.QUIT, KeyEvent.VK_ESCAPE);
		controls.put(KeyFunction.MOVE_DOWN, KeyEvent.VK_DOWN);
		controls.put(KeyFunction.MOVE_LEFT, KeyEvent.VK_LEFT);
		controls.put(KeyFunction.MOVE_RIGHT, KeyEvent.VK_RIGHT);
		controls.put(KeyFunction.MOVE_UP, KeyEvent.VK_UP);
		controls.put(KeyFunction.JUMP, KeyEvent.VK_SPACE);
		
		for (Field i: KeyEvent.class.getFields()) {
			if (i.getName().contains("VK_"	)) {
				try{
					Integer val = i.getInt(i);
					status.put(val, false);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();
		status.put(key, true);

		if (key == controls.get(KeyFunction.QUIT)) {
			Game.setRunning(false);
		}
		else if (key == controls.get(KeyFunction.MOVE_DOWN)) {
			Game.getPlayer().moveDown(10);
		}
		else if (key == controls.get(KeyFunction.MOVE_LEFT)) {
			Game.getPlayer().moveLeft(10);
		}
		else if (key == controls.get(KeyFunction.MOVE_RIGHT)) {
			Game.getPlayer().moveRight(10);
		}
		else if (key == controls.get(KeyFunction.MOVE_UP)) {
			Game.getPlayer().moveUp(10);
		}
		else if (key == controls.get(KeyFunction.JUMP)) {
			Game.getPlayer().setJumping(true);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent evt) {
		int key = evt.getKeyCode();
		status.put(key, false);
		
		if (key == controls.get(KeyFunction.JUMP)) {
			Game.getPlayer().setJumping(false);
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
