package controller;

import model.*;
import view.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

/**
 * The Controller of our MVC. Implements the ActionListener Interface and handles all the actions from the GUIs.
 * @author Team C
 *
 */
public final class Controller implements ActionListener, WindowListener {

	private GUI mainGUI;
	private RefereeGUI refGUI;
	private MatchProgram matchProgram;
	private Referee tempRef;

	public Controller() {
		mainGUI = new GUI(this);

		//Initialises matchProgram object.
		matchProgram = new MatchProgram();
		//Initialises the referees from file.
		initialiseRef();

		//Display the initial referees to the main textArea
		String displayStr = matchProgram.displayRefereesList();
		mainGUI.display.setText(displayStr);
	}

	/**
	 * Reads the file referresIn.txt and initialises the referee ArrayList.
	 */
	private void initialiseRef()
	{
		FileReader reader = null;

		try{
			try{
				reader = new FileReader("RefereesIn.txt");
				// Using scanner to read the file line by line
				Scanner sc = new Scanner(reader);

				while(sc.hasNextLine())
				{
					//Reads a line.
					String line = sc.nextLine();

					//Instantiates a Referee object.
					Referee tempRef = new Referee(line);

					//Insert the object to the matchProgram arrayList.
					matchProgram.insertInitialRef(tempRef);
				}

				sc.close();
			} finally {
				//If the file exists close it
				if(reader != null)
				{
					reader.close();
				}
			}
		}
		catch(IOException e)
		{
			//Error, handle the exception for the non existing file
			JOptionPane.showMessageDialog(null, "The input file could not be opened", "Could not open input file", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Process that allocates 2 referees to a match.
	 */
	private void allocateProcess(){

		int weekNumber = 0, matchArea = 0; 		

		//Gets the week number from the user. We don't need to validate that the value is between 1 and 52 because it comes from a JSpinner which only accepts integers in that range.
		weekNumber = (int) mainGUI.weekIn.getValue();

		//Gets the area that the match is going to take place.
		matchArea = mainGUI.areaList.getSelectedIndex();

		//Checks if there is a match already happening that week.
		if(matchProgram.getMatch(weekNumber) != null){
			JOptionPane.showMessageDialog(null, "There is another match at that week!", "Match conflict", JOptionPane.ERROR_MESSAGE);
			return;
		}
		//Gets the level of the match.
		String matchQual = (String) mainGUI.levelOfMatchList.getSelectedItem();

		matchProgram.allocateRefsToMatch(weekNumber, matchArea, matchQual);
	}

	/**
	 * Saves the output and then exits the program.
	 */
	private void saveExit() {		
		//Saves the output.
		FileOutput fout = new FileOutput(matchProgram.getMatches());
		fout.RefOut(matchProgram.getAllRefs());
		fout.MatchOut();

		//Exits the system normally.
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		//Main GUI events:
		//Referee -> New  MenuItem pressed.
		if (ae.getSource() == mainGUI.menuItemNew) {
			//Checks if there are less than 12 referees in the list already. If there are less than 12, program allows the adding of the referee. Otherwise, it forbids it.
			if(matchProgram.getAllRefs().size() < 12 ) {
				//Instantiates the InsertRefGUI.
				refGUI = new InsertRefGUI(this);
			} else {
				JOptionPane.showMessageDialog(null, "There are 12 referees already in the list! Delete someone before adding another!", "Too many referees", JOptionPane.ERROR_MESSAGE);
			}
		}

		//Allocate button pressed.
		else if(ae.getSource() == mainGUI.allocateButton) {
			allocateProcess();
			//Sets the spinner to minimum value.
			mainGUI.weekIn.setValue(1);
			////Updates the mainGUI display.
			mainGUI.display.setText(matchProgram.displayRefereesList());
		}

		//Referee -> Search MenuItem pressed.
		else if (ae.getSource() == mainGUI.menuItemSearch) {
			if (matchProgram.getAllRefs().size() == 0) {
				JOptionPane.showMessageDialog(null, "The referee list is empty!", "No referees", JOptionPane.ERROR_MESSAGE);
			} else {
				//Instantiates a SearchRefGUI.
				refGUI = new SearchRefGUI(this);				
			}
		}

		//Help -> About MenuItem pressed.
		else if (ae.getSource() == mainGUI.menuItemAbout) {

			JOptionPane.showMessageDialog(mainGUI, "Team C Members: \n\nGeorgios Gkavresis\nEvangelos Karvounis\nKonstantinos Kousinas\nAthanasios Santas", "Team Information", JOptionPane.INFORMATION_MESSAGE);
		}

		//Bar Chart button pressed.
		else if (ae.getSource() == mainGUI.barChartButton) {

			JFrame f = new JFrame();
			f.setSize(1000, 800);
			f.setTitle("Bar Chart");
			f.setLocationRelativeTo(null);

			//Gets the ArrayList of Referees.
			ArrayList<Referee> refs = matchProgram.getAllRefs();

			//Create two arrays to store matches and id of the referees respectively.
			int[] matchAlloc = new int[refs.size()];
			String[] id = new String[refs.size()];			

			//Creates a RefereeIDComparator object that is used to sort Referee objects based on their ID.
			RefereeIDComparator comparator = new RefereeIDComparator();
			Collections.sort(refs, comparator);
			//Populates the two arrays.
			for(int i = 0; i < matchProgram.getAllRefs().size(); i++)
			{				
				matchAlloc[i] = refs.get(i).getMatchesAllocated();
				id[i] = refs.get(i).getRefID();
			}

			f.add(new BarChart(matchAlloc, id));
			f.setVisible(true);
		}

		//Exit button pressed.
		else if (ae.getSource() == mainGUI.exitButton) {			
			saveExit();
		}

		//---------------------------------------------------------------------------------------------------------------------------------------------
		//RefGUI buttons

		//Search Button pressed.
		else if(ae.getSource() == refGUI.searchButton){
			//Gets the full name of the referee, trims and stores it.
			String fname = refGUI.firstName.getText().trim();
			String surname = refGUI.lastName.getText().trim();

			//Checks if the inputs are not empty or null.
			if(!fname.isEmpty()  && !surname.isEmpty()) {
				//Selects the Referee based on his first and last name.
				Referee search = matchProgram.selectRef(fname, surname);

				//If we did not find a referee with these inputs, print an error message.
				if(search == null) {
					JOptionPane.showMessageDialog(null, "No referee with these credentials found!", "Search failed", JOptionPane.ERROR_MESSAGE);

				} else {
					tempRef = search;
					//Closes this GUI and creates a EditRefGUI to deal with updating the Referee's information or deleting him.
					refGUI.dispose();
					refGUI = new EditRefGUI(this, tempRef);
				}
			} else {
				JOptionPane.showMessageDialog(null, "You provided incorrect inputs!", "Incorrect inputs", JOptionPane.ERROR_MESSAGE);
			}		
		}

		//Delete Button pressed.
		else if (ae.getSource() == refGUI.deleteButton) {
			//Checks the number of actual matches that the Referee has been allocated. If is is 0 then this referee will be allowed to be deleted.
			if(tempRef.getActualMatches() == 0){
				//Gets user's choice regarding a specific Referee's deletion.
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + tempRef.getFirstName() + " " + tempRef.getLastName() + "?" , "Delete confirmation", JOptionPane.OK_CANCEL_OPTION);

				if(choice ==  JOptionPane.OK_OPTION){
					matchProgram.deleteRef(tempRef);
					JOptionPane.showMessageDialog(null, tempRef.getFirstName() + " " + tempRef.getLastName() + " successfully deleted!", "Successful insertion", JOptionPane.INFORMATION_MESSAGE);					
				}
			}else{
				//Prints an error message and does not allow the deletion of the referee.
				JOptionPane.showMessageDialog(null, tempRef.getFirstName() + " " + tempRef.getLastName() + " has already been allocated to a match!\nReferee can not be deleted.", "Referee already allocated", JOptionPane.ERROR_MESSAGE);
			}
			//Disposes the GUI
			refGUI.dispose();
			//Updates the display.
			mainGUI.display.setText(matchProgram.displayRefereesList());
		}

		//Implements insert and update referee.
		else if(ae.getSource() == refGUI.updButton || ae.getSource() == refGUI.insButton) {

			//Gets the user's inputs from the different components of the GUI and stores them accordingly.
			String first = refGUI.firstName.getText().trim();
			String surname = refGUI.lastName.getText().trim();
			String qualif = (String) refGUI.qualCombo.getSelectedItem();

			int matchesAll = (int) refGUI.matchesText.getValue();
			int home = refGUI.homeCombo.getSelectedIndex();
			boolean[] willing = {refGUI.areasArrayCheck[0].isSelected(), refGUI.areasArrayCheck[1].isSelected(), refGUI.areasArrayCheck[2].isSelected()};

			//Insert Button pressed.
			if(ae.getSource() == refGUI.insButton) {
				if(!first.isEmpty() && !surname.isEmpty()) {
					//Make sure that the referee's name does not contain any spaces within the first or last name.
					if(!first.contains(" ") && !surname.contains(" ")) {
						//Makes sure that there isn't a referee with that name in the list.
						if(matchProgram.selectRef(first, surname) == null) {
							//Asks user for confirmation.
							int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to add " + first + " " + surname + "?", "Insert confirmation", JOptionPane.OK_CANCEL_OPTION);

							if(choice ==  JOptionPane.OK_OPTION){
								//Inserts the Referee to the match program.
								matchProgram.insertRef(first, surname, qualif, matchesAll, home, willing);
								//Confirms the successful insertion.
								JOptionPane.showMessageDialog(null, "Referee " + first + " " + surname + " successfully added!", "Successful insertion", JOptionPane.INFORMATION_MESSAGE);
								refGUI.dispose();
							}
						} else {
							JOptionPane.showMessageDialog(null, "There is already a referee named " + first + " " + surname + " in the list. Please choose a different name!", "Referee exists!", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "There should not be any spaces within either the first or last name. Please choose a different name!", "Invalid name!", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "The referee's first name or surname cannot be empty", "Incorrect inputs", JOptionPane.ERROR_MESSAGE);
				}

				//Update button pressed.
			}else{
				//Asks user for confirmation.
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to update " + first + " " + surname + "?", "Update confirmation", JOptionPane.OK_CANCEL_OPTION);

				if(choice ==  JOptionPane.OK_OPTION){
					//Updates the Referee based on the user's inputs.
					tempRef.setQualification(qualif);
					tempRef.setLocality(home);
					tempRef.setWillingToGo(willing);

					//Confirms the successful update.
					JOptionPane.showMessageDialog(null, "Referee " + first + " " + surname + " successfully updated!", "Successful update", JOptionPane.INFORMATION_MESSAGE);
					refGUI.dispose();
				}
			}
			//Updates the display.
			mainGUI.display.setText(matchProgram.displayRefereesList());
		}
		//Cancel button pressed.
		else if (ae.getSource() == refGUI.cancelButton) {
			//Closes the GUI.
			refGUI.dispose();
		}

	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		if (e.getSource() == mainGUI) {
			saveExit();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}
