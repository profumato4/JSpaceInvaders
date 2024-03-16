import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.util.Random;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class Main {

	private JFrame frame;
	private int score = 0;
	private int x = 287;
	private int y = 625;
	private int bulletY = 600;
	private JLabel[] aliens;
	private Rectangle[] aliensRec = new Rectangle[24];
	private Rectangle playerRec;
	private JLabel scoreLabel;
	private int lifes = 3;
	private JLabel lifeLabel1;
	private JLabel lifeLabel2;
	private JLabel lifeLabel3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().setLayout(null);
		
		JLabel player = new JLabel("");
		player.setIcon(new ImageIcon("res/player.png"));
		player.setBounds(287, 625, 60, 35);
		frame.getContentPane().add(player);
		playerRec = player.getBounds();
		
		aliens = aliensSpawn();
		score();
		fireAlien();
		lifes();
		
		for(int i = 0;i < aliens.length;i++) {
			aliensRec[i] = aliens[i].getBounds();
		}
		
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_RIGHT) {
					moveRight(player);
					playerRec = player.getBounds();
				}
				
				if(keyCode == KeyEvent.VK_LEFT) {
					moveLeft(player);
					playerRec = player.getBounds();
				}
				
				if(keyCode == KeyEvent.VK_SPACE) {
					firePlayer();
				}
				
			} 
		});
		frame.setBounds(100, 100, 656, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);;
	}
	
	private void moveRight(JLabel player) {
		x += 20;
		if(x > 564) {
			x = 574;
		}
		player.setBounds(x, y, 60, 35);
		System.out.println(x);
	}
	
	private void moveLeft(JLabel player) {
		x -= 20;
		if(x < 4) {
			x = 4;
		}
		player.setBounds(x, y, 60, 35);
		System.out.println(x);
	}
	
	private void firePlayer() {
		JLabel bullet = new JLabel("");
		bullet.setBackground(Color.YELLOW);
		bullet.setOpaque(true);
		bullet.setBounds(x+24, bulletY, 10, 10);
		frame.getContentPane().add(bullet);
		frame.repaint();
		frame.revalidate();
		
		
		Timer timer = new Timer(5, new ActionListener() {
			private int x1 = x;
			private int y = bulletY;
			private Rectangle rec = bullet.getBounds();

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(y == 0) {
					((Timer) e.getSource()).stop();
					frame.getContentPane().remove(bullet);
					frame.repaint();
					frame.revalidate();
				}
				
				if(y > 0) {
					y = Math.min(y - 20, 600);
					bullet.setBounds(x1+24, y, 10, 10);
					rec = bullet.getBounds();
					frame.repaint();
					frame.revalidate();
				}
				
				for(int i = 0;i < aliensRec.length;i++) {
					if(aliensRec[i].intersects(rec)) {
						System.out.print(5);
						frame.getContentPane().remove(aliens[i]);
						y = 0;
						aliensRec[i] = new Rectangle();
						score += 10;
						scoreLabel.setText("SCORE: " + score);
						frame.repaint();
						frame.revalidate();
					}
				}
				
			}
			
		});
		
		timer.start();
	}
	
	private void fireAlien() {
		new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int r = new Random().nextInt(6);
				JLabel bullet = new JLabel("");
				bullet.setBackground(Color.YELLOW);
				bullet.setOpaque(true);
				bullet.setBounds(aliens[r].getX() + 27, aliens[r].getY() + 50, 10, 12);
				frame.getContentPane().add(bullet);
				frame.repaint();
				frame.revalidate();
				
				new Timer(5, new ActionListener() {
					private int x = aliens[r].getX() + 27;
					private int y = aliens[r].getY() + 50;
					private Rectangle rec = bullet.getBounds();
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if(y == 640) {
							((Timer) e.getSource()).stop();
							frame.getContentPane().remove(bullet);
							frame.repaint();
							frame.revalidate();
						}
						
						if(y < 640) {
							y = Math.min(y + 20, 640);
							bullet.setBounds(x, y, 10, 12);
							rec = bullet.getBounds();
							frame.repaint();
							frame.revalidate();
						}
						
						if (playerRec.intersects(rec)) {
							System.out.println(104);
							switch(lifes) {
								case 3:
									lifeLabel1.setIcon(new ImageIcon("res/life3.png"));
									break;
									
								case 2:
									lifeLabel2.setIcon(new ImageIcon("res/life3.png"));
									break;
								
								case 1:
									lifeLabel3.setIcon(new ImageIcon("res/life3.png"));
									break;
								
								case 0:
									System.out.println("hai perso");
									
							}
							lifes--;
							
							y = 640;
							System.out.println(lifes);
							rec = new Rectangle();
						}

					}
					
				}).start();
				
			}
		}).start();;
		
			
	}
	
	private JLabel[] aliensSpawn() {
		JLabel alien_1 = new JLabel("");
		alien_1.setIcon(new ImageIcon("res/alien.png"));
		alien_1.setBounds(90, 159, 60, 60);
		frame.getContentPane().add(alien_1);
		
		JLabel alien_2 = new JLabel("");
		alien_2.setIcon(new ImageIcon("res/alien.png"));
		alien_2.setBounds(160, 159, 60, 60);
		frame.getContentPane().add(alien_2);
		
		JLabel alien_3 = new JLabel("");
		alien_3.setIcon(new ImageIcon("res/alien.png"));
		alien_3.setBounds(230, 159, 60, 60);
		frame.getContentPane().add(alien_3);
		
		JLabel alien_4 = new JLabel("");
		alien_4.setIcon(new ImageIcon("res/alien.png"));
		alien_4.setBounds(300, 159, 60, 60);
		frame.getContentPane().add(alien_4);
		
		JLabel alien_5 = new JLabel("");
		alien_5.setIcon(new ImageIcon("res/alien.png"));
		alien_5.setBounds(367, 159, 60, 60);
		frame.getContentPane().add(alien_5);
		
		JLabel alien_6 = new JLabel("");
		alien_6.setIcon(new ImageIcon("res/alien.png"));
		alien_6.setBounds(437, 159, 60, 60);
		frame.getContentPane().add(alien_6);
		
		JLabel alien_7 = new JLabel("");
		alien_7.setIcon(new ImageIcon("res/alien.png"));
		alien_7.setBounds(90, 222, 60, 60);
		frame.getContentPane().add(alien_7);
		
		JLabel alien_8 = new JLabel("");
		alien_8.setIcon(new ImageIcon("res/alien.png"));
		alien_8.setBounds(160, 222, 60, 60);
		frame.getContentPane().add(alien_8);
		
		JLabel alien_9 = new JLabel("");
		alien_9.setIcon(new ImageIcon("res/alien.png"));
		alien_9.setBounds(230, 222, 60, 60);
		frame.getContentPane().add(alien_9);
		
		JLabel alien_10 = new JLabel("");
		alien_10.setIcon(new ImageIcon("res/alien.png"));
		alien_10.setBounds(300, 222, 60, 60);
		frame.getContentPane().add(alien_10);
		
		JLabel alien_11 = new JLabel("");
		alien_11.setIcon(new ImageIcon("res/alien.png"));
		alien_11.setBounds(367, 222, 60, 60);
		frame.getContentPane().add(alien_11);
		
		JLabel alien_12 = new JLabel("");
		alien_12.setIcon(new ImageIcon("res/alien.png"));
		alien_12.setBounds(437, 222, 60, 60);
		frame.getContentPane().add(alien_12);
		
		JLabel alien_13 = new JLabel("");
		alien_13.setIcon(new ImageIcon("res/alien.png"));
		alien_13.setBounds(90, 283, 60, 60);
		frame.getContentPane().add(alien_13);
		
		JLabel alien_14 = new JLabel("");
		alien_14.setIcon(new ImageIcon("res/alien.png"));
		alien_14.setBounds(160, 283, 60, 60);
		frame.getContentPane().add(alien_14);
		
		JLabel alien_15 = new JLabel("");
		alien_15.setIcon(new ImageIcon("res/alien.png"));
		alien_15.setBounds(230, 283, 60, 60);
		frame.getContentPane().add(alien_15);
		
		JLabel alien_16 = new JLabel("");
		alien_16.setIcon(new ImageIcon("res/alien.png"));
		alien_16.setBounds(300, 283, 60, 60);
		frame.getContentPane().add(alien_16);
		
		JLabel alien_17 = new JLabel("");
		alien_17.setIcon(new ImageIcon("res/alien.png"));
		alien_17.setBounds(367, 283, 60, 60);
		frame.getContentPane().add(alien_17);
		
		JLabel alien_18 = new JLabel("");
		alien_18.setIcon(new ImageIcon("res/alien.png"));
		alien_18.setBounds(437, 283, 60, 60);
		frame.getContentPane().add(alien_18);
		
		JLabel alien_19 = new JLabel("");
		alien_19.setIcon(new ImageIcon("res/alien.png"));
		alien_19.setBounds(90, 346, 60, 60);
		frame.getContentPane().add(alien_19);
		
		JLabel alien_20 = new JLabel("");
		alien_20.setIcon(new ImageIcon("res/alien.png"));
		alien_20.setBounds(160, 346, 60, 60);
		frame.getContentPane().add(alien_20);
		
		JLabel alien_21 = new JLabel("");
		alien_21.setIcon(new ImageIcon("res/alien.png"));
		alien_21.setBounds(230, 346, 60, 60);
		frame.getContentPane().add(alien_21);
		
		JLabel alien_22 = new JLabel("");
		alien_22.setIcon(new ImageIcon("res/alien.png"));
		alien_22.setBounds(300, 346, 60, 60);
		frame.getContentPane().add(alien_22);
		
		JLabel alien_23 = new JLabel("");
		alien_23.setIcon(new ImageIcon("res/alien.png"));
		alien_23.setBounds(367, 346, 60, 60);
		frame.getContentPane().add(alien_23);
		
		JLabel alien_24 = new JLabel("");
		alien_24.setIcon(new ImageIcon("res/alien.png"));
		alien_24.setBounds(437, 346, 60, 60);
		frame.getContentPane().add(alien_24);
		
		JLabel[] aliens = {alien_1,alien_2,alien_3,alien_4,alien_5,alien_6
							,alien_7,alien_8,alien_9,alien_10,alien_11,alien_12
							,alien_13,alien_14,alien_15,alien_16,alien_17,alien_18
							,alien_19,alien_20,alien_21,alien_22,alien_23,alien_24};
		
		return aliens;
	}
	
	private void score() {
		File file = new File("res/font/arcadeFont.ttf");
		Font arcadeFont = null;
		try {
			System.out.println(file);
			arcadeFont = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(Font.PLAIN, 30);
        } catch (Exception e) {
            System.err.println("Impossibile caricare il font");
            arcadeFont = new Font("Arial", Font.PLAIN, 30);
        }
		scoreLabel = new JLabel("SCORE: " + score);
		scoreLabel.setFont(arcadeFont);
		scoreLabel.setForeground(new Color(255, 255, 255));
		scoreLabel.setBounds(10, 11, 321, 47);
		frame.getContentPane().add(scoreLabel);
		
		
		
	}
	
	private void lifes() {
		lifeLabel1 = new JLabel("");
		lifeLabel1.setIcon(new ImageIcon("res\\lifes.png"));
		lifeLabel1.setBounds(559, 11, 71, 60);
		frame.getContentPane().add(lifeLabel1);
		
		lifeLabel2 = new JLabel("");
		lifeLabel2.setIcon(new ImageIcon("res\\lifes.png"));
		lifeLabel2.setBounds(478, 11, 71, 60);
		frame.getContentPane().add(lifeLabel2);
		
		lifeLabel3 = new JLabel("");
		lifeLabel3.setIcon(new ImageIcon("res\\lifes.png"));
		lifeLabel3.setBounds(397, 11, 71, 60);
		frame.getContentPane().add(lifeLabel3);
	}
	
}
