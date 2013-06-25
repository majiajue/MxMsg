package com.mx.client.msg;

public class MessageType {
	/**系统消息	     */
	public static final int SysMessage_Type                 =100010;
	
	/**用户  上线消息	 */
	public static final int OnlineMessage_Type              =100011;
	
	/**用户  下线消息	 */
	public static final int ExitMessage_Type                =100012;
	
	/**用户  登入消息	 */
	public static final int LoginMessage_Type               =100013;
	
	public static final int RepeatLoginMessage_Type         =100113;
	/**用户  群聊天消息*/
	public static final int TeamMessage_Type                =100014;
	
	/**用户  登入响应消息	 */
	public static final int LoginResponseMessage_Type       =800013;
	
	/**用户 聊天消息	 */
	public static final int TalkMessage_Type                =100021;
	
	/**用户 文件消息	 */
	public static final int FileMessage_Type                =100022;
	/**用户 文件请求接受消息	 */
	public static final int FileRequestMessage_Type         =100023;
	/**用户 响应 文件请求接受消息		 */
	public static final int FileResponseMessage_Type        =800023;
	
	/**用户 FileSockect 文件通道注册消息消息		 */
	public static final int FileSockectRegMessage_Type      =100026;
//	
	public static final int FileGetedResponseMessage_Type   =800022;
	
	/**用户 添加好友消息	 */
	public static final int AddFridentMessage_Type          =100024;
	/**用户 添加好友请求消息	 */
	public static final int AddFridentRequestMessage_Type   =100025;
	/**用户 响应 添加好友请求消息	 */
	public static final int AddFridentResponseMessage_Type  =800025;
	
	public static final int ShockFrameMessage_Type          =100026;
	
	/**服务器发送客户的好友列表消息	 */
	public static final int FriendGroupMessage_Type         =100027;
	
	/**用户 视频请求接受消息	 */
	public static final int CinemaRequestMessage_Type       =100028;
	/**用户 响应 视频请求接受消息		 */
	public static final int CinemaResponseMessage_Type      =800028;
	
	
	
	
	/**登入成功的还回验证结果	 */
	public static final int LoginResponse_Succee              =1;
	/**登入失败的还回验证结果	 */
	public static final int LoginResponse_Faile               =0;
	
	/**登入失败的原因 服务器内部错误 	 */
	public static final int LoginResponse_Faile_SerReason     =0;
	/**登入失败的原因 userQQ错误 	 */
	public static final int LoginResponse_Faile_QQReason      =1;
	/**登入失败的原因 密码错误 	 */
	public static final int LoginResponse_Faile_PwdReason     =2;
	/**登入失败的原因 版本错误 	 */
	public static final int LoginResponse_Faile_VisonReason   =3;
	/**登入失败的原因 其他错误 	 */
	public static final int LoginResponse_Faile_OtherReason   =4;
	
	
	
	
	/**拒绝文件传输请求 	 */
	public static final int FileResponse_Decline              =0;
	/**接受文件传输请求 	 */
	public static final int FileResponse_Accepted             =1;
	
	/**拒绝视频传输请求 	 */
	public static final int CinemaResponse_Decline             =0;
	/**接受视频传输请求 	 */
	public static final int CinemaResponse_Accepted             =1;
	
}
