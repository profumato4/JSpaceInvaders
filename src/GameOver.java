import javax.swing.JPanel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOver extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public GameOver(Font font, Main main) {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				main.getFrame().dispose();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Main window = new Main();
							window.getFrame().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		setOpaque(false);
		setSize(656, 700);
		setLayout(null);
		
		JLabel game = new JLabel("GAME");
		game.setFont(font.deriveFont(Font.PLAIN, 80));
		game.setForeground(new Color(255, 255, 255));
		game.setBounds(146, 145, 455, 180);
		add(game);
		
		JLabel over = new JLabel("OVER");
		over.setFont(font.deriveFont(Font.PLAIN, 80));
		over.setForeground(new Color(255, 255, 255));
		over.setBounds(146, 296, 429, 180);
		add(over);
		
		
	}
}
