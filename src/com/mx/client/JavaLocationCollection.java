package com.mx.client;

import java.util.ArrayList;
import java.util.List;

public class JavaLocationCollection {
	private static List<JavaLocation> defaultLocations = new ArrayList<JavaLocation>();
	static{
		
		defaultLocations.add(new JavaLocation("27631", "majiajue", "head_boy_01_32.jpg"));
		defaultLocations.add(new JavaLocation("27632", "None", "head_boy_01_32.jpg"));
	}
	private List<JavaLocation> locations;
	private int numCountries;

	public JavaLocationCollection(List<JavaLocation> locations) {
		this.locations = locations;
		this.numCountries = countCountries(locations);
	}

	public JavaLocationCollection() {
		
		this(defaultLocations);
	}

	public List<JavaLocation> getLocations() {
		return (locations);
	}

	public int getNumCountries() {
		return (numCountries);
	}

	// Assumes the list is sorted by country name

	private int countCountries(List<JavaLocation> Frind) {
		int n = 0;
		String currentPeerId, previousPeerId = "None";
		for (int i = 0; i < locations.size(); i++) {
			currentPeerId = locations.get(i).getNickName();
			if (!previousPeerId.equals(currentPeerId))
				n = n + 1;
			currentPeerId = previousPeerId;
		}
		return (n);
	}
}
