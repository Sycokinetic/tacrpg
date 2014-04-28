package geowars.display;

import geowars.Game;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {
	private class LaunchListener implements ActionListener {
		public LaunchListener() {}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == launch) {
				Game.togglePaused();
				System.out.println(Game.isPaused());
			}
			else if (event.getSource() == exit) {
				Game.setPaused(true);
				Game.setRunning(false);
			}
		}
	}
	
	JButton launch;
	JButton exit;
	
	public MainMenuPanel() {
		super(new GridBagLayout());
		
		launch = new JButton("Launch");
		launch.addActionListener(new LaunchListener());
		this.add(launch);
		
		exit = new JButton("Exit");
		exit.addActionListener(new LaunchListener());
		this.add(exit);
	}
}
