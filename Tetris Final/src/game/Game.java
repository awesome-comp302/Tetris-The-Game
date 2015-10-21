package game;


import settings.OptionSet;
import settings.ShapeOptions;
import shapes.*;

import java.util.Random;

import sound.Mp3;



public class Game {
	
	private Board gameBoard;
	private int width;
	private int height;
	private int currentShape;
	private int nextShape;
	private Block[][] shapeArray;
	private int refX;
	private int refY;
	private boolean createNextShape;
	private final int ghost = 3;
	private int startRefX;
	private int startRefY;
	private int oldRefX;
	private int oldRefY;
	private int orientation = 0;
	private Shape object;
	private int eCounter;
	private int score = 0;
	private int erasedLineNum = 0;
	private OptionSet cfg;
	private boolean FX;
	
	private static Mp3 moveFX;
	private static Mp3 rotateFX;
	private static Mp3 collideFX;
	private static Mp3 nEraseFX;
	private static Mp3 tEraseFX;

	public Game(OptionSet cfgInt) {
		cfg = cfgInt;
		width = cfg.getWidth() + 2;
		height = cfg.getHeight() + ghost +1;
		startRefX = ((width-1)/2);
		startRefY = 2;
		FX = cfg.getFXSound();
		if(FX){
			soundInit();
		}
		
		refX = startRefX;
		refY = startRefY;
		
		
		gameBoard = new Board(width,height);
		currentShape = setShape();
		nextShape = setShape();
		createNextShape = true;
		
	}
	
	
	private void updateBoard(Movements controller){

			if(controller == Movements.DOWN || (refY < ghost-1)){
				if(FX){
					moveFX.play();
				}
				tryMoveDown();
			}else
			if(controller == Movements.RIGHT){
				if(FX){
					moveFX.play();
				}
				tryMoveRight();
			}else
			if(controller == Movements.LEFT){
				if(FX){
					moveFX.play();
				}
				tryMoveLeft();
			}else 
			if(controller == Movements.ROTATION){
				if(FX){
					rotateFX.play();
				}
				oldRefX = refX;
				oldRefY = refY;
				
				if(refX == 1)refX++;
				
				if(refX >= (width-3)) {
					if(currentShape == 0){
						if(refX == (width-3)) refX++;
						refX--;
						refX--;
					}else
					if(refX == (width-2)) refX--;
				}
				if(refY == (height-1)) refY--;
				rotate();
			}/*else 
				if(controller == 4 && currentShape !=2){
					while(!isAnyActiveCollides()){
						tryMoveDown();
					}
					tryMoveDown();
					System.out.println("All way down!");
				}*/
	}
	
	private void tryMoveDown(){
		if(isAnyActiveCollides()){
			if(FX){
				collideFX.play();
			}
			
			allActivesToPassive();
			searcAndDestroyCompletedRows();
			
			currentShape = nextShape;
			createNextShape = true;
			refX = startRefX;
			refY = startRefY;
			orientation = 0;
			nextShape = setShape();
			
		}else{
			
			refY++;
			for(int i=width-1; i>0; i--){
				for(int k=height-1; k>=0; k--){
					if(gameBoard.isActive(i,k)){
						gameBoard.setBlockStatus(i, k, BlockStatus.EMPTY);
						gameBoard.setBlockStatus(i, k+1, BlockStatus.ACTIVE);
					}
				}
			}
			
		}
	}
	
	private void tryMoveRight(){
		for(int i=0; i<width-1; i++){
			for(int k=0; k<height; k++){
				if(gameBoard.isActive(i,k)){
					if(gameBoard.isPassive(i+1,k) || gameBoard.isBorder(i+1,k)){
						return;
					}
				}
			}
		}
		refX++;
		for(int i=width-1; i>0; i--){
			for(int k=height-1; k>=0; k--){
				if(gameBoard.isActive(i,k)){
					gameBoard.setBlockStatus(i, k, BlockStatus.EMPTY);
					gameBoard.setBlockStatus(i+1, k, BlockStatus.ACTIVE);
				}
			}
		}
		
	}
	
	private void tryMoveLeft(){
		for(int i=0; i<width-1; i++){
			for(int k=0; k<height; k++){
				if(gameBoard.isActive(i,k)){
					if(gameBoard.isPassive(i-1,k) || gameBoard.isBorder(i-1,k)){
						return;
					}
				}
			}
		}
		refX--;
		for(int i=0; i<width-1; i++){
			for(int k=0; k<=height-1; k++){
				if(gameBoard.isActive(i,k)){
					gameBoard.setBlockStatus(i, k, BlockStatus.EMPTY);
					gameBoard.setBlockStatus(i-1, k, BlockStatus.ACTIVE);
				}
			}
		}
		
	}
	
	private void rotate(){
		orientation++;
		insertShape();
	}
	
	private void searcAndDestroyCompletedRows(){
		eCounter=0;
		
		for(int y=height-2; y>0; y--){
			while(isRowCompleted(y)){
				eCounter++;
				eraseRow(y);
				fallPassiveRow(y);
			}
		}
		
		if(eCounter == 1 ){
			if(FX){
				nEraseFX.play();
			}
			score +=  1;
		}else if(eCounter == 2){
			if(FX){
				nEraseFX.play();
			}
			score +=  3;
		}else if(eCounter == 3){
			if(FX){
				nEraseFX.play();
			}
			score +=  6;
		}else if( eCounter == 4 ){
			if(FX){
				tEraseFX.play();
			}
			score +=  10;
		}	
		erasedLineNum += eCounter;		
		updateLevel();
	}
	
	private int setShape(){
		ShapeOptions sh = cfg.getShapeType();
		Random rand = new Random();
		if(sh == ShapeOptions.BOTH) return rand.nextInt(10);
		if(sh == ShapeOptions.TETRAMINO && sh != ShapeOptions.TRIMINO) return rand.nextInt(7);
		if(sh != ShapeOptions.TETRAMINO && sh == ShapeOptions.TRIMINO) return (rand.nextInt(3))+7;
		
		return 1;
	}

	private void createShape(){

		switch (currentShape){
		case 0:  
			object = new IShape (width, height, refX, refY, orientation);
			break;
		case 1:  
			object = new JShape (width, height, refX, refY, orientation);
			break;
		case 2:  
			object = new OShape (width, height, refX, refY);
			break;
		case 3:  
			object = new SShape (width, height, refX, refY, orientation);
			break;
		case 4: 
			object = new TShape (width, height, refX, refY, orientation);
			break;
		case 5:  
			object = new ZShape (width, height, refX, refY, orientation);
			break;
		case 6:  
			object = new LShape (width, height, refX, refY, orientation);
			break;
		case 7:  
			object = new SmallIShape (width, height, refX, refY, orientation);
			break;
		case 8:  
			object = new SmallJShape (width, height, refX, refY, orientation);
			break;
		case 9: 
			object = new SmallRShape (width, height, refX, refY, orientation);
			break;
		default:
			break;
		}
		
		shapeArray = object.getShapeArray();
	}
	
	private void insertShape(){
		createShape();
		if(!canInsert()){
			refY--;
		}else{
			insert();
			return;
		}
		createShape();
		if(!canInsert()){
			refY++;
			refX++;
		}else{
			insert();
			return;
		}
		createShape();
		if(!canInsert()){
			refX--;
			refX--;
		}else{
			insert();
			return;
		}
		createShape();
		if(!canInsert()){
			refX++;
			refY++;
		}else{
			insert();
			return;
		}
		createShape();
		if(!canInsert()){
			refX++;
			refY--;
			refY--;
		}else{
			insert();
			return;
		}
		createShape();
		if(!canInsert()){
			refX--;
			refX--;
		}else{
			insert();
			return;
		}
		createShape();
		if(!canInsert()){
			orientation--;
			refX = oldRefX;
			refY = oldRefY;
			return;
		}else{
			insert();
			return;
		}
		
	}
	
	private void insert(){
		allActivesToEmpty();
		for(int i=0; i<width-1; i++){
			for(int k=0; k<height; k++){
				if(shapeArray[i][k].getStatus() == BlockStatus.ACTIVE){
					gameBoard.setBlockStatus(i, k, BlockStatus.ACTIVE);
					
				}
			}
		}
	}
	
	private boolean canInsert(){
		for(int i=0; i<width; i++){
			for(int k=0; k<height; k++){
				if(shapeArray[i][k].getStatus() == BlockStatus.ACTIVE && (gameBoard.isPassive(i, k) || gameBoard.isBorder(i,  k))){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private void allActivesToPassive(){
		for(int i=0; i<height-1; i++){
			for(int k=0; k<width; k++){
				if(gameBoard.isActive(k,i)){
					gameBoard.setBlockStatus(k, i, BlockStatus.PASSIVE);
				}
			}
		}
	}
	
	private void allActivesToEmpty(){    
		for(int i=0; i<height-1; i++){
			for(int k=0; k<width; k++){
				if(gameBoard.isActive(k,i)){
					gameBoard.setBlockStatus(k, i, BlockStatus.EMPTY);
				}
			}
		}
	}
	
	private boolean isAnyActiveCollides(){
		for(int i=0; i<width-1; i++){
			for(int k=0; k<height; k++){
				if(gameBoard.isActive(i,k)){
					if(!gameBoard.isActive(i,k+1) && !gameBoard.isEmpty(i,k+1)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean isRowCompleted(int y){
		for(int x=1; x<width-1; x++){
			if(gameBoard.isEmpty(x,y)){
				return false;
			}
		}
		return true;
	}
	
	private void eraseRow(int y){
		for(int x=1; x<width-1; x++){
			gameBoard.setBlockStatus(x, y, BlockStatus.EMPTY);
		}
	}
	
	private void fallPassiveRow(int y){
		for(int i=width-1; i>0; i--){
			for(int k=y; k>=0; k--){
				if(gameBoard.isPassive(i,k)){
					gameBoard.setBlockStatus(i, k, BlockStatus.EMPTY);
					gameBoard.setBlockStatus(i, k+1, BlockStatus.PASSIVE);
				}
			}
		}		
	}
	
	private void updateLevel(){
		int curLev = cfg.getLevel();
		if(erasedLineNum >14 && (erasedLineNum % 15) == 0 && curLev <5) {
			curLev++;
			cfg.setLevel(curLev);
		}
	}
	
	
	public void play(Movements controller){
		if(createNextShape){
			insertShape();
			createNextShape = false;
		}else{
			updateBoard(controller);
		}
	}
	
	public boolean isGameOver(){
		for(int i=0; i<width-2; i++){
			for(int k=0; k<ghost; k++){
				if(gameBoard.isPassive(i+1,k)){
					if (FX) {
						closeSounds();
					}
					return true;
					}
			}
		}
		return false;
	}
	
	public Block[][] getBoard(){
		return gameBoard.getBoardArray();
	}
	
	public int getScore(){
		return (int) (score/cfg.getSpeed());
	}
	
	public int getErasedLines(){
		return erasedLineNum;
	}	

	public Block[][] getNextShape(){

		Shape object1;
		int width = 5, height = 5, refX = 2, refY = 2, orientation = 0;
		
		switch (nextShape){
		case 0:  
			object1 = new IShape (width, height, refX, refY, orientation);
			break;
		case 1:  
			object1 = new JShape (width, height, refX, refY, orientation);
			break;
		case 2:  
			object1 = new OShape (width, height, refX, refY);
			break;
		case 3:  
			object1 = new SShape (width, height, refX, refY, orientation);
			break;
		case 4: 
			object1 = new TShape (width, height, refX, refY, orientation);
			break;
		case 5:  
			object1 = new ZShape (width, height, refX, refY, orientation);
			break;
		case 6:  
			object1 = new LShape (width, height, refX, refY, orientation);
			break;
		case 7:  
			object1 = new SmallIShape (width, height, refX, refY, orientation);
			break;
		case 8:  
			object1 = new SmallJShape (width, height, refX, refY, orientation);
			break;
		case 9: 
			object1 = new SmallRShape (width, height, refX, refY, orientation);
			break;
		default:
			object1 = new Shape();
			break;
		}
		
		return object1.getShapeArray();
	}
	

	private static void soundInit(){
		 String move = "sounds/move.sound";
		 String rotate = "sounds/rotate.sound";
		 String collide = "sounds/collide.sound";
		 String nErase = "sounds/normalErase.sound";
		 String tErase = "sounds/tetrisErase.sound";
		 
		 
	     moveFX = new Mp3(move);
	     rotateFX = new Mp3(rotate);
	     collideFX = new Mp3(collide);
	     nEraseFX = new Mp3(nErase);
	     tEraseFX = new Mp3(tErase);
	}
	
	private static void closeSounds(){
	     moveFX.close();
	     rotateFX.close();
	     collideFX.close();
	     nEraseFX.close();
	     tEraseFX.close();
	}
}
