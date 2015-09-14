package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.*;

/**
 * The parent GUI class for searching, inserting or editing referees.
 * @author Team C
 */
public abstract class RefereeGUI extends JFrame {

	/**Array of Strings containing the qualifications a referee can have.*/
	private static final String[] QUALIFICATIONS = {"IJB1", "IJB2", "IJB3", "IJB4", "NJB1", "NJB2", "NJB3", "NJB4"};
	/**Array of Strings containing the possible areas a referee can live.*/
	private static final String[] AREAS = {"North", "Central", "South"};
	/**The three area constants*/
	private static final int AREA_NORTH = 0, AREA_CENTRAL = 1, AREA_SOUTH = 2;

	/**An instance variable to store the controller instance.*/
	protected Controller controller;
	public JTextField firstName, lastName;
	public JSpinner matchesText;
	public JButton cancelButton, updButton, insButton, deleteButton, searchButton;

	public JComboBox<String> qualCombo, homeCombo;
	public JCheckBox[] areasArrayCheck;

	/**
	 * Constructor
	 * @param matchProg
	 */
	protected RefereeGUI(Controller controller) {
		setResizable(false);
		this.controller = controller;
		//Do not terminate the program when closing this JFrame
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(controller);

		//Layout the common components
		layoutCommon();
	}

	/**
	 * Layouts the components that appear in all RefereeGUI children
	 */
	private void layoutCommon() {
		//Using the GridBagLayout
		setLayout(new GridBagLayout());
		//Variable to hold the constraints for the GridBagLayout
		GridBagConstraints constraint = new GridBagConstraints();

		//First name JLabel will be at the first line, first column. All the elements will have a weight of 1.0
		JLabel labelFirst = new JLabel("First Name");
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.weightx = 1.0;
		constraint.weighty = 1.0;
		add(labelFirst,constraint);

		//First name JTextField will be at the first line, second column
		firstName = new JTextField(13);
		constraint.gridx = 1;	//Only gridx has to change, the other constraints are set previously
		add(firstName,constraint);

		//Last name JLabel will be at the second line, first column
		JLabel labelLast = new JLabel("Last Name");		
		constraint.gridx = 0;
		constraint.gridy = 1;
		add(labelLast,constraint);

		//Last name JTextField will be at the second line, second column
		lastName = new JTextField(13);
		constraint.gridx = 1;
		add(lastName,constraint);		
	}

	/**
	 * Layout for Inserting and Updating referees.
	 * @param type
	 */
	protected void layoutInsEdit(){
		//Instantiates the array of JCheckBoxes
		areasArrayCheck = new JCheckBox[3];
		areasArrayCheck[AREA_NORTH] = new JCheckBox("North");
		areasArrayCheck[AREA_CENTRAL] = new JCheckBox("Central");
		areasArrayCheck[AREA_SOUTH] = new JCheckBox("South");

		//The default home locality is north so this sets up the North JCheckbox accordingly
		areasArrayCheck[AREA_NORTH].setEnabled(false);
		areasArrayCheck[AREA_NORTH].setSelected(true);

		//ComboBox containing the three home locality areas
		homeCombo = new JComboBox<String>(AREAS);

		/*
		 * homeCombo needs an ActionListener to detect the changes and setup the JCheckboxes accordingly
		 * A referee must always be willing to go to his home area, so that area's JCheckbox is set to
		 * checked and not enabled as the user should not be able to uncheck it
		 */
		homeCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{

				int choice = homeCombo.getSelectedIndex();
				for(int i=0; i<3 ; i++){
					//Set all check boxes to be enabled and unchecked
					areasArrayCheck[i].setEnabled(true);
					areasArrayCheck[i].setSelected(false);
				}
				//Then set the selected area's checkbox to be not enabled and checked
				areasArrayCheck[choice].setEnabled(false);
				areasArrayCheck[choice].setSelected(true);
			}
		});

		//Variable to hold the constraints for the GridBagLayout
		GridBagConstraints constraints = new GridBagConstraints();

		//Qualification JLabel will be at the third line, first column. All the elements will have a weight of 1.0
		JLabel labelQualif = new JLabel("Qualification");	
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(labelQualif, constraints);

		//ComboBox containing all the possible qualifications the referee can have. Third line, second column
		qualCombo = new JComboBox<String>(QUALIFICATIONS);
		constraints.gridx = 1;
		add(qualCombo, constraints);

		//Allocated matches JLabel will be at the fourth line, first column
		JLabel labelMatches = new JLabel("Matches");
		constraints.gridx = 0;
		constraints.gridy = 3;
		add(labelMatches, constraints);

		//A JSpinner that contains the number of matches the referee has already officiated. Fourth line, second column
		matchesText = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		matchesText.setPreferredSize(new Dimension(55, 20));
		constraints.gridx = 1;
		add(matchesText, constraints);

		//Home JLabel will be at the fifth line, first column
		JLabel labelHome = new JLabel("Home");
		constraints.gridx = 0;
		constraints.gridy = 4;
		add(labelHome, constraints);

		//Combobox to choose the referee's home locality. Fifth line, second column
		constraints.gridx = 1;
		add(homeCombo, constraints);

		//Willing to go areas JLabel will be at the sixth line, first column
		JLabel labelWill = new JLabel("Willing to go areas");
		constraints.gridx = 0;
		constraints.gridy = 5;
		add(labelWill, constraints);

		//The three JCheckboxes for the areas that the referee is willing to go. They are placed in a 
		//temporary panel using Flow Layout and then the panel is placed at the sixth line, second column
		JPanel tempPanel = new JPanel();
		tempPanel.add(areasArrayCheck[AREA_NORTH]);
		tempPanel.add(areasArrayCheck[AREA_CENTRAL]);
		tempPanel.add(areasArrayCheck[AREA_SOUTH]);
		constraints.gridx = 1;
		add(tempPanel, constraints);
	}

	/**
	 * Layouts the cancel button and adds an ActionListener to it.
	 * @param panel
	 */
	protected void layoutCancelBtn(JPanel panel) {
		//Cancel button that appears in every GUI is added to the same panel with the other buttons
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(controller);
		panel.add(cancelButton);

		//Constraints for the GridBagLayout
		GridBagConstraints constraint = new GridBagConstraints();

		//The panel containing the buttons is placed in the 7th row, both columns. 7th was choses because it
		//is the least required for the buttons to be at the last row in every JFrame
		constraint.gridx = 0;
		constraint.gridy = 6;
		constraint.gridwidth = 2;
		constraint.weightx = 1.0;
		constraint.weighty = 1.0;
		add(panel, constraint);
	}
}