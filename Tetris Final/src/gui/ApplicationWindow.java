package gui;

import shapes.Block;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ApplicationWindow extends JFrame {

	protected AnimationWindow animationWindow;

	public boolean buttonPressed = false;
	public int button;
	
	public ApplicationWindow(int blockColumns, int blockRows, int blockSize,Block[][] theboard,Block[][] thepreview) {
		super("Tetris The Game");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		animationWindow = new AnimationWindow(blockColumns,blockRows,blockSize,theboard,thepreview);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(new Dimension((7 + blockColumns) * blockSize, (4+blockRows) * blockSize));
		contentPane.add(animationWindow, BorderLayout.CENTER);
		setContentPane(contentPane);
		
		KeyListener listener = new KeyListener() {	
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				buttonPressed = false;
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				button = e.getKeyCode();
				buttonPressed = true;
			}
		};
		
		addKeyListener(listener);
		setFocusable(true);
	}

	public boolean updateBoard(Block[][] newBoard,Block[][] newPreview){
		
		return animationWindow.updateBoard(newBoard,newPreview);
	}
	public boolean updateLevel(int level){
		
		return animationWindow.updateLevel(level);
	}
	public boolean updateScore(int score){
		
		return animationWindow.updateScore(score);
	}
	public boolean updateLines(int lines){
		
		return animationWindow.updateLines(lines);
	}
}


	

