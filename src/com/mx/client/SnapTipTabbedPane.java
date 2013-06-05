package com.mx.client;

/*
 * DynoTabbedPane.java
 *
 * Created on 2007��3��27��, ����10:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JToolTip;

/**
 *
 * @author rehte
 */
public class SnapTipTabbedPane extends JTabbedPane{
    private double scaleRatio=0.5;
    private HashMap<String, Component> maps=new HashMap<String, Component>();
    public void insertTab(String title, Icon icon, Component component, String tip, int index) {
        tip="tab"+component.hashCode();
        maps.put(tip, component);
        super.insertTab(title, icon, component, tip, index);
    }
    
    public void removeTabAt(int index) {
        Component component=getComponentAt(index);
        maps.remove("tab"+component.hashCode());
        super.removeTabAt(index);
    }
    
    public JToolTip createToolTip() {
        ImageToolTip tooltip=new ImageToolTip();
        tooltip.setComponent(this);
        return tooltip;
    }
    class ImageToolTip extends JToolTip{
        public Dimension getPreferredSize() {
            String tip=getTipText();
            Component component=maps.get(tip);
            if(component!=null)
                return new Dimension((int)(getScaleRatio()*component.getWidth()),(int)(getScaleRatio()*component.getHeight()));
            else
                return super.getPreferredSize();
        }
        public void paintComponent(Graphics g) {
            String tip=getTipText();
            Component component=maps.get(tip);
            if(component instanceof JComponent){
                JComponent jcomponent=(JComponent)component;
                Graphics2D g2d=(Graphics2D)g;
                AffineTransform at=g2d.getTransform();
                g2d.transform(AffineTransform.getScaleInstance(getScaleRatio(), getScaleRatio()));
                ArrayList<JComponent> dbcomponents=new ArrayList<JComponent>();
                updateDoubleBuffered(jcomponent, dbcomponents);
                jcomponent.paint(g);
                resetDoubleBuffered(dbcomponents);
                g2d.setTransform(at);
            }
        }
        private void updateDoubleBuffered(JComponent component, ArrayList<JComponent> dbcomponents){
            if(component.isDoubleBuffered()){
                dbcomponents.add(component);
                component.setDoubleBuffered(false);
            }
            for(int i=0;i<component.getComponentCount();i++){
                Component c=component.getComponent(i);
                if(c instanceof JComponent)
                    updateDoubleBuffered((JComponent)c, dbcomponents);
            }
        }
        private void resetDoubleBuffered(ArrayList<JComponent> dbcomponents){
            for(JComponent component:dbcomponents)
                component.setDoubleBuffered(true);
        }
    }
    public double getScaleRatio() {
        return scaleRatio;
    }
    
    public void setScaleRatio(double scaleRatio) {
        this.scaleRatio = scaleRatio;
    }
}
