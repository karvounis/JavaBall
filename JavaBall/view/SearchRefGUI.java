package view;

import javax.swing.*;
import controller.*;

/**
 * Sets up a RefereeGUI to search for a referee.
 * @author Team C
 */
public final class SearchRefGUI extends RefereeGUI {

	public SearchRefGUI(Controller controller){
		super(controller);
		setTitle("Search Referee");
		setSize(350, 170);
		//Display this dialog in the center of the screen
		setLocationRelativeTo(null);
		setVisible(true);	

		//Layouts the search specific buttons
		layoutSearchButtons();
	}

	/**
	 * Lays out the search specific buttons
	 */
	private void layoutSearchButtons(){
		//Create a JPanel to put all the buttons
		JPanel btnPanel = new JPanel();

		//Create the search button and add it to the JPanel
		searchButton = new JButton("Search");
		searchButton.addActionListener(controller);
		btnPanel.add(searchButton);

		//Call superclass's method to insert the cancel button to the JPanel and add the JPanel to the JFrame
		layoutCancelBtn(btnPanel);
	}
}