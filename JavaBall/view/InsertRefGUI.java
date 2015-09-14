package view;

import javax.swing.*;
import controller.*;

/**
 * Sets up a RefereeGUI to insert a new referee.
 * @author Team C
 */
public final class InsertRefGUI extends RefereeGUI {

	public InsertRefGUI(Controller controller) {
		super(controller);
		//Layout the insert specific elements and buttons.
		layoutInsEdit();
		layoutInsButtons();

		setTitle("Insert Referee");
		setSize(500, 350);
		//Display this dialog in the center of the screen.
		setLocationRelativeTo(null);
		setVisible(true);
	}		

	/**
	 * Lays out the insert specific buttons
	 */
	private void layoutInsButtons() {
		//Create a JPanel to put all the buttons
		JPanel btnPanel = new JPanel();

		//Create the insert button and add it to the JPanel
		insButton = new JButton("Insert");
		insButton.addActionListener(controller);
		btnPanel.add(insButton);

		//Call superclass's method to insert the cancel button to the JPanel and add the JPanel to the JFrame
		layoutCancelBtn(btnPanel);
	}
}