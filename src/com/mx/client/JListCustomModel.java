package com.mx.client;
import java.awt.*;

import javax.swing.*;


public class JListCustomModel extends JScrollPane {
	
  public JListCustomModel() {
	    this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    JavaLocationCollection collection =
	      new JavaLocationCollection();
	    JavaLocationListModel listModel =
	      new JavaLocationListModel(collection);
	    JList sampleJList = new JList(listModel);
	    sampleJList.setCellRenderer(new JavaLocationRenderer());
	    Font displayFont = new Font("Serif", Font.BOLD, 18);
	    sampleJList.setFont(displayFont);
	    JPanel jPanel = new JPanel();
	    jPanel.setLayout(new BorderLayout());
	    jPanel.add(sampleJList);
	    //content.add(sampleJList);
	    this.setViewportView(jPanel);
	    //setVisible(true);

  }
}
