package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import shapes.Block;


@SuppressWarnings("serial")
public class AnimationWindow extends JPanel {
	
	private TetrisBoard board;
	private JPanel boardFrame;
	private JLabel scoreLabel;
	private JLabel levelLabel;
	private JLabel linesLabel;

	public AnimationWindow(int columns, int rows, int blockSize,Block[][] theboard,Block[][] thepreview) {
		super();
		
		board = new TetrisBoard(blockSize,columns,rows,theboard,thepreview);
		boardFrame = new JPanel();
		boardFrame.setPreferredSize(new Dimension(board.blockSize * board.boardColumns, board.blockSize * board.boardRows));
		Color borderColor = new Color(154,173,180);
		boardFrame.setBackground(borderColor);
		scoreLabel = new JLabel("0",SwingConstants.RIGHT);
		levelLabel = new JLabel("0",SwingConstants.RIGHT);
		linesLabel = new JLabel("0",SwingConstants.RIGHT);
		setBackground(borderColor);
		
		this.add(boardFrame, BorderLayout.WEST);
		JPanel p = new JPanel(new GridLayout(3,2));
		p.setBounds(board.blockSize * board.boardColumns, board.blockSize * 5, board.blockSize * 5, board.blockSize * (board.boardRows - 5));
		p.add(new JLabel("Level"));
		p.add(levelLabel);
		p.add(new JLabel("Lines"));
		p.add(linesLabel);
		p.add(new JLabel("Score"));
		p.add(scoreLabel);
		p.setBackground(borderColor);
		this.add(p, BorderLayout.EAST);
	}
	
	private void setScore(int score){
		scoreLabel.setText(score+"");
	}
	
	private void setLevel(int level){
		levelLabel.setText(level+"");
	}
	
	private void setLines(int lines){
		linesLabel.setText(lines+"");
	}
	public boolean updateBoard(Block[][] newBoard,Block[][] newPreview){
		board.update(newBoard,newPreview);
		return true;
	}
	public boolean updateLevel(int level){
		setLevel(level);
		return true;
	}
	public boolean updateScore(int score){
		setScore(score);
		return true;
	}
	public boolean updateLines(int lines){
		setLines(lines);
		return true;
	}

	public boolean isFocusTraversable() {
		return true;
	}

	public void paint(Graphics g) {
		super.paint(g);

		board.paint(g);
	}
}