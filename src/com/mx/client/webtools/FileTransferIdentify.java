package com.mx.client.webtools;

public class FileTransferIdentify {
	/**
	 * FileTaskId 服务器返回给客户端的每个文件传输任务的唯一标识
	 */
	public String FileTaskId;

	/**
	 * 在没有获取到FileTaskId前的本地文件唯一标识
	 */
	public String FileId;

	/**
	 * 传输任务队列的唯一标识
	 */
	public String TaskId;

	public long MessageID;

	public FileTransferIdentify() {
		MessageID = -1;
	}
}
