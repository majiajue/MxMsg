package com.mx.client.webtools;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.mx.clent.vo.AnPeersBean;

/**
 * ��Ϣ������
 * @author majiajue
 *
 */

public class TextManager {
	private String mEncodedSend; // Ҫ���͵����ݣ��Ѿ�������
	private HashMap<String, String> mResult; // ������ȡÿ�η��͵Ľ��
//	private final Queue<QuickMsg> msgQueue = new LinkedList<QuickMsg>();
	private boolean isRunning = true;
//	private ChatSendThread mChatSendMsgThread = null;
	private AnPeersBean mPeerBean;
	private String mMimeSend; // Ҫ���͵����ݣ��Ѿ�����mimeͷ��

//	private class QuickMsg {
//		public QuickMsg(AnMessageBean messageBean, int msgtype) {
//			this.msgtype = msgtype;
//			this.message = messageBean.PMsg;
//			this.peerId = messageBean.PPeerid;
//			this.msgBean = messageBean;
//		}
//
//		public AnMessageBean msgBean;
//		public int msgtype;
//		public String message;
//		public String peerId;
//	}
//	class ChatSendThread extends Thread {
//		QuickMsg sMsg = null;
//		int iQueueSize;
//
//		@Override
//		public void run() {
//			while (true) {
//				try {
//					if (!isRunning) {
//						break;
//					}
//					synchronized (this) {
//						wait();
//						iQueueSize = msgQueue.size();
//						if (iQueueSize > 0) {
//							sMsg = msgQueue.poll();
//						}
//					}
//					while (iQueueSize > 0) {
//						sendMessageOut(sMsg, 1);
//						synchronized (this) {
//							iQueueSize = msgQueue.size();
//							if (iQueueSize > 0) {
//								sMsg = msgQueue.poll();
//							}
//						}
//					}
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	};
}
