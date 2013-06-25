package com.mx.client.msg;

public class MessageType {
	/**ϵͳ��Ϣ	     */
	public static final int SysMessage_Type                 =100010;
	
	/**�û�  ������Ϣ	 */
	public static final int OnlineMessage_Type              =100011;
	
	/**�û�  ������Ϣ	 */
	public static final int ExitMessage_Type                =100012;
	
	/**�û�  ������Ϣ	 */
	public static final int LoginMessage_Type               =100013;
	
	public static final int RepeatLoginMessage_Type         =100113;
	/**�û�  Ⱥ������Ϣ*/
	public static final int TeamMessage_Type                =100014;
	
	/**�û�  ������Ӧ��Ϣ	 */
	public static final int LoginResponseMessage_Type       =800013;
	
	/**�û� ������Ϣ	 */
	public static final int TalkMessage_Type                =100021;
	
	/**�û� �ļ���Ϣ	 */
	public static final int FileMessage_Type                =100022;
	/**�û� �ļ����������Ϣ	 */
	public static final int FileRequestMessage_Type         =100023;
	/**�û� ��Ӧ �ļ����������Ϣ		 */
	public static final int FileResponseMessage_Type        =800023;
	
	/**�û� FileSockect �ļ�ͨ��ע����Ϣ��Ϣ		 */
	public static final int FileSockectRegMessage_Type      =100026;
//	
	public static final int FileGetedResponseMessage_Type   =800022;
	
	/**�û� ��Ӻ�����Ϣ	 */
	public static final int AddFridentMessage_Type          =100024;
	/**�û� ��Ӻ���������Ϣ	 */
	public static final int AddFridentRequestMessage_Type   =100025;
	/**�û� ��Ӧ ��Ӻ���������Ϣ	 */
	public static final int AddFridentResponseMessage_Type  =800025;
	
	public static final int ShockFrameMessage_Type          =100026;
	
	/**���������Ϳͻ��ĺ����б���Ϣ	 */
	public static final int FriendGroupMessage_Type         =100027;
	
	/**�û� ��Ƶ���������Ϣ	 */
	public static final int CinemaRequestMessage_Type       =100028;
	/**�û� ��Ӧ ��Ƶ���������Ϣ		 */
	public static final int CinemaResponseMessage_Type      =800028;
	
	
	
	
	/**����ɹ��Ļ�����֤���	 */
	public static final int LoginResponse_Succee              =1;
	/**����ʧ�ܵĻ�����֤���	 */
	public static final int LoginResponse_Faile               =0;
	
	/**����ʧ�ܵ�ԭ�� �������ڲ����� 	 */
	public static final int LoginResponse_Faile_SerReason     =0;
	/**����ʧ�ܵ�ԭ�� userQQ���� 	 */
	public static final int LoginResponse_Faile_QQReason      =1;
	/**����ʧ�ܵ�ԭ�� ������� 	 */
	public static final int LoginResponse_Faile_PwdReason     =2;
	/**����ʧ�ܵ�ԭ�� �汾���� 	 */
	public static final int LoginResponse_Faile_VisonReason   =3;
	/**����ʧ�ܵ�ԭ�� �������� 	 */
	public static final int LoginResponse_Faile_OtherReason   =4;
	
	
	
	
	/**�ܾ��ļ��������� 	 */
	public static final int FileResponse_Decline              =0;
	/**�����ļ��������� 	 */
	public static final int FileResponse_Accepted             =1;
	
	/**�ܾ���Ƶ�������� 	 */
	public static final int CinemaResponse_Decline             =0;
	/**������Ƶ�������� 	 */
	public static final int CinemaResponse_Accepted             =1;
	
}
