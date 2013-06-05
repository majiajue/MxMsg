package com.mx.client;

/*
 * DefaultCompletionFilter.java
 *
 * Created on 2007-6-21, 23:18:58
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.mx.clent.vo.MsgUser;



/**
 *
 * 
 */
public class DefaultCompletionFilter implements CompletionFilter {

	private Map<String ,MsgUser> friendMap;

    public DefaultCompletionFilter() {
    	friendMap = new HashMap<String ,MsgUser>();
    }

    public DefaultCompletionFilter(Map<String ,MsgUser> v) {
    	friendMap = v;
    }
    public ArrayList filter(String text) {
        ArrayList list=new ArrayList();
        String txt=text.trim();
        int length=txt.length();
        
        for(String userID:friendMap.keySet()){
        	if(length==0||userID.startsWith(txt)){
        		list.add(userID);
        	}
        }
        return list;
    }
}
