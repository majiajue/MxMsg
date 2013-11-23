package com.mx.client;

import java.util.List;

public class MessageLocationCollection {
	private List<MessageCollection> locations;
	private int numCountries;

	public MessageLocationCollection(List<MessageCollection> locations) {
		this.locations = locations;
		this.numCountries = countCountries(locations);
	}

	public MessageLocationCollection() {
		
		
	}

	public List<MessageCollection> getLocations() {
		return (locations);
	}

	public int getNumCountries() {
		return (numCountries);
	}

	// Assumes the list is sorted by country name

	private int countCountries(List<MessageCollection> Frind) {
		int n = 0;
		String currentPeerId, previousPeerId = "None";
		for (int i = 0; i < locations.size(); i++) {
			currentPeerId = locations.get(i).getM_peerid();
			if (!previousPeerId.equals(currentPeerId))
				n = n + 1;
			currentPeerId = previousPeerId;
		}
		return (n);
	}
}
