package gui;

import application.GameController;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import sound.Mp3;

@SuppressWarnings("serial")
public class StartWindow extends JFrame {
	private JButton startGameButton;
	private JButton optionsButton;
	private JButton exitButton;
	private GameController gc = new GameController();
	private Thread t = new Thread(gc);
	private Mp3 music;
	
	
	public StartWindow() {
		super("Tetris The Game");
		
		music = new Mp3("sounds/menu.sound");
		music.play();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getRootPane( ).setWindowDecorationStyle(3);
		setVisible(true);
		setLayout(new GridLayout(3, 1));
		
		startGameButton = new JButton("Play");
		add(startGameButton);
		optionsButton = new JButton("Options");
		add(optionsButton);
		exitButton = new JButton("Quit");
		add(exitButton);
		
		StartWindowHandler handler = new StartWindowHandler();
		startGameButton.addActionListener(handler);
		optionsButton.addActionListener(handler);
		exitButton.addActionListener(handler);
		
		
	}
	
	private StartWindow getThis() {
		return this;
	}
	private class StartWindowHandler implements ActionListener {
		

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startGameButton) {
				gc = new GameController();
				t = new Thread(gc);
				t.start();
				music.close();
				dispose();
			} else if (e.getSource() == optionsButton){
				OptionsUI window = new OptionsUI(getThis());
				window.setSize(600, 500);
				
				window.setResizable(false);
				window.setLocationRelativeTo(null);
				
			} else if (e.getSource() == exitButton) {
				String[] options = {"Yes", "No"};
				int n = JOptionPane.showOptionDialog(getThis(),
					    "Are you sure you want to quit?\n",
					    "Quit",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.CANCEL_OPTION,
					    null,
					    options ,
					    options[1]); 
				if (n == 0) {
					
					music.close();
					System.exit(0);

					
				} 
			}
		}
		
	}
}
