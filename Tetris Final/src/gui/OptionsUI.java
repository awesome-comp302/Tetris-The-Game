package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

import settings.KeyConfig;
import settings.OptionSet;
import settings.ShapeOptions;
import storage.TetrisStorage;

@SuppressWarnings("serial")
public class OptionsUI extends PopUp {
	private OptionSet selected;
	private KeyConfig kc;

	private JLabel nameLabel;
	private JTextField nameField;

	private JLabel gameModeLabel;
	private JComboBox<String> gameModeSelections;

	private JLabel heightLabel;
	private JFormattedTextField heightField;

	private JLabel widthLabel;
	private JFormattedTextField widthField;

	private JLabel levelLabel;
	private JComboBox<Integer> levelBox;

	private JLabel rightLabel;
	private JTextField rightSelection;

	private JLabel leftLabel;
	private JTextField leftSelection;

	private JLabel rotateLabel;
	private JTextField rotateSelection;

	private JLabel softDrop;
	private JTextField softDropSelections;

	private JLabel pauseLabel;
	private JTextField pauseSelection;
	
	private JCheckBox soundSelection;
	private JCheckBox fxSoundSelection;

	private JButton okButton;
	private JButton cancelButton;

	private final String gameModes[] = { "Tetramino", "Trimino", "Mixed" };
	private final Integer[] levels = { 1, 2, 3, 4, 5 };
	private final int ROW_NUMBER = 12;

	public OptionsUI(StartWindow mother) {
		super("Options", mother);
		try {
			selected = TetrisStorage.loadOptionSet();
		} catch (RuntimeException e) {
			selected = new OptionSet();
		}
		try {
			kc = TetrisStorage.loadKeyConfig();
		} catch (RuntimeException e) {
			kc = new KeyConfig(37, 39, 40, 38, 80);
		}

		setLayout(new GridLayout(ROW_NUMBER, 2));

		nameLabel = new JLabel("Player Name");
		add(nameLabel);
		nameField = new JTextField();
		nameField.setText(""+ selected.getPlayerName());
		add(nameField);

		gameModeLabel = new JLabel("Game Mode");
		add(gameModeLabel);
		gameModeSelections = new JComboBox<String>(gameModes);
		gameModeSelections.setSelectedIndex(getSelectionIndexFromSelected());
		add(gameModeSelections);
		

		widthLabel = new JLabel("Board Width");
		add(widthLabel);
		widthField = new JFormattedTextField(createFormatter("##"));
		widthField.setText("" + selected.getWidth());
		add(widthField);
		widthField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {			
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(isValid(e.toString())){
					selected.setWidth(keyCode);
					widthField.setText(e.toString());
				}
				
			}
		});
		


		heightLabel = new JLabel("Board Height");
		add(heightLabel);
		heightField = new JFormattedTextField(createFormatter("##"));
		heightField.setText("" + selected.getHeight());
		add(heightField);
		heightField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(isValid(e.toString())){
					int keyCode = e.getKeyCode();
					selected.setHeight(keyCode);
					heightField.setText(e.toString());
				}
				
			}
		});

		levelLabel = new JLabel("Game Level");
		levelLabel.setToolTipText("Level increases speed of the game");
		add(levelLabel);
		levelBox = new JComboBox<Integer>(levels);
		levelBox.setSelectedItem(selected.getLevel());
		add(levelBox);
		levelBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selected.setLevel((Integer)levelBox.getSelectedItem());
				levelBox.setSelectedItem(selected.getLevel());
				
			}
		});

		rightLabel = new JLabel("Right");
		add(rightLabel);
		rightSelection = new JTextField("" + KeyEvent.getKeyText(kc.getRight()));
		add(rightSelection);

		rightSelection.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				kc.setRigt(keyCode);
				rightSelection.setText(KeyEvent.getKeyText(kc.getRight()));
			}
		});

		leftLabel = new JLabel("Left");
		add(leftLabel);
		leftSelection = new JTextField("" + KeyEvent.getKeyText(kc.getLeft()));
		add(leftSelection);
		leftSelection.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				kc.setLeft(keyCode);
				leftSelection.setText(KeyEvent.getKeyText(kc.getLeft()));
				}
		});

		rotateLabel = new JLabel("Rotate");
		add(rotateLabel);
		rotateSelection = new JTextField(""
				+ KeyEvent.getKeyText(kc.getRotation()));
		add(rotateSelection);
		rotateSelection.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				kc.setRotation(keyCode);
				rotateSelection.setText(KeyEvent.getKeyText(kc.getRotation()));
			}
		});

		softDrop = new JLabel("Soft Drop");
		add(softDrop);
		softDropSelections = new JTextField(""
				+ KeyEvent.getKeyText(kc.getDown()));
		add(softDropSelections);
		softDropSelections.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				kc.setDown(keyCode);
				softDropSelections.setText(KeyEvent.getKeyText(kc.getDown()));
			}
		});

		pauseLabel = new JLabel(
				"Pause");
		add(pauseLabel);
		pauseSelection = new JTextField("" + KeyEvent.getKeyText(kc.getPause()));
		add(pauseSelection);
		pauseSelection.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				kc.setPause(keyCode);
				pauseSelection.setText(KeyEvent.getKeyText(kc.getPause()));
			}
		});
		
		
		soundSelection = new JCheckBox("Music");
		add(soundSelection);
		soundSelection.setSelected(selected.getSound());
		soundSelection.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent ce) {
				selected.setSound(soundSelection.isSelected());
				
			}
		});
		
		
		fxSoundSelection = new JCheckBox("SFX");
		add(fxSoundSelection);
		fxSoundSelection.setSelected(selected.getFXSound());
		fxSoundSelection.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent ce) {
				selected.setFXSound(fxSoundSelection.isSelected());
				
			}
		});

		
		ButtonListener bl = new ButtonListener();

		okButton = new JButton("Ok");
		add(okButton);
		okButton.addActionListener(bl);
		cancelButton = new JButton("Cancel");
		add(cancelButton);
		cancelButton.addActionListener(bl);

	}

	private int getSelectionIndexFromSelected() {
		// TODO Auto-generated method stub
		switch (selected.getShapeType()) {
		case TETRAMINO:
			return 0;
		case TRIMINO:
			return 1;
		default:
			return 2;
		}
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == okButton) {
				String newName = nameField.getText();
				if (!newName.equals("")) {
					if (newName.contains("@@@")) {
						newName = newName.replace("@@@", "");
					}
					
					selected.setPlayerName(newName);
				} else {
					warning("Please enter a Player Name");
				}

				selected.setShapeType(getShapeType((String)gameModeSelections.getSelectedItem()));
				
				
				if (isValid(widthField.getText())) {
					selected.setWidth(Integer.parseInt(widthField.getText()));
				} else {
					warning("Please enter board width.");
				}
				
				if (isValid(heightField.getText())) {
					selected.setWidth(Integer.parseInt(widthField.getText()));
					selected.setHeight(Integer.parseInt(heightField.getText()));
				} else {
					warning("Please enter board height.");
				}
				
				
				if (isValid(heightField.getText()) && isValid(heightField.getText()) && !newName.equals("")) {
					TetrisStorage.saveKeyConfig(kc);
					TetrisStorage.saveOptionSet(selected);
					goBack();
				}

			} else if (e.getSource() == cancelButton) {
				String[] options = {"Yes", "No"};
				int n = JOptionPane.showOptionDialog(null,
					    "The change(s) will not be saved!\n"
					    + "Do you want to continue?",
					    "Warning",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.CANCEL_OPTION,
					    null,
					    options ,
					    options[1]); 
				if (n == 0) {
					goBack();
				} 
			}

		}

		private ShapeOptions getShapeType(String selectedItem) {
			// TODO Auto-generated method stub
			switch (selectedItem) {
			case "Tetramino":
				return ShapeOptions.TETRAMINO;
			case "Trimino":
				return ShapeOptions.TRIMINO;
			default:
				return ShapeOptions.BOTH;
			
			}
		}

	}

	private boolean isValid(String str) {
		return (str.matches("[0-9][0-9]*"));
	}
	
	private MaskFormatter createFormatter(String s){
		MaskFormatter formatter = null;
		try{
			formatter =  new MaskFormatter(s);
		}catch(java.text.ParseException exc){
			String warningMessage = "You need to enter an Integer.";
			warning(warningMessage);
		}
		return formatter;
		
	}
	
	private static void warning(String warningMessage){
		JOptionPane.showMessageDialog(null, warningMessage, "Error", JOptionPane.WARNING_MESSAGE);
		
	}
	

}
