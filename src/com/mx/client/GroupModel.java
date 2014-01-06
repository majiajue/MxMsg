package com.mx.client;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class GroupModel implements ListModel {
	 private GroupLocationCollection collection;
	 
	public GroupModel(GroupLocationCollection collection) {
		super();
		this.collection = collection;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return collection.getLocations().size();
	}

	@Override
	public Object getElementAt(int index) {
		// TODO Auto-generated method stub
		return collection.getLocations().get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}
    
}
