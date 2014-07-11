package com.mx.client;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataListener;

public class MessageModel extends DefaultListModel {
	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub
		super.addListDataListener(arg0);
	}

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
