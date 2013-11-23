package com.mx.client;

import javax.swing.DefaultListModel;

public class MessageModel extends DefaultListModel {
	private MessageLocationCollection collection;

	public MessageModel(MessageLocationCollection collection) {
		this.collection = collection;
	}

	public Object getElementAt(int index) {
		return (collection.getLocations().get(index));
	}

	public int getSize() {
		return (collection.getLocations().size());
	}
}
