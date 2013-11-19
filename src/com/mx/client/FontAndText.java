package com.mx.client;

import java.awt.Color;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class FontAndText {
	private String msg = "", name = "宋体"; // 要输入的文本和字体名称

	private int size = 0; // 字号

	private Color color = new Color(225, 225, 225); // 文字颜色

	private SimpleAttributeSet attrSet = null; // 属性集

	/**
	 * 一个空的构造（可当做换行使用）
	 */

	public FontAndText() {
	}

	public FontAndText(String msg, String fontName, int fontSize, Color color) {
		this.msg = msg;
		this.name = fontName;
		this.size = fontSize;
		this.color = color;
	}

	/**
	 * 返回属性集
	 * 
	 * @return
	 */
	public SimpleAttributeSet getAttrSet() {
		attrSet = new SimpleAttributeSet();
		if (name != null) {
			StyleConstants.setFontFamily(attrSet, name);
		}
		StyleConstants.setBold(attrSet, false);
		StyleConstants.setItalic(attrSet, false);
		StyleConstants.setFontSize(attrSet, size);
		if (color != null)
			StyleConstants.setForeground(attrSet, color);
		return attrSet;
	}

	public String toString() {
		// 将消息分为四块便于在网络上传播
		return name + "|" + size + "|" + color.getRed() + "-"
				+ color.getGreen() + "-" + color.getBlue() + "|" + msg;
	}

	public String getText() {
		return msg;
	}

	public void setText(String text) {
		this.msg = text;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
