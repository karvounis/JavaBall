package model;

import java.util.Comparator;

/**
 * Comparator to sort the Referee objects by their allocated matches.
 * @author Team C
 */
public final class RefereeMatchesComparator implements Comparator<Referee> {	
	@Override
	public int compare(Referee ref1, Referee ref2) {
		if (ref1.getMatchesAllocated() > ref2.getMatchesAllocated())
			return 1;
		else if (ref1.getMatchesAllocated() < ref2.getMatchesAllocated())
			return -1;
		else
			return 0;
	}
}
