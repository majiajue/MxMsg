package com.mx.client;

public class JavaLocationCollection {
	private static JavaLocation[] defaultLocations = { new JavaLocation("27631", "majiajue", "head_boy_01_32.jpg"),
			new JavaLocation("27632", "None", "head_boy_01_32.jpg"),
			new JavaLocation("3", "None", "head_boy_01_32.jpg"), new JavaLocation("4", "None", "head_boy_01_32.jpg") };

	private JavaLocation[] locations;
	private int numCountries;

	public JavaLocationCollection(JavaLocation[] locations) {
		this.locations = locations;
		this.numCountries = countCountries(locations);
	}

	public JavaLocationCollection() {
		this(defaultLocations);
	}

	public JavaLocation[] getLocations() {
		return (locations);
	}

	public int getNumCountries() {
		return (numCountries);
	}

	// Assumes the list is sorted by country name

	private int countCountries(JavaLocation[] Frind) {
		int n = 0;
		String currentPeerId, previousPeerId = "None";
		for (int i = 0; i < locations.length; i++) {
			currentPeerId = locations[i].getNickName();
			if (!previousPeerId.equals(currentPeerId))
				n = n + 1;
			currentPeerId = previousPeerId;
		}
		return (n);
	}
}
