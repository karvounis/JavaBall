package model;

import java.util.ArrayList;
import java.util.Collections;
import java.io.*;
import javax.swing.JOptionPane;

/**
 * Handles the output for Referees.txt and MatchAllocs.txt files.
 * @author Team C
 */
public final class FileOutput {

	/**Format of the output file's lines.*/
	private static final String OUTPUT_FORMAT = "%15s %17s %17s %25s %25s";
	/**Filenames of the output files*/
	private static final String REFEREES_OUT = "RefereesOut.txt", MATCHES_ALLOCATIONS = "MatchAllocs.txt";

	private Match[] matchArr;
	private FileWriter fwriter = null;

	public FileOutput(Match[] matchArr)
	{
		this.matchArr = matchArr;
	}

	/**
	 * Write the RefereesOut.txt file
	 * @param refs
	 */
	public void RefOut(ArrayList<Referee> refs)
	{
		StringBuilder output = new StringBuilder();
		//Instantiates the RefereeIDComparator comparator to sort the Referee objects in order of their ID.
		RefereeIDComparator comparator = new RefereeIDComparator();
		Collections.sort(refs, comparator);		
		
		//For every referee, extract his details and output them to the file.
		for(Referee tempRef : refs)
		{
			String id, firstName, lastName, qualification, locality;
			int matchallocations;
			String willingToGo="";
			String matchallocStr = "";
			id = tempRef.getRefID();

			firstName = tempRef.getFirstName();
			lastName = tempRef.getLastName();
			qualification = tempRef.getQualification();
			locality = tempRef.getStringLocality();
			matchallocations = tempRef.getMatchesAllocated();
			matchallocStr = "" + matchallocations;
			willingToGo = tempRef.getStringWilling();

			output.append(String.format("%s %s %s %s %s %s %s", id, firstName, lastName, qualification, matchallocStr, locality, willingToGo));
			output.append("\r\n");

			//Set willing to go to empty in order not to include previous strings via concatenation
			willingToGo = "";
		}
		try{
			try{
				//Prints the output String to the file.
				fwriter = new FileWriter( REFEREES_OUT );
				fwriter.write(output.toString());				
			}
			finally
			{
				if(fwriter != null)
				{
					fwriter.close();
				}
			}
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "There was an error writing the RefereesOut.txt file.", "Write failed", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Write the MatchAllocs.txt file
	 * @param refs
	 */
	public void MatchOut()
	{
		StringBuilder output = new StringBuilder();

		output.append( String.format("%50s", "Match Allocations") );
		output.append("\r\n\r\n");

		output.append( String.format(OUTPUT_FORMAT, "Week Number", "Match Level", "Area", "First Referee", "Second Referee") );
		output.append("\r\n");

		//For every match that has been created, extract its information and append them to the StringBuilder.
		for(int i = 0; i < matchArr.length; i++)
		{
			if(matchArr[i] != null)
			{
				String level, areaStr = "";
				int weekNumber,area;
				String fname1, lname1, fname2, lname2, fullname1, fullname2;

				level = matchArr[i].getMatchLevel();

				weekNumber = matchArr[i].getMatchWeekNumber();

				area = matchArr[i].getMatchArea();

				if(area == Referee.AREA_CENTRAL)
				{
					areaStr = "Central";				
				}
				else if(area == Referee.AREA_NORTH)
				{
					areaStr = "North";
				}
				else if(area == Referee.AREA_SOUTH)
				{
					areaStr = "South";
				}
				//Full name of the first Referee.
				fname1 = matchArr[i].getFirstReferee().getFirstName();
				lname1 = matchArr[i].getFirstReferee().getLastName();
				fullname1 = fname1 + " " + lname1;
				
				//Full name of the second Referee.
				fname2 = matchArr[i].getSecondReferee().getFirstName();				
				lname2 = matchArr[i].getSecondReferee().getLastName();				
				fullname2 = fname2 + " " + lname2;

				output.append( String.format(OUTPUT_FORMAT, weekNumber, level, areaStr, fullname1, fullname2) );
				output.append("\r\n");
			}
		}
		try{
			try{
				fwriter = new FileWriter( MATCHES_ALLOCATIONS );
				fwriter.write( output.toString() );
			}
			finally
			{
				if(fwriter != null)
				{
					fwriter.close();
				}
			}
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(null, "There was an error writing the match allocation file.", "Write failed", JOptionPane.ERROR_MESSAGE);
		}		
	}
}
