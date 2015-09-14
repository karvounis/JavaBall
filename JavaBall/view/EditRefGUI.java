package view;

import javax.swing.*;
import controller.*;
import model.Referee;

/**
 * Sets up a RefereeGUI to update or delete a referee.
 * @author Team C
 */
public final class EditRefGUI extends RefereeGUI {

	public EditRefGUI(Controller controller, Referee ref) {
		super(controller);
		//Layouts the edit specific elements and buttons.
		layoutInsEdit();
		layoutEditButtons();

		setTitle("Edit Referee - " + ref.getFirstName() + " " + ref.getLastName());
		setSize(500, 350);
		//Display this dialog in the center of the screen
		setLocationRelativeTo(null);
		setVisible(true);

		//Populate the JTextFields with the referee's attributes
		//First name and last name JTextFields are non editable. The user should not be able to edit those
		firstName.setText(ref.getFirstName());
		firstName.setEditable(false);

		lastName.setText(ref.getLastName());
		lastName.setEditable(false);

		//Populate the rest of the elements
		matchesText.setValue(ref.getMatchesAllocated());
		matchesText.setEnabled(false);

		qualCombo.setSelectedItem(ref.getQualification());
		homeCombo.setSelectedItem(ref.getStringLocality());

		boolean[] willingness = ref.getWillingToGoAreas();
		for(int i = 0; i < willingness.length; i++) {
			if(willingness[i]) {
				areasArrayCheck[i].setSelected(true);
			}
		}
	}

	/**
	 * Lays out the edit specific buttons
	 */
	private void layoutEditButtons() {
		//Create a JPanel to put all the buttons
		JPanel btnPanel = new JPanel();

		//Create the update button and add it to the JPanel
		updButton = new JButton("Update");
		updButton.addActionListener(controller);
		btnPanel.add(updButton);

		//Create the delete button and add it to the JPanel
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(controller);
		btnPanel.add(deleteButton);

		//Call superclass's method to insert the cancel button to the JPanel and add the JPanel to the JFrame
		layoutCancelBtn(btnPanel);
	}
}