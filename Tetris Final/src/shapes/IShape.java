package shapes;

public class IShape extends Shape {

	public IShape(int width, int height, int refx, int refy, int orientation)
			throws IllegalArgumentException {
		id++;
		initBoard(width, height);
		orientation %= 2;
		try {
			switch (orientation) {
			case 0:
				shapeArray[refx][refy].setStatus(BlockStatus.ACTIVE);
				shapeArray[refx - 1][refy].setStatus(BlockStatus.ACTIVE);
				shapeArray[refx + 1][refy].setStatus(BlockStatus.ACTIVE);
				shapeArray[refx + 2][refy].setStatus(BlockStatus.ACTIVE);
				break;
			case 1:
				shapeArray[refx][refy].setStatus(BlockStatus.ACTIVE);
				shapeArray[refx][refy - 1].setStatus(BlockStatus.ACTIVE);
				shapeArray[refx][refy - 2].setStatus(BlockStatus.ACTIVE);
				shapeArray[refx][refy + 1].setStatus(BlockStatus.ACTIVE);
				break;
			default:
				break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException(
					"Reference coordinates are invalid");
		}
	}
}
