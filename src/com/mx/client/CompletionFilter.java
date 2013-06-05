package com.mx.client;

/*
 * AutoCompletionFilter.java
 * 
 * Created on 2007-6-21, 22:57:11
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author William Chen
 */
public interface CompletionFilter {
    ArrayList filter(String text);
}
