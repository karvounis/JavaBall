package model;

/**
 * Referee object containing information about our referees.
 * @author Team C
 */
public final class Referee {
	/**Static integers to store the area of the match.*/
	public static final int AREA_NORTH = 0, AREA_CENTRAL = 1, AREA_SOUTH = 2;

	private String refID, firstName, lastName, qualification;
	private int matchesAllocated, locality;

	//This integer is used to check if this Referee has been allocated to a match. If >0 then the program should not allow the user to delete him from the list of Referees.
	private int actualMatches;

	//Array storing boolean values whether a referee is willing to go to other areas.
	//[0: North, 1: Central, 2: South]. A referee is always willing to go to his home area.
	private boolean[] willingToGo = new boolean[3];	

	/**
	 * Constructor. Splits the input String to an array of String and extracts information to the instance variables through this array.
	 * @param container	An entire line read from the input file.
	 */
	public Referee (String container) {

		String[] tmpArray = container.split("[ ]+");
		refID = tmpArray[0];
		firstName = tmpArray[1];
		lastName = tmpArray[2];
		qualification = tmpArray[3];
		matchesAllocated = Integer.parseInt(tmpArray[4]);
		//In case of the referee's home area, we use three constants.
		switch (tmpArray[5].toLowerCase()) {
		case "north":
			locality = AREA_NORTH;
			break;
		case "central":
			locality = AREA_CENTRAL;
			break;
		case "south":
			locality = AREA_SOUTH;
			break;
		}

		for (int i = 0; i < 3; i++) {
			if (tmpArray[6].charAt(i) == 'Y') {
				willingToGo[i] = true;
			} else {
				willingToGo[i] = false;
			}
		}
		actualMatches = 0;
	}

	/**
	 * Constructor that instantiates a Referee object given multiple values.
	 * @param refID	The referee's ID
	 * @param firstName The referee's first name
	 * @param lastName The referee's last name
	 * @param qualification The referee's qualification
	 * @param matchesAllocated The matches allocated to the referee
	 * @param locality The referee's home locality
	 * @param willingToGo Where the referee is willing to go
	 */
	public Referee (String refID, String firstName, String lastName, String qualification, int matchesAllocated, int locality, boolean[] willingToGo) {
		this.refID = refID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.qualification = qualification;
		this.matchesAllocated = matchesAllocated;
		this.locality = locality;
		this.willingToGo = willingToGo;
		actualMatches = 0;
	}

	/**
	 * Returns the referees's ID
	 * @return a String containing the referee's ID
	 */
	public String getRefID () {
		return refID;
	}

	/**
	 * Returns the referees's first name
	 * @return a String containing the referee's first name
	 */
	public String getFirstName () {
		return firstName;
	}

	/**
	 * Returns the referees's last name
	 * @return a String containing the referee's last name
	 */
	public String getLastName () {
		return lastName;
	}

	/**
	 * Returns the referees's qualification
	 * @return a String containing the referee's qualification
	 */
	public String getQualification () {
		return qualification;
	}

	/**
	 * Returns if a referee is senior or not
	 * @return a boolean indicating if a referee is senior
	 */
	public boolean isSenior () {
		if (qualification.charAt(3) == '1') {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns the referees's home locality
	 * @return an integer representing the referee's home locality
	 */
	public int getLocality () {
		return locality;
	}

	/**
	 * Returns the matches allocated to the referee.
	 * @return an integer representing the matches allocated to the referee.
	 */
	public int getMatchesAllocated () {
		return matchesAllocated;
	}

	/**
	 * Returns the number of actual matches allocated to the referee
	 * @return an integer representing the actual matches allocated to the referee.
	 */
	public int getActualMatches () {
		return actualMatches;
	}

	/**
	 * Returns a boolean array size of 3 with values regarding whether the referee is willing to go to an area.
	 * @return
	 */
	public boolean[] getWillingToGoAreas () {
		return willingToGo;
	}

	/**
	 * Given an area, returns if the referee is willing to go to this area.
	 * @param area	Area that the match is going to take place.
	 * @return	true or false.
	 */
	public boolean isWillingToGo (int area) {
		return willingToGo[area];
	}

	/**
	 * Sets the qualification of a referee.
	 * @param qual
	 */
	public void setQualification (String qual) {
		qualification = qual;
	}

	/**
	 * Sets the locality or home area of a referee.
	 * @param loc
	 */
	public void setLocality (int loc) {
		locality = loc;
		//Makes sure that the referee is willing to go to his home area.
		willingToGo[loc] = true;
	}

	/**
	 * Increments the number of matches allocated to this referee.
	 */
	public void incMatches () {
		matchesAllocated++;
		//Increments the actual matches of a referee.
		actualMatches++;
	}

	/**
	 * Given an area and a true or false value, sets or clears respectively the cell in the willing to go array that corresponds to the given area..
	 * @param willing
	 */
	public void setWillingToGo (boolean[] willing) {
		willingToGo = willing;
	}

	/**
	 * Returns a String representation of the locality of the Referee object.
	 * @return	String that has three possible values: "North" or "Central" or "South".
	 */
	public String getStringLocality()
	{
		if(this.locality == AREA_NORTH)
		{
			return  "North";
		}
		else if(this.locality == AREA_CENTRAL)
		{
			return "Central";
		}
		else
		{
			return "South";
		}
	}

	/**
	 * Returns a String representation of the Referee's willingness to go to the three areas.
	 * @return String representation of the referee's willingness to go to the three areas.
	 */
	public String getStringWilling()
	{
		String result = "";
		for(int i = 0; i < 3; i++)
		{
			if(this.willingToGo[i])
			{
				result += "Y";
			}
			else
			{
				result += "N";
			}
		}
		return result;
	}	
}
