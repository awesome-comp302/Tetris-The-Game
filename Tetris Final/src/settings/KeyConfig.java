package settings;

import java.io.Serializable;
import java.util.HashMap;

import javax.swing.JOptionPane;

import game.Movements;

@SuppressWarnings("serial")
public class KeyConfig implements Serializable{

	private HashMap<Movements, Integer> keys = new HashMap<Movements, Integer>();

	public KeyConfig( int l,  int r, int d,  int rot, int p) {
		setLeft(l);
		setRigt(r);
		setDown(d);
		setRotation(rot);
		setPause(p);
	}

	public void setLeft(int l) {
		if (!keys.containsValue(l)) {
			keys.put(Movements.LEFT, l);
		}else{
			dublicateWarning();
		}
	}

	public void setRigt(int r) {
		if (!keys.containsValue(r)) {
			keys.put(Movements.RIGHT, r);
		}else{
			dublicateWarning();
		}
	}


	public void setDown(int d) {
		if (!keys.containsValue(d)) {
			keys.put(Movements.DOWN, d);
		}else{
			dublicateWarning();
		}
	}

	public void setRotation(int rot) {
		if (!keys.containsValue(rot)) {
			keys.put(Movements.ROTATION, rot);
		}else{
			dublicateWarning();
		}
	}

	public void setPause(int p){
		if(!keys.containsValue(p)){
			keys.put(Movements.PAUSE,p);
		}else{
			dublicateWarning();
		}
	}

	public int getLeft() {
		
		return keys.get(Movements.LEFT);
	}

	public int getRight() {
		
		return keys.get(Movements.RIGHT);
	}

	public int getDown() {
		return keys.get(Movements.DOWN);
	}

	public int getRotation() {
		return keys.get(Movements.ROTATION);
	}

	public int getPause() {
		return keys.get(Movements.PAUSE);
	}

	public Movements getMovement(int s){
		Movements movement = null;
		
		for(Movements m : keys.keySet()){
			if(keys.get(m).equals(s)){
				movement = m;
				break;
			}
		}
		
		return movement;
	}
	

	@Override
	public String toString() {
		String result =  "";
		for (Movements m : keys.keySet()) {
			result += "\n" + m +  "->" + keys.get(m);
		}
		return result;
	}
	
	private static void dublicateWarning(){
		String warningMessage = "The key is already in use.";
		JOptionPane.showMessageDialog(null, warningMessage, "Error", JOptionPane.WARNING_MESSAGE);
		
	}
	
	
}
