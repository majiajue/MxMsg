package com.mx.client;

import java.util.HashMap;

public class CustomFace {
	public final static String[] customFaces = { "/微 笑", "/撇 嘴", "/  色", "/发 呆", "/得 意",
			"/流 泪", "/害 羞", "/闭 嘴", "/睡 觉", "/大 哭", "/尴 尬", "/发 怒", "/调 皮", "/呲 牙", "/惊 讶",
			"/难 过", "/酷 酷", "/冷 汗", "/抓 狂", "/呕 吐", "/偷 笑", "/可 爱", "/白 眼", "/傲 慢",
			"/饥 饿", "/困 乏", "/惊 恐", "/流 汗", "/憨 笑", "/大 兵", "/奋 斗", "/谩 骂", "/疑 问",
			"/嘘..", "/  晕", "/折 磨", "/  衰", "/骷 髅", "/敲 打", "/再 见", "/擦 汗", "/抠 鼻",
			"/鼓 掌", "/糗大了", "/坏 笑", "/左哼哼", "/右哼哼", "/哈 欠", "/鄙 视", "/委 屈", "/快哭了",
			"/阴 险", "/亲 亲", "/  吓", "/可 怜", "/菜 刀", "/西 瓜", "/啤 酒", "/篮 球", "/乒 乓",
			"/咖 啡", "/  饭", "/猪 头", "/玫 瑰", "/凋 谢", "/示 爱", "/爱 心", "/心 碎", "/蛋 糕",
			"/闪 电", "/炸 弹", "/  刀", "/足 球", "/瓢 虫", "/便 便", "/月 亮", "/太 阳", "/礼 物", "/拥 抱",
			"/  强", "/  弱", "/握 手", "/胜 利", "/抱 拳", "/勾 引", "/拳 头", "/差 劲", "/爱 你", "/ NO",
			"/ OK", "/爱 情", "/飞 吻", "/跳 跳", "/发 抖", "/怄 火", "/转 圈", "/磕 头", "/回 头",
			"/跳 绳", "/挥 手", "/激 动", "/街 舞", "/献 吻", "/左太极", "/右太极" };

	public final static HashMap<Integer, String> faceIntToStr;
	public final static HashMap<String,Integer> faceStrToInt;

	static {
		faceIntToStr = new HashMap<Integer, String>();
		for (int i = 0; i < customFaces.length; i++) {
			faceIntToStr.put(i,customFaces[i] );
		}
	}
	
	static {
		faceStrToInt = new HashMap<String, Integer>();
		for (int i = 0; i < customFaces.length; i++) {
			faceStrToInt.put(customFaces[i],i );
		}
	}
	
	
}
