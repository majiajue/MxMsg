package com.mx.client;

import java.util.ArrayList;
import java.util.List;

public class GroupLocationCollection {
	private static List<GroupLocation> defaultLocations = new ArrayList<GroupLocation>();
	private List<GroupLocation> locations;
	private int numCountries;

	public GroupLocationCollection(List<GroupLocation> locations) {
		this.locations = locations;
		this.numCountries = countCountries(locations);
	}

	public GroupLocationCollection() {
		
		this(defaultLocations);
	}

	public List<GroupLocation> getLocations() {
		return (locations);
	}

	public int getNumCountries() {
		return (numCountries);
	}

	// Assumes the list is sorted by country name

	private int countCountries(List<GroupLocation> Frind) {
		int n = 0;
		String currentPeerId, previousPeerId = "None";
		for (int i = 0; i < locations.size(); i++) {
			currentPeerId = locations.get(i).getGroupName();
			if (!previousPeerId.equals(currentPeerId))
				n = n + 1;
			currentPeerId = previousPeerId;
		}
		return (n);
	}
}
