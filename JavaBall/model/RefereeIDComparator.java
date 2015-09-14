package model;

import java.util.Comparator;

/**
 * Comparator to sort the Referee objects by their IDs.
 * @author Team C
 */
public final class RefereeIDComparator implements Comparator<Referee> {	
	@Override
	public int compare(Referee ref1, Referee ref2) {
		int result = ref1.getRefID().compareTo(ref2.getRefID());
		return result;
	}
}
