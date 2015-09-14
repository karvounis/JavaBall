package model;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 * Holds information about a JavaBall match.
 * @author Team C
 */
public final class Match {
	/**Static integers to store the area of the match.*/
	public static final int AREA_NORTH = 0, AREA_CENTRAL = 1, AREA_SOUTH = 2;
	/**Level of the match*/
	private String matchLevel;
	/**ArrayList that stores the Referees in order of suitability*/
	private ArrayList<Referee> suitableRefs;
	/**Number of the week that the match is going to take place.*/
	private int matchWeekNumber;
	/**Area that the match is going to take place.*/
	private int matchArea;
	/**Stores the two most suitable Referees for that match.*/
	private Referee suitableRef1, suitableRef2;

	/**
	 * Constructor.
	 * @param matchID	The ID of the match.
	 * @param matchArea	The area that the match is going to take place.
	 * @param matchLevel	Level of the match. Senior or Junior.
	 */
	public Match(int matchWeekNumber, int matchArea, String matchLevel){

		this.matchWeekNumber = matchWeekNumber;
		this.matchArea = matchArea;
		this.matchLevel = matchLevel;
		suitableRefs = new ArrayList<Referee>();
	}

	/**
	 * Returns the week that the match is going to take place.
	 * @return
	 */
	public int getMatchWeekNumber(){
		return matchWeekNumber;
	}

	/**
	 * Returns the area that the match is going to take place.
	 * @return	0 = North, 1 = Central, 2 = South.
	 */
	public int getMatchArea(){
		return matchArea;
	}

	/**
	 * Returns the level of the match.
	 * @return "Junior" or "Senior"
	 */
	public String getMatchLevel(){
		return matchLevel;
	}

	/**
	 * Returns the first most suitable referee that is assigned to this match. 
	 * @return	Referee object
	 */
	public Referee getFirstReferee(){
		return suitableRef1;
	}

	/**
	 * Returns the second most suitable referee that is assigned to this match.
	 * @return	Referee object
	 */
	public Referee getSecondReferee(){
		return suitableRef2;
	}

	/**
	 * Returns an arrayList containing all the suitable referees.
	 * @return
	 */
	public ArrayList<Referee> getSuitableRefs () {		
		return suitableRefs;
	}

	/**
	 * Allocates the 2 most suitable Referees to a match. Displays a list of all the referees in descending order of suitability.
	 * Also, displays the 2 most suitable referees to the user.
	 * @param refs	The list of referees
	 */
	public boolean allocateRefs(ArrayList<Referee> refs) {

		//If we have less than 2 Referees, prints an error and exits the method.
		if(refs.size() < 2){
			JOptionPane.showMessageDialog(null, "There aren't enough suitable referees in the database!", "Not enough referees!", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Creates three new ArrayLists to store the Referees based on their distance from the match area.
		//Local are the Referees that live in the area that the match is taking place.
		ArrayList<Referee> local = new ArrayList<Referee>();
		//Adjacent are the Referees that live in an adjacent area that the match is taking place.
		ArrayList<Referee> adjacent = new ArrayList<Referee>();
		//Far are the Referees that live in a non adjacent area that the match is taking place.
		ArrayList<Referee> far = new ArrayList<Referee>();

		//Loops all the Referees and based on their distance from the match area, adds them to the corresponding ArrayList.
		for (Referee referee : refs) {
			//Calculates the distance from the match area.
			int areaDiff = Math.abs(referee.getLocality() - matchArea);
			boolean willing2go = referee.isWillingToGo(matchArea);

			//If the referee is not willing to go to this area, skip him.
			//Local referees are willing to go, this is ensured elsewhere
			if (!willing2go) {
				continue;
			}

			if (areaDiff == 0) {		//Local referee
				local.add(referee);
			} else if (areaDiff == 1) {	//Referee from adjacent areas
				adjacent.add(referee);
			} else {					//Referee from non-adjacent area
				far.add(referee);
			}
		}

		//Instantiates a RefereeMatchesComparator object. That object compares two referees based on their match allocations so far.
		RefereeMatchesComparator comparator = new RefereeMatchesComparator();

		//Sorts the three ArrayLists based on the previous comparator.
		Collections.sort(local, comparator);
		Collections.sort(adjacent, comparator);
		Collections.sort(far, comparator);

		//Stores all the Referees in the suitableRefs ArrayList.
		//First the local Referees are added to the suitableRefs ArrayList. Then the adjacent ones and last the far ones.
		//The three ArrayLists are already sorted before merged, so we know that the two most suitable referees are the first two Referees in the suitableRefs ArrayList.
		for (Referee r : local) {
			suitableRefs.add(r);
		}

		for (Referee r : adjacent) {
			suitableRefs.add(r);
		}

		for (Referee r : far) {
			suitableRefs.add(r);
		}

		if (suitableRefs.size() < 2) {
			JOptionPane.showMessageDialog(null, "There aren't enough suitable referees willing to go to that area", "Not enough suitable referees!", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//StringBuilder that provides the display of the suitable referees and the 2 most suitable ones.
		StringBuilder sRefs = new StringBuilder();
		sRefs.append("List of suitable referees for this match in descending order of suitability.\n\n");

		for (Referee r: suitableRefs) {
			sRefs.append(r.getFirstName() + " " + r.getLastName() + ", Matches Allocated: " + r.getMatchesAllocated() + "\n");
		}

		sRefs.append("\nThe two most suitable referees for the match are the following: \n\n");
		suitableRef1 = suitableRefs.get(0);
		//Increments the number of matches of the first Referee.
		suitableRef1.incMatches();

		suitableRef2 = suitableRefs.get(1);
		//Increments the number of matches of the second Referee.
		suitableRef2.incMatches();

		sRefs.append(suitableRef1.getFirstName() + " " + suitableRef1.getLastName() + "\n");
		sRefs.append(suitableRef2.getFirstName() + " " + suitableRef2.getLastName() + "\n");

		//Prints the information message.
		JOptionPane.showMessageDialog(null, sRefs.toString(), "", JOptionPane.INFORMATION_MESSAGE);
		return true;
	}
}
