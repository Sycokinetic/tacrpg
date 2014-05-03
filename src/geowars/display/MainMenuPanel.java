package geowars.display;

import geowars.Game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
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
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets.set(5, 5, 5, 5);
		
		c.gridx = 0;
		c.gridy = 0;
		launch = new JButton("Launch");
		launch.addActionListener(new LaunchListener());
		this.add(launch, c);

		c.gridx = 1;
		exit = new JButton("Exit");
		exit.addActionListener(new LaunchListener());
		this.add(exit, c);

		c.gridx = 0;
		c.gridy = 1;
		this.add(new JLabel("P = Pause"), c);
		
		c.gridx = 1;
		this.add(new JLabel("Esc = Exit"), c);
	}
}
