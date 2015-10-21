package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import shapes.*;

public class TetrisBoard {
	public int boardColumns;
	public int boardRows;
	public int blockSize;
	public Block[][] blockGrid;
	public Block[][] prevBlockGrid;
	private HashMap<String,Color> blockColors;

	public TetrisBoard(int blockSize, int columns, int rows, Block[][] theboard, Block[][] thepreview){
		this.boardColumns = theboard.length;
		this.boardRows = theboard[0].length;
		this.blockSize = blockSize;
		this.blockGrid = theboard;
		Color borderColor = new Color(154,173,180);
		Color emptyColor = new Color(0,70,150);
		
		blockColors = new HashMap<String, Color>();
		blockColors.put("BORDER",  borderColor);
		blockColors.put("EMPTY", emptyColor);
		blockColors.put("PASSIVE", Color.ORANGE);
		blockColors.put("ACTIVE", Color.yellow);
		
		this.prevBlockGrid = thepreview;
	}

	public boolean update(Block[][] newGrid, Block[][] newPrevGrid){
		this.prevBlockGrid = newPrevGrid;
		this.blockGrid = newGrid;
		return true;
	}
	
	public void paint(Graphics g){
		if (blockGrid == null || prevBlockGrid == null) {
			return;
		}
		for (int i = 0; i < this.blockGrid.length; i++) {
			for (int j = 2; j < this.blockGrid[0].length; j++) {
				int x = i * this.blockSize; 
				int y = (j-1) * this.blockSize;
				
				g.setColor(blockColors.get(this.blockGrid[i][j].getStatus().toString()));
				
				g.fillRect(x, y, this.blockSize, this.blockSize);
				if (this.blockGrid[i][j].getStatus() != BlockStatus.BORDER) {
					g.setColor(Color.BLACK);
					g.drawRect(x, y, this.blockSize, this.blockSize);
				}
			}
		}
		for (int i = 0; i < this.prevBlockGrid.length; i++) {
			for (int j = 0; j < this.prevBlockGrid[0].length; j++) {
				g.setColor(blockColors.get(this.prevBlockGrid[i][j].getStatus().toString()));
				int x = (this.blockGrid.length + i) * this.blockSize;
				int y = (j+1) * this.blockSize;
				g.fillRect(x, y, this.blockSize, this.blockSize);
				g.setColor(Color.BLACK);
				g.drawRect(x, y, this.blockSize, this.blockSize);
			}
		}
	}
}
