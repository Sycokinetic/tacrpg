package geowars.display;

import geowars.Game;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {
	private class LaunchListener implements ActionListener {
		public LaunchListener() {
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == launch) {
				Game.togglePaused();
			} else if (event.getSource() == exit) {
				Game.stop();
			}
		}
	}

	private JButton launch;
	private JButton exit;
	
	private static final long serialVersionUID = 1L;

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
