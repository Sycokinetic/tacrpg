package geowars.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Controller {
	private static HashMap<String, UserAction> controls;
	private static HashMap<String, Boolean> heldStatus;
	private static HashMap<String, Boolean> toggleStatus;
	
	private static UserAction moveUp;
	private static UserAction moveLeft;
	private static UserAction moveRight;
	private static UserAction exit;
	private static UserAction pause;
	
	private JComponent component;
	
	public Controller(JComponent c) {
		this.component = c;
		controls = new HashMap<String, UserAction>();
		heldStatus = new HashMap<String, Boolean>();
		toggleStatus = new HashMap<String, Boolean>();
	}
	
	public abstract static class UserAction extends AbstractAction implements ActionListener {
		private String name;
		private boolean pressed;
		private boolean held;
		
		UserAction(String nm, boolean p, boolean h) {
			this.name = nm;
			this.pressed = p;
			this.held = h;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (held) {
				if (this.pressed) {
					heldStatus.put(this.name, true);
				}
				else {
					heldStatus.put(this.name, false);
				}
			}
			else {
				if (this.pressed && !toggleStatus.get(this.name)) {
					toggleStatus.put(this.name, true);
					action();
				}
				else if (!this.pressed) {
					toggleStatus.put(this.name, false);
				}
			}
		}
		
		public String getName() {
			return this.name;
		}
		
		public boolean isHeld() {
			return this.held;
		}
		
		public abstract void action();
	}
	
	public void addAction(int keyCode, UserAction action) {
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(keyCode, 0);
		String releaseString = KeyStroke.getKeyStroke(keyCode, 0).toString().replace("pressed", "released");
		KeyStroke releasedKeyStroke = KeyStroke.getKeyStroke(releaseString);
		
		InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(pressedKeyStroke, action);
		component.getActionMap().put(action, action);
		
		UserAction releasedAction = new UserAction(action.getName(), false, action.isHeld()) {
			public void action() {}
		};
		
		inputMap.put(releasedKeyStroke, releasedAction);
		component.getActionMap().put(releasedAction, releasedAction);
		
		controls.put(action.getName(), action);
		if (action.isHeld()) {
			heldStatus.put(action.getName(), false);
		}
		else {
			toggleStatus.put(action.getName(), false);
		}
	
	}
	
	public static void processKeys() {
		for (String i: heldStatus.keySet()) {
			if (heldStatus.get(i)) {
				controls.get(i).action();
			}
		}
	}
}
