package storage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import settings.*;

public class TetrisStorage {

	public static void saveOptionSet(OptionSet options) throws RuntimeException{
		try {
			ObjectOutputStream output = new ObjectOutputStream(
					new FileOutputStream("data/options.cfg"));
			output.writeObject(options);
			output.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found!");
		} catch (IOException e) {
			throw new RuntimeException("An error occured in file reading");
		}

	}

	public static OptionSet loadOptionSet() throws RuntimeException{
		OptionSet currentOptions = null;
		try {
			ObjectInputStream input = new ObjectInputStream(
					new FileInputStream("data/options.cfg"));
			currentOptions = (OptionSet) input.readObject();
			input.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException("An error occured in file reading");
		} catch (IOException e) {
			
			throw new RuntimeException("An error occured in file reading");
			
		} catch (ClassNotFoundException e) {
			System.err.println("This");
			throw new RuntimeException("An error occured in file reading");
		}
		return currentOptions;
	}

	public static void saveKeyConfig(KeyConfig kc) throws RuntimeException{
		try {
			ObjectOutputStream output = new ObjectOutputStream(
					new FileOutputStream("data/key_config.cfg"));
			output.writeObject(kc);
			output.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("An error occured in file reading");
		} catch (IOException e) {
			throw new RuntimeException("An error occured in file reading");
		}
	}

	public static KeyConfig loadKeyConfig() throws RuntimeException{
		KeyConfig keys = null;
		try {
			ObjectInputStream input = new ObjectInputStream(
					new FileInputStream("data/key_config.cfg"));
			keys = (KeyConfig) input.readObject();
			input.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException("An error occured in file reading");
		} catch (IOException e) {
			throw new RuntimeException("An error occured in file reading");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("An error occured in file reading");
		}
		return keys;
	}

	public static void updateLeaderBoard(String username, int score) {
		Pair[] currentLeaders = readLeaderBoard();
		
		if (score >= currentLeaders[4].getScore()) {
			Pair myPair = new Pair(username, score);
			currentLeaders[4] = myPair;
			int i = 3;
			while (i>=0 && score >= currentLeaders[i].getScore()) {
				currentLeaders[i+1] = currentLeaders[i];
				currentLeaders[i--] = myPair;
			}
			writeLeaderBoard(currentLeaders);	
		}
	}	
	
	public static boolean leaderBoardUpdateRequired(int score) {
		Pair[] currentLeaders = readLeaderBoard();
		return score > currentLeaders[4].getScore();
	}


	private static Pair[] readLeaderBoard() throws RuntimeException{
		Pair[] pairs = new Pair[5];
		try {
			BufferedReader rd = new BufferedReader(
					new FileReader("data/leaders.lb"));
			int i = 0;
			while (true) {
				String pairLine = rd.readLine();
				if (pairLine == null) {
					break;
				}
				String[] pairData = pairLine.split("\\@@@");
				pairs[i++] = new Pair(pairData[0], 
						Integer.parseInt(pairData[1]));
			}
			rd.close();
		} catch (FileNotFoundException e) {
			pairs = initLeaderBoardFile();
			
		} catch (IOException e) {
			throw new RuntimeException("An error occured in file reading");
		}
		return pairs;
	}

	private static void writeLeaderBoard(Pair[] currentLeaders) throws RuntimeException{
		try {
			PrintWriter pw = new PrintWriter(
					new FileWriter("data/leaders.lb"));
			for (int i = 0; i < currentLeaders.length; i++) {
				pw.println(currentLeaders[i].getName() + "@@@" + currentLeaders[i].getScore());
			}
			pw.close();
		} catch (IOException e) {
			throw new RuntimeException("An error occured in file reading");
			
		}
	}

	public static String getLeaderBoard() {
		String lb = "";
		Pair[] pairs = readLeaderBoard();
		for (int i = 0; i < pairs.length; i++) {
			Pair pair = pairs[i];
			String txt = String.format("%s %d",pair.getName(), pair.getScore());
			if (txt.equals("unknown 0")) {
				lb += String.format("%d.    %s\n",i+1,  "Empty" );
			} else {
				lb += String.format("%d.    %s\n",i+1,  txt );
			}
		}
		return lb;
	}
	
	

	private static Pair[] initLeaderBoardFile() {
		Pair[] p = new Pair[5];
		for (int i = 0; i < p.length; i++) {
			p[i] = new Pair("unknown", 0);
		}
		writeLeaderBoard(p);
		return p;
	}
	
	public static void cleanLeaderBoard() {
		initLeaderBoardFile();
	}
}
