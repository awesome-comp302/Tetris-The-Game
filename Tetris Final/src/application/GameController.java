package application;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.Game;
import game.Movements;
import settings.KeyConfig;
import settings.OptionSet;
import shapes.Block;
import storage.TetrisStorage;
import gui.ApplicationWindow;
import gui.Leaderboard;
import gui.PauseScreen;
import sound.Mp3;

public class GameController implements Runnable {

	private int blockSize;
	private Block[][] board;
	private Game tetris;
	private double gameSpeed;
	private Movements controller = Movements.DOWN;
	private boolean introSound = true;
	private boolean mainSound = false;
	private OptionSet cfg;
	private KeyConfig kfg;
	public boolean end = false;
	private boolean paused;
	private int caseInt;
	private static boolean music;

	private Mp3 introMusic;
	private Mp3 mainMusic;
	private Mp3 pauseSound;
	private static Mp3 gameOver;
	private static Mp3 winSound;

	public void run() {

		try {
			cfg = TetrisStorage.loadOptionSet();
		} catch (RuntimeException e) {
			cfg = new OptionSet();
			TetrisStorage.saveOptionSet(cfg);
		}

		try {
			kfg = TetrisStorage.loadKeyConfig();
		} catch (RuntimeException e) {
			kfg = new KeyConfig(37, 39, 40, 38, 80);
			TetrisStorage.saveKeyConfig(kfg);
		}

		music = cfg.getSound();
		if (music) {
			soundInit();
			introMusic.play();
		}

		if (cfg.getWidth() > 30 || cfg.getHeight() > 30) {
			blockSize = (int) ((int) 800 / Math.max(cfg.getWidth(),
					cfg.getHeight()));
		} else if (cfg.getWidth() < 15 || cfg.getHeight() < 15) {
			blockSize = (int) ((int) 300 / Math.max(cfg.getWidth(),
					cfg.getHeight()));
		} else {
			blockSize = (int) ((int) 500 / Math.max(cfg.getWidth(),
					cfg.getHeight()));
		}

		tetris = new Game(cfg);
		ApplicationWindow frame = new ApplicationWindow(cfg.getWidth(),
				cfg.getHeight(), blockSize, tetris.getBoard(),
				tetris.getNextShape());
		draw(frame);
		frame.repaint();
		gameSpeed = cfg.getSpeed();
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.dispose();
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(3);
		frame.setVisible(true);

		long lastUpdate = System.currentTimeMillis();
		final int down = kfg.getDown(), left = kfg.getLeft(), rot = kfg
				.getRotation(), right = kfg.getRight(), pause = kfg.getPause();

		PauseScreen ps = new PauseScreen(this);
		ps.setVisible(false);

		while (true) {
			if (end){
				ps.setVisible(false);
				break;
				}
			if (music) {
				mainSoundLoop();
			}

			for (int x = 0; x < 150; x++) {
				if (tetris.isGameOver()) {
					end = true;
					break;
				}

				gameSpeed = cfg.getSpeed();
				if (frame.button == down) {
					caseInt = 1;
				} else if (frame.button == right) {
					caseInt = 2;
				} else if (frame.button == left) {
					caseInt = 3;
				} else if (frame.button == rot) {
					caseInt = 4;
				} else if (frame.button == pause) {
					paused = true;
					if (pauseSound != null) {
						pauseSound.play();
					}

					frame.button = 0;
				} else {
					caseInt = 0;
				}

				switch (caseInt) {
				case 1:
					controller = Movements.DOWN;
					if (frame.buttonPressed)
						gameSpeed /= 10;
					break;
				case 2:
					controller = Movements.RIGHT;
					break;
				case 3:
					controller = Movements.LEFT;
					break;
				case 4:
					controller = Movements.ROTATION;
					break;
				default:
					controller = Movements.DOWN;
					break;
				}

				if (ps.cont) {
					frame.setFocusable(true);
					frame.setEnabled(true);
					frame.setAlwaysOnTop(true);
					frame.setAlwaysOnTop(false);
					paused = false;
					ps.cont = false;
				}

				if (!paused) {
					if (controller != Movements.DOWN && frame.buttonPressed) {
						tetris.play(controller);
						board = tetris.getBoard();
						draw(frame);
						frame.repaint();
						frame.buttonPressed = false;
					}

					if (System.currentTimeMillis() - lastUpdate > 1000 * gameSpeed) {
						lastUpdate = System.currentTimeMillis();
						tetris.play(Movements.DOWN);

						board = tetris.getBoard();
						draw(frame);
						frame.repaint();
					}
				} else {
					if(frame.isEnabled() == true) frame.setEnabled(false);
					if(frame.isFocusable() == true)	frame.setFocusable(false);
					if (ps.isVisible() == false) ps.setVisible(true);
				}
			}
		}

		if (music) {
			closeSounds();
		}
		try {
			if (TetrisStorage.leaderBoardUpdateRequired(tetris.getScore())) {
				if(music) winSound.play();
				String name = JOptionPane.showInputDialog(
						"High Score!\nEnter a player name for Leaderboard!",
						cfg.getPlayerName());
				if (name != null) {
					if ((name.contains("@@@"))) {
						name = name.replace("@@@", "");
					}

					if (!name.isEmpty()) {
						cfg.setPlayerName(name);
					}
					TetrisStorage.updateLeaderBoard(cfg.getPlayerName(),
							tetris.getScore());
					try {
						TetrisStorage.saveOptionSet(cfg);
					} catch (Exception e) {
						// Do nothing
					}
				}
				Leaderboard lb = new Leaderboard(frame);
				lb.setVisible(true);
			}else{
				if(music) gameOver.play();
				Leaderboard lb = new Leaderboard(frame);
				lb.setVisible(true);
			}
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(null,
					"Leaderboard could not be loaded.\n");
			System.exit(1);
		}

	}

	private void draw(ApplicationWindow frame) {
		if (board == null)
			return;
		cfg.getSpeed();
		frame.updateBoard(board, tetris.getNextShape());
		frame.updateScore(tetris.getScore());
		frame.updateLevel(cfg.getLevel());
		frame.updateLines(tetris.getErasedLines());
	}

	private void soundInit() {
		String intro = "sounds/intro.sound";
		String main = "sounds/main.sound";
		String pause = "sounds/pause.sound";
		String win   = "sounds/win.sound";
		String gOver   = "sounds/gOver.sound";

		introMusic = new Mp3(intro);
		mainMusic = new Mp3(main);
		pauseSound = new Mp3(pause);
		winSound = new Mp3(win);
		gameOver = new Mp3(gOver);
	}

	private void mainSoundLoop() {
		if (introMusic.complete() && introSound) {
			mainSound = true;
			introSound = false;
		}

		if (mainSound) {
			mainMusic.play();
			mainSound = false;
		}

		try {
			if (mainMusic.complete())
				mainSound = true;
		} catch (Exception e) {
			return;
		}

	}

	private void closeSounds() {
		introMusic.close();
		mainMusic.close();
		pauseSound.close();
	}
	
	public static void closeGame(JFrame frame){
		frame.dispose();
		if(music){
			winSound.close();
			gameOver.close();
		}
		
	}

}
