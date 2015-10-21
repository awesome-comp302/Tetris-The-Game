package shapes;

import java.awt.Color;

public class Block {
	
	private BlockStatus status;
	public Color color; 
	
	public Block(BlockStatus status) {
		setStatus(status);
		
	}
	
	public void setStatus(BlockStatus status){
		this.status = status;
	}
	
	public BlockStatus getStatus(){
		return status;
	}

}
