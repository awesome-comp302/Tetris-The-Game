package shapes;

public class OShape extends Shape{

	public OShape(int width, int height, int refx, int refy) {
		id++;
		initBoard(width, height);
		try {
			shapeArray[refx][refy].setStatus(BlockStatus.ACTIVE);
			shapeArray[refx+1][refy].setStatus(BlockStatus.ACTIVE);
			shapeArray[refx][refy+1].setStatus(BlockStatus.ACTIVE);
			shapeArray[refx+1][refy+1].setStatus(BlockStatus.ACTIVE);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			throw new IllegalArgumentException("Reference coordinates are invalid");
		}
	}

}
