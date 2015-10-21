package shapes;


public class Shape {
	protected Block[][] shapeArray;

	protected static int id = 0;
	protected void initBoard(int width, int height) throws IllegalArgumentException {
		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("Width and height cannot be negative");
		}
		
		shapeArray = new Block[width][height];
		
		
		for(int i=0; i<height; i++){
			for(int k=0; k<width; k++){
				shapeArray[k][i] = new Block(BlockStatus.EMPTY);
			}
		}
	}

	public Block[][] getShapeArray() {
		return shapeArray;
	}

	public int getId() {return id;}
	
	

}
