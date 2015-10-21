package settings;

public class Pair {
	
	private String name;
	private int score;

	public Pair(String name, int point){
		this.name = name;
		this.score = point;
	}

	public String toString(){
		return name + " "  + score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
