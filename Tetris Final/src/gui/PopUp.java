package gui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PopUp extends JFrame {
	private JFrame mother;
	
	public PopUp(String title, JFrame mother) {
		super(title);
		this.mother = mother;
		this.mother.setVisible(false);
		dispose();
		setUndecorated( true );
		getRootPane( ).setWindowDecorationStyle(3);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	protected void goBack() {
		mother.setVisible(true);
		dispose();
	}	
}
