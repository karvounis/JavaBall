package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Maintains an array of matches and an ArrayList of referees.
 * Contains methods that allows objects to be added and removed.
 * @author Team C
 */
public final class MatchProgram {

	/**String that contains the format of the first line of the display.*/
	private static final String FIRST_LINE_FORMAT = "   %-7s %-18s %-18s %-18s %-21s %-19s %-18s";
	/**String that contains the format of the following lines of the display.*/
	private static final String LINES_FORMAT = "   %-7s %-18s %-22s %-21s %-14s %-24s %-18s";

	/**Class constant. Represents the maximum number of weeks.*/
	private static final int WEEKS = 52;	

	/**ArrayList containing all the referees.*/
	private ArrayList<Referee> refereesList; 

	/**Array that contains all the matches.*/
	private Match[] matchesArray;

	/**
	 * Constructor that instantiates an array that contains the matches and is size 52 and
	 * an ArrayList that contain all the referees.
	 */
	public MatchProgram(){
		matchesArray = new Match[WEEKS];
		refereesList = new ArrayList<Referee>();
	}

	/**
	 * Returns an arrayList of Referees that have Senior Qualification.
	 * @return
	 */
	private ArrayList<Referee> getSeniorRefs(){
		ArrayList<Referee> seniorRefs = new ArrayList<Referee>();
		
		for(Referee tempRef : refereesList){
			if(tempRef.isSenior()){
				seniorRefs.add(tempRef);
			}
		}
		return seniorRefs;
	}

	/**
	 *  Returns a large String with all the referees to be displayed
	 *  in GUI class
	 */
	public String displayRefereesList()
	{
		StringBuilder mainStr = new StringBuilder();
		String id, firstName, lastName, qualification, locality, willingToGo = "", matchallocStr = "";
		int matchallocations;

		//Sort the referees list based on their ID.
		RefereeIDComparator comparator = new RefereeIDComparator();
		Collections.sort(refereesList, comparator);

		//First Line
		mainStr.append( String.format("%n" + FIRST_LINE_FORMAT + "%n%104s %20s %n%n", "ID", "First Name", "Last Name", "Qualification", "Match Allocations", "Locality","Willing to go", " ", "[North, Central, South]"));

		// Iterates through list
		for(Referee tempRef: refereesList){

			//Gets all piece of information for every referee
			id = tempRef.getRefID();

			firstName = tempRef.getFirstName();

			lastName = tempRef.getLastName();

			qualification = tempRef.getQualification();

			locality = tempRef.getStringLocality();

			matchallocations = tempRef.getMatchesAllocated();
			matchallocStr = "" + matchallocations;

			//Processes boolean table willing to go
			willingToGo = tempRef.getStringWilling();

			mainStr.append( String.format(LINES_FORMAT + "%n", id, firstName, lastName, qualification, matchallocStr, locality, willingToGo) );

			//Sets willing to go to empty in order not to include previous strings via concatenation
			willingToGo = "";
		}

		return mainStr.toString();
	}

	/**
	 * Returns the arrayList of Referees.
	 * @return
	 */
	public ArrayList<Referee> getAllRefs(){
		return refereesList;
	}

	/**
	 * Sets the array of referees according to the inputs from the RefereesIn.txt file.
	 * @param addInitRef Referee to be added to the list
	 */
	public void insertInitialRef(Referee addInitRef){
		refereesList.add(addInitRef);
	}

	/**
	 * Given the full name of a referee, returns all the details of this referee.
	 * @param refName	The first name of the referee.
	 * @param refSurname	The surname of the referee.
	 * @return	A Referee object or null.
	 */
	public Referee selectRef(String refName, String refSurname){

		for(Referee ref : refereesList){
			if(ref.getFirstName().equals(refName) && ref.getLastName().equals(refSurname)){
				return ref;
			}
		}
		//If no referee with this first and last name is found, return null.
		return null;
	}

	/**
	 * Inserts a Referee to the list.
	 * @param refName	Name of the referee.
	 * @param refSurname	Surname of the referee.
	 * @param qualification	Qualification
	 * @param matchesAllocated	Number of matches already allocated to this referee.
	 * @param home	Home of the referee.
	 * @param willing	Areas that the referee is willing to go.
	 */
	public void insertRef(String refName, String refSurname, String qualification, int matchesAllocated, int home, boolean[] willing){

		//Based on the referee's first and last name, calculate a unique ID.
		String tmpID = giveID(refName, refSurname);

		Referee newRef = new Referee(tmpID, refName, refSurname, qualification, matchesAllocated, home, willing);

		//Adds him to the list of referees.
		refereesList.add(newRef);
	}

	/**
	 * Given the first name and the last name of a referee, calculates a unique id of the newly added referee.
	 * @param refName	Referee's first name.
	 * @param refSurname	Referee's last name.
	 * @return	Referee's unique ID.
	 */
	private String giveID(String refName, String refSurname){
		//We know that the ID is going to be the first letter of first name followed by the first letter of the last name.
		//The ID finishes with a sequence number. Counter helps us calculate this number.
		String tempID = ("" + refName.charAt(0) + refSurname.charAt(0)).toUpperCase();
		int counter = 1;

		for(Referee tempRef : refereesList){
			String eachRefID = tempRef.getRefID().substring(0, 2);

			//If another referee's ID's first two letters are equal to the newly added, then we increment the counter.
			if(eachRefID.equalsIgnoreCase(tempID)){
				counter++;
			}
		}
		//Returns the ID followed by a sequence number.
		return (tempID + counter);
	}
	
	/**
	 * Given the full name of a referee, deletes this referee from the array of referees.
	 * @param refName	The first name of the referee.
	 * @param refSurname	The surname of the referee.
	 * @return	If a referee was found and deleted, then true is returned. Otherwise, returns false.
	 */
	public void deleteRef(Referee referee){
		refereesList.remove(referee);
	}

	/**
	 * Given a week number, returns the object Match that is played in this week number.
	 * @param weekNumber
	 * @return	A Match object.
	 */
	public Match getMatch(int weekNumber){
		if(matchesArray[weekNumber - 1] != null){
			return matchesArray[weekNumber - 1]; 
		}
		return null;
	}

	/**
	 * Returns all the Matches.
	 * @return
	 */
	public Match[] getMatches(){
		return matchesArray;
	}

	/**
	 * Creates a match based on the user's inputs and then allocates 2 referees to this match.
	 * @param week	The number of the week that the match is going to take place.
	 * @param category	Category of the match. There are 2 possible values: ["Junior", "Senior"].
	 * @param area	The area that the match is going to take place ["North", "Central", "South"].
	 */
	public void allocateRefsToMatch(int week, int area, String category) {

		Match newMatch = new Match(week, area, category);
		boolean success;

		if(category.equals("Senior")){
			//If the match is "Senior" then only referees with Senior qualification can referee the match.
			//getSeniorRefs() returns an arrayList of Referees.
			success = newMatch.allocateRefs(getSeniorRefs());
		}else{
			//If the match is "Junior" then all the referees can referee the match.
			success = newMatch.allocateRefs(getAllRefs());
		}
		//If the referee allocation was successful, insert the match to the array of matches
		if (success) {
			matchesArray[week-1] = newMatch;
		}
	}
}
