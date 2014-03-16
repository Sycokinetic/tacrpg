package geowars;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputListener implements KeyListener, MouseListener, MouseMotionListener {
	@Override
	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE) {
			Game.setRunning(false);
		}
		else if (key == KeyEvent.VK_DOWN){
			Game.getPlayer().moveDown(10);
		}
		else if (key == KeyEvent.VK_LEFT) {
			Game.getPlayer().moveLeft(10);
		}
		else if (key == KeyEvent.VK_RIGHT) {
			Game.getPlayer().moveRight(10);
		}
		else if (key == KeyEvent.VK_UP) {
			Game.getPlayer().moveUp(10);
		}
		else if (key == KeyEvent.VK_SPACE) {
			Game.getPlayer().jump();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent evt) {
		
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
