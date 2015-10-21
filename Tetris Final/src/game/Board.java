package game;


import shapes.Block;
import shapes.BlockStatus;


public class Board {
	
	private int width;
	private int height;
	private Block [][] board;

	public Board(int x, int y) {		
		width = x; 
		height = y;
		
		board = new Block[width][height];
		
		for(int i=0; i<height; i++){
			for(int k=0; k<width; k++){
				board[k][i] = new Block(BlockStatus.EMPTY);
			}
		}
		
		createBorders();
	}
	
	private void createBorders(){
		for(int i=0; i<width; i++){
			board[i][height-1].setStatus(BlockStatus.BORDER);
		}
		for(int i=0; i<height; i++){
			board[0][i].setStatus(BlockStatus.BORDER);
		}
		for(int i=0; i<height; i++){
			board[width-1][i].setStatus(BlockStatus.BORDER);
		}
	}
	
	
	public void setBlockStatus(int x, int y, BlockStatus status){
		board[x][y].setStatus(status);
	}
	
	
	public boolean isActive(int x, int y){
		if(board[x][y].getStatus() == BlockStatus.ACTIVE){
			return true;
		}else{
			return false;
		}
	}

	
	public boolean isEmpty(int x, int y){
		if(board[x][y].getStatus() == BlockStatus.EMPTY){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean isPassive(int x, int y){
		if(board[x][y].getStatus() == BlockStatus.PASSIVE){
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean isBorder(int x, int y){
		if(board[x][y].getStatus() == BlockStatus.BORDER){
			return true;
		}else{
			return false;
		}
	}

	
	public Block[][] getBoardArray(){
		return board;
	}

	
}
