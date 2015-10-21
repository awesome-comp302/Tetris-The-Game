package settings;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("serial")
public class OptionSet implements Serializable {

	private String playerName;
	private int width;
	private int height;
	private int level;
	private boolean sound;
	private boolean fxSound;
	private ShapeOptions shapeDecider;
	private HashMap<Integer, Double> speed = new HashMap<Integer, Double>();

	public OptionSet(String playerName, int width, int height, int level,
			boolean sound, boolean fxSound, ShapeOptions shapeDecider) {
		
		this.sound = sound;
		this.playerName = playerName;
		this.width = width;
		this.height = height;
		this.level = level;
		this.fxSound = sound;
		this.shapeDecider = shapeDecider;
		speed.put(1, 1.0);
		speed.put(2, 0.8);
		speed.put(3, 0.6);
		speed.put(4, 0.4);
		speed.put(5, 0.2);
	}

	public OptionSet() {
		this("Player", 10, 15, 1,true , true, ShapeOptions.TETRAMINO);
	}

	public void setLevel(int level) {
		if (level >= 1 && level <= 5) {
			this.level = level;
		} else {
			System.err.println("Invalid level..");
		}
	}

	public int getLevel() {
		return this.level;
	}

	public double getSpeed() {
		return speed.get(this.level);
	}

	public void setHeight(int h) {
		if (h > 0) {
			this.height = h;
		} else {
			System.err.println("Height can't be negatif");
		}
	}

	public int getHeight() {
		return this.height;
	}

	public void setWidth(int w) {
		if (w > 0) {
			this.width = w;
		} else {
			System.err.println("Width can't be negatif");
		}
	}

	public int getWidth() {
		return this.width;
	}
	
	public void setSound(boolean b){
		sound = b;
	}
	
	public boolean getSound(){
		return sound;
	}
	
	public void setFXSound(boolean b){
		fxSound = b;
	}
	
	public boolean getFXSound(){
		return fxSound;
	}

	public void setShapeType(ShapeOptions s) {
		shapeDecider = s;
	}

	public ShapeOptions getShapeType() {
		return this.shapeDecider;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public String toString() {
		String result = "OptionSet [playerName=" + playerName + ", width="
				+ width + ", height=" + height + ", level=" + level
				+ ", shapeDecider=" + shapeDecider;
		for (int a : speed.keySet()) {
			result += String.format("\n%d %f", a, speed.get(a));
		}
		return result;
	}

}
