package gui;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import application.GameController;

@SuppressWarnings("serial")
public class PauseScreen extends JFrame {

	private JButton continueButton;
	private JButton quitButton;
	private JButton mMenuButton;
	public boolean cont;
	private GameController gc;
	
	private static final int NUMBER_OF_BUTTONS = 4;
	public PauseScreen(GameController gc) {
		super("Game Paused");
		cont = false;
		this.gc = gc;
		setLayout(new GridLayout(NUMBER_OF_BUTTONS, 1));
		dispose();
		setUndecorated(true);
		setVisible(true);
		add(new JLabel("Game Paused"));
		continueButton = new JButton("Continue");
		add(continueButton);
		mMenuButton = new JButton("End Game");
		add(mMenuButton);
		quitButton = new JButton("Quit");
		add(quitButton);
		ButtonListener bl = new ButtonListener();
		continueButton.addActionListener(bl);
		mMenuButton.addActionListener(bl);
		quitButton.addActionListener(bl);
		setResizable(false);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == continueButton) {
				cont = true;
				dispose();
			} else if (e.getSource() == mMenuButton ) {
				String[] options = {"Yes", "No"};
				int n = JOptionPane.showOptionDialog(null,
					    "Are you sure you want to end game\n"
					    + "(The progress will be lost!)\n",
					    "End Game?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.CANCEL_OPTION,
					    null,
					    options ,
					    options[1]); 
				if (n == 0) {
					dispose();
					gc.end = true;;
				} 
					
			}else if (e.getSource() == quitButton ) {
				String[] options = {"Yes", "No"};
				int n = JOptionPane.showOptionDialog(null,
					    "Are you sure you want to quit?\n"
					    + "(The progress will be lost!)\n",
					    "End Game?",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.CANCEL_OPTION,
					    null,
					    options ,
					    options[1]); 
				if (n == 0) {
					System.exit(1);
				} 
				
				
			}
		}
	}
}
