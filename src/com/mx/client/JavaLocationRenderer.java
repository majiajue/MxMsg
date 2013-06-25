package com.mx.client;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/** Simple custom cell renderer. The idea here is to augment
 *  the default one instead of building one from scratch. The
 *  advantage of this is that you don't have to handle the
 *  highlighting of the selected entries yourself, plus values that
 *  aren't of the new type you want to draw can be handled
 *  automatically. The disadvantage is that you are limited to a
 *  variation of a JLabel, which is what the default renderer returns.
 *  <P>
 *  Note that this method can get called lots and lots of times as you
 *  click on entries. But I don't want to keep generating new ImageIcon
 *  objects. So I make a Hashtable that associates previously displayed
 *  values with icons, reusing icons for entries that have been displayed
 *  already.
 *  <P>
 *  Note that in the first release of 1.2, the default renderer
 *  has a bug in that it doesn't clear out icons for later entries.
 *  so if you mix plain strings and ImageIcons in your JList, the
 *  plain strings still get an icon. Thus the call below that clears
 *  the old icon when the value is not a JavaLocation.
 *  <P>
 *  1998-99 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class JavaLocationRenderer extends DefaultListCellRenderer {
  private Hashtable iconTable = new Hashtable();
  
  public Component getListCellRendererComponent(JList list,
                                                Object value,
                                                int index,
                                                boolean isSelected,
                                                boolean hasFocus) {
    JLabel label =
      (JLabel)super.getListCellRendererComponent(list,
                                                 value,
                                                 index,
                                                 isSelected,
                                                 hasFocus);
    System.out.println("value==="+value.getClass());
    if (value instanceof JavaLocation) {
    	System.out.println("===");
      JavaLocation location = (JavaLocation)value;
      ImageIcon icon = (ImageIcon)iconTable.get(value);
      if (icon == null) {
    	  //System.out.println(location.getFlagFile());
        icon = new ImageIcon(this.getClass()
				.getResource("/com/mx/client/headImage/head_boy_01_32.jpg"));
        iconTable.put(value, icon);
      }
      label.setIcon(icon);
    } else {
 
      label.setIcon(null); 
    }
    return(label);
  }
}
