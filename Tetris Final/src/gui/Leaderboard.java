package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import application.GameController;
import storage.TetrisStorage;

@SuppressWarnings("serial")
public class Leaderboard extends JFrame {

	private JLabel[] highScoresLabels;
	private JButton returnToMainMenuButton;
	private static final int NUMBER_OF_BUTTONS = 2;
	private JFrame frame;
	private JButton cleanButton;
	
	public Leaderboard(JFrame frame) {
		super("Leaderboard");
		this.frame = frame;
		String[] highScores = TetrisStorage.getLeaderBoard().split("\n");
		highScoresLabels = new JLabel[highScores.length];
		setLayout(new GridLayout(highScores.length + NUMBER_OF_BUTTONS, 1));
		for (int i = 0; i < highScoresLabels.length; i++) {
			highScoresLabels[i] = new JLabel(highScores[i]);
			add(highScoresLabels[i]);
		}
		cleanButton = new JButton("Erase Leaderboard");
		add(cleanButton);
		returnToMainMenuButton = new JButton("Return to Main Menu");
		add(returnToMainMenuButton);
		
		ButtonListener bl = new ButtonListener();
		cleanButton.addActionListener(bl);
		returnToMainMenuButton.addActionListener(bl);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		dispose();
		setUndecorated(true);
		getRootPane( ).setWindowDecorationStyle(3);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setAlwaysOnTop(false);
		setVisible(true);
		
	}
	
	private Leaderboard getThis() {return this;}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == returnToMainMenuButton) {
				StartWindow sw = new StartWindow();
				sw.setVisible(true);
				GameController.closeGame(frame);
				dispose();
			} else if (e.getSource() == cleanButton) {
				String[] options = {"Yes", "No"};
				int n = JOptionPane.showOptionDialog(getThis(),
					    "All leaderboard will be erased\n"
					    + "Do you want to continue?",
					    "Warning",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    options ,
					    options[1]); 
				if (n == 0) {
					
					try {
						TetrisStorage.cleanLeaderBoard();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(getThis(), "File operation unsuccessful");
						System.exit(1);
					}
					GameController.closeGame(frame);
					dispose();
					StartWindow sw = new StartWindow();
					sw.setVisible(true);
					
				} 
				
			}
		}	
	}		
}

