package com.mx.client.webtools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.ParserConfigurationException;









import com.mx.clent.vo.AnPeersBean;
import com.mx.client.db.DBDataSQL;
import com.mx.client.webtools.MySSLSocketFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.MapConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import sun.reflect.generics.scope.Scope;

public class ConnectionUtils {

	private final static boolean mSsl = true;
	private String mHost = null;
	private String mPort;
	private HttpParams mHttpParams;
	private SchemeRegistry mSchemeRegistry;

	public ConnectionUtils() {
		PropertiesUtils p = PropertiesUtils.getInstance();
		mHost = "https://" + p.getWebHost() + ":" + p.getWebPort();
		mPort = p.getWebPort();
		mHttpParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(mHttpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(mHttpParams, HTTP.UTF_8);
		HttpConnectionParams.setConnectionTimeout(mHttpParams, 3000); // û�н������ӳ�ʱ�����ó�ʱʱ�䣬1000����=1��
		HttpConnectionParams.setSoTimeout(mHttpParams, 12000); // û���յ�
		KeyStore trustStore;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			mSchemeRegistry = new SchemeRegistry();
			mSchemeRegistry.register(new Scheme("https", sf, Integer
					.parseInt(mPort)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class SingletonHolder {
		public static final ConnectionUtils INSTANCE = new ConnectionUtils();
	}

	public static ConnectionUtils getInstance() {
		if (SingletonHolder.INSTANCE.mPort == null) {
			SingletonHolder.INSTANCE.mPort = PropertiesUtils.getInstance()
					.getWebPort();
		}
		if (SingletonHolder.INSTANCE.mHost == null) {
			if (SingletonHolder.INSTANCE.mSsl) {
				SingletonHolder.INSTANCE.mHost = "https://"
						+ PropertiesUtils.getInstance().getWebHost() + ":"
						+ SingletonHolder.INSTANCE.mPort;
			} else {
				SingletonHolder.INSTANCE.mHost = "http://"
						+ PropertiesUtils.getInstance().getWebHost() + ":"
						+ SingletonHolder.INSTANCE.mPort;
			}
		}

		return SingletonHolder.INSTANCE;
	}

	public HttpClient initPgHttpClient() {
		if (mSsl) {
			try {
				ClientConnectionManager ccm = new ThreadSafeClientConnManager(
						mHttpParams, mSchemeRegistry);
				return new DefaultHttpClient(ccm, mHttpParams);
			} catch (Exception e) {
				e.printStackTrace();
				return new DefaultHttpClient();
			}
		} else {
			return new DefaultHttpClient();
		}
	}

	public String getRequest(String pos) throws ClientProtocolException,
			IOException {
		BufferedReader in = null;
		if (mHost == null) {
			if (mSsl) {
				mHost = "https://" + PropertiesUtils.getInstance().getWebHost()
						+ ":" + PropertiesUtils.getInstance().getWebPort();
			} else {
				mHost = "http://" + PropertiesUtils.getInstance().getWebHost()
						+ ":" + PropertiesUtils.getInstance().getWebPort();
			}
		}
		String url = mHost + pos;
		// LOG.v("web", "url:" + url + " ");
		HttpClient client = initPgHttpClient();

		StringBuffer sb = new StringBuffer("");
		try {
			HttpGet method = new HttpGet(url);
			HttpResponse response = client.execute(method);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
		} catch (Exception e) {

		}

		if (client != null && client.getConnectionManager() != null) {
			client.getConnectionManager().shutdown();
		}
		return sb.toString();
	}

	public String postRequestwithVersion(String pos,
			Map<String, Object> postContent) throws Exception {
		return postRequestwithVersion(pos, postContent, null);
	}

	public String postRequestwithVersion(String pos,
			Map<String, Object> postContent, String murl) throws Exception {
		BufferedReader in = null;
		String url = null;
		if (murl == null) {
			url = mHost + pos;
		} else {
			url = murl + pos;
		}
		HttpClient client = initPgHttpClient();
		System.out.println("url====" + url);
		try {
			HttpPost request = new HttpPost(url);
			List<BasicNameValuePair> postParameters = new ArrayList<BasicNameValuePair>();
			if (postContent != null && postContent.size() > 0) {
				Iterator<Entry<String, Object>> i = postContent.entrySet()
						.iterator();

				while (i.hasNext()) {
					Entry<String, Object> entry = i.next();

					if (entry.getValue() == null) {
						continue;
					}
					postParameters.add(new BasicNameValuePair(entry.getKey(),
							(String) entry.getValue()));
				}
			}

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, "UTF-8");
			request.addHeader("x-mx-version", "1.0");
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			System.out.println("sb===" + sb.toString());
			return sb.toString();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null && client.getConnectionManager() != null) {
				client.getConnectionManager().shutdown();
			}
		}
	}

	public String postRequest(String pos, Map<String, Object> postContent)
			throws Exception {
		return postRequest(pos, postContent, null);
	}

	public String postRequest(String pos, Map<String, Object> postContent,
			String murl) throws Exception {
		BufferedReader in = null;
		String url = null;
		if (murl == null) {
			url = mHost + pos;
		} else {
			url = murl + pos;
		}
		HttpClient client = initPgHttpClient();
		System.out.println("url" + url);
		try {
			HttpPost request = new HttpPost(url);
			List<BasicNameValuePair> postParameters = new ArrayList<BasicNameValuePair>();
			if (postContent != null && postContent.size() > 0) {
				Iterator<Entry<String, Object>> i = postContent.entrySet()
						.iterator();

				while (i.hasNext()) {
					Entry<String, Object> entry = i.next();

					if (entry.getValue() == null) {

						continue;
					}
					postParameters.add(new BasicNameValuePair(entry.getKey(),
							(String) entry.getValue()));
				}
			}

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, "UTF-8");
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			return sb.toString();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null && client.getConnectionManager() != null) {
				client.getConnectionManager().shutdown();
			}
		}
	}

	/**
	 * �@ȡ�M��
	 * 
	 * @return
	 */
	public String getContacts() {

		String xml = "";
		try {
			xml = postRequest("/backup/relations/get/"
					+ SConfig.getInstance().getProfile().getSession()
					+ "/call.xml", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("xml====>" + xml);
		String result = "";
		try {
			if (XmlUtil.instance().parseXmltoString(xml, "UTF-8", "r")
					.equalsIgnoreCase("ok")) {
				result = XmlUtil.instance().parseXmltoString(xml, "UTF-8",
						"relations");

			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public void getPubkey(String Uid) {
		String url = "/getpubkey/"
				+ SConfig.getInstance().getProfile().getSession() + "/" + Uid
				+ "/call.xml";
		System.out.println("url" + url);
		String xml = "";
		String pubkey = "";
		String time = "";
		try {
			xml = postSSLRequest(url, null, "https://www.han2011.com");
			System.out.println(xml.toString());
			if (XmlUtil.instance().parseXmltoString(xml, "UTF-8", "r")
					.equalsIgnoreCase("ok")) {
				pubkey = XmlUtil.instance().parseXmltoString(xml, "UTF-8",
						"pubkey");
				time = XmlUtil.instance()
						.parseXmltoString(xml, "UTF-8", "time");
			}
			System.out.println(pubkey + " ======== " + time);

			byte[] decoded = Base64.decodeBase64(pubkey.getBytes());
			PublicKey pkey = PubkeyUtils.decodePublic(decoded, "RSA");
			AnPeersBean bean = new AnPeersBean();
			bean.savePeerKey(Uid, pkey, time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String postSSLRequest(String pos, Map<String, Object> postContent,
			String murl) throws Exception {
		BufferedReader in = null;
		String url = null;
		if (murl == null) {
			url = mHost + pos;
		} else {
			url = murl + pos;
		}
		System.out.println("url==" + url);
		PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();

		connectionManager.setMaxTotal(1);

		HttpClient httpclient = new DefaultHttpClient(connectionManager);
		httpclient = wrapClient(httpclient);
		httpclient.getParams().setParameter("http.socket.timeout", 3000);

		httpclient.getParams().setParameter("http.connection.timeout", 3000);

		httpclient.getParams().setParameter("http.connection-manager.timeout",
				300000000L);

		//
		// // ����ܳ׿�
		// KeyStore trustStore =
		// KeyStore.getInstance(KeyStore.getDefaultType());
		// // FileInputStream instream = new FileInputStream(new
		// File("D:/zzaa"));
		// // �ܳ׿������
		// trustStore.load(null, null);
		// // ע���ܳ׿�
		// SSLSocketFactory socketFactory = new AnSSLSocketFactory(trustStore);
		// // ��У������
		// socketFactory.setHostnameVerifier(AnSSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		// Scheme sch = new Scheme("https", 443, socketFactory);
		// httpclient.getConnectionManager().getSchemeRegistry().register(sch);

		// ���HttpGet����
		HttpGet httpGet = null;

		httpGet = new HttpGet(url);

		// ��������
		HttpResponse response = httpclient.execute(httpGet);
		// �������ֵ
		InputStream is = response.getEntity().getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer("");
		String line = "";
		String NL = System.getProperty("line.separator");
		while ((line = br.readLine()) != null) {
			sb.append(line + NL);
		}
		is.close();
		System.out.println("sb======" + sb.toString());
		return sb.toString();
	}

	/**
	 * �û���ȡhttpcliect����
	 * 
	 * @param base
	 * @return
	 */

	public static org.apache.http.client.HttpClient wrapClient(
			org.apache.http.client.HttpClient base) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
			ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(
					registry);
			return new DefaultHttpClient(mgr, base.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public HashMap<String, String> postTxtMessage(
			Map<String, Object> postContent) {

		BufferedReader in = null;
		String url = "https://www.han2011.com/postmessage/"
				+ SConfig.getInstance().getProfile().getSession() + "/call.xml";
		;
		HashMap<String, String> map = new HashMap<String, String>();
		PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();

		connectionManager.setMaxTotal(1);

		HttpClient client = new DefaultHttpClient(connectionManager);
		client = wrapClient(client);
		client.getParams().setParameter("http.socket.timeout", 3000);
		client.getParams().setParameter("http.connection.timeout", 3000);
		client.getParams().setParameter("http.connection-manager.timeout",
				300000000L);
		System.out.println("url" + url);
		try {
			HttpPost request = new HttpPost(url);
			List<BasicNameValuePair> postParameters = new ArrayList<BasicNameValuePair>();
			if (postContent != null && postContent.size() > 0) {
				Iterator<Entry<String, Object>> i = postContent.entrySet()
						.iterator();

				while (i.hasNext()) {
					Entry<String, Object> entry = i.next();

					if (entry.getValue() == null) {

						continue;
					}
					postParameters.add(new BasicNameValuePair(entry.getKey(),
							(String) entry.getValue()));
				}
			}

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, "UTF-8");
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			map = XmlUtil.instance().parseXmltoMap(sb.toString(), "UTF-8");
			System.out.println("===发送一条消息的" + map.toString());
			return map;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null && client.getConnectionManager() != null) {
				client.getConnectionManager().shutdown();
			}

		}
		return null;
	}

	/**
	 * 处理信息
	 * 
	 * @param MsgId
	 */
	public void delTxtMessage(String MsgId) {

		BufferedReader in = null;
		String url = "https://www.han2011.com/delmessage/"
				+ SConfig.getInstance().getProfile().getSession() + "/" + MsgId
				+ "/call.aspx";
		;

		PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();

		connectionManager.setMaxTotal(1);

		HttpClient client = new DefaultHttpClient(connectionManager);
		client = wrapClient(client);
		client.getParams().setParameter("http.socket.timeout", 3000);
		client.getParams().setParameter("http.connection.timeout", 3000);
		client.getParams().setParameter("http.connection-manager.timeout",
				300000000L);
		System.out.println("url" + url);
		try {
			HttpPost request = new HttpPost(url);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();

			System.out.println(sb.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null && client.getConnectionManager() != null) {
				client.getConnectionManager().shutdown();
			}
		}

	}

	/**
	 * 获取群信息
	 * 
	 * @param roomId
	 * @return
	 */

	public HashMap<String, String> getRoomInfo(String roomId) {
		String xml = "";
		try {
			xml = postSSLRequest("/room/info/"
					+ SConfig.getInstance().getProfile().getSession() + "/"
					+ roomId + "/call.xml", null, "https://www.han2011.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("xml-->" + xml.toString());
		if (!"".equals(xml))
			try {
				return XmlUtil.instance().parseXmltoMap(xml, "UTF-8");
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 获取匿名群信息
	 * 
	 * @param roomId
	 * @return
	 */

	public HashMap<String, String> getMaskRoomInfo(String roomId) {
		String xml = "";
		try {
			xml = postSSLRequest("/maskroom/info/"
					+ SConfig.getInstance().getProfile().getSession() + "/"
					+ roomId + "/call.xml", null, "https://www.han2011.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("xml-->" + xml.toString());
		if (!"".equals(xml))
			try {
				return XmlUtil.instance().parseXmltoMap(xml, "UTF-8");
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	/**
	 * 发送群聊信息
	 */

	public HashMap<String, String> roomSendMsg(String sRoomId, String sMsg,
			int roomType) {
		Map<String, Object> postContent = new HashMap<String, Object>();
		postContent.put("msg", sMsg);
		String url;
		switch (roomType) {
		case DBDataSQL.ROOM_TYPE_NORMAL:
			url = "/room/sendmsg/"
					+ SConfig.getInstance().getProfile().getSession() + "/"
					+ sRoomId + "/call.xml";
			break;
		case DBDataSQL.ROOM_TYPE_MASK:
			url = "/maskroom/sendmsg/"
					+ SConfig.getInstance().getProfile().getSession() + "/"
					+ sRoomId + "/call.xml";
			break;
		default:
			url = "/room/sendmsg/"
					+ SConfig.getInstance().getProfile().getSession() + "/"
					+ sRoomId + "/call.xml";
		}
		HashMap<String, String> map = new HashMap<String, String>();
		String message;
		try {
			message = postRequest(url, postContent, "https://www.han2011.com");
			map = XmlUtil.instance().parseXmltoMap(message, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	};

	/**
	 * 正常群聊天室创建
	 * 
	 * @param sRoomSubject
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> roomCreate3(String sRoomSubject)
			throws Exception {
		Map<String, Object> postContent = new HashMap<String, Object>();

		postContent.put("subject", sRoomSubject);
		return XmlUtil.instance().parseXmltoMap(
				postRequest("/room/create/"
						+ SConfig.getInstance().getProfile().getSession()
						+ "/call.aspx", postContent), "UTF-8");
	}

	public String[] getCreateRoomResultFromServer3(String roomName)
			throws Exception {
		// 建立一个聊天室
		/*
		 * 聊天室建立成功<b><r>room_create</r><id>roomid</id><secret>secret<
		 * /secret></b> 对room内成员：<br><r>room_create</r><id>roomid<
		 * /id><secret>secret</secret></b> client收到这个消息以后，重新获取聊天室名单
		 * 聊天室建立失败<b><r>failed</r></b>
		 */
		HashMap<String, String> xmlMap;
		// xmlMap = roomCreate2(sOwner);
		xmlMap = roomCreate3(roomName);
		if (xmlMap != null && !xmlMap.isEmpty()) {
			if (xmlMap.get("r").equals("room_create")) {
				String[] roomspre = new String[] { xmlMap.get("id"),
						xmlMap.get("secret"), "-1" };
				return roomspre;
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * 匿名群创建
	 * 
	 * @param sRoomSubject
	 * @param sRoomMember
	 * @param sRoomValidity
	 * @param advanced
	 * @param allowchange
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> maskroomCreate(String sRoomSubject,
			String sRoomMember, String sRoomValidity, boolean advanced,
			boolean allowchange) throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("subject", sRoomSubject);
		values.put("members", sRoomMember);
		values.put("validity", sRoomValidity);
		if (advanced) {
			values.put("level", "advanced");
		}
		if (!allowchange) {
			values.put("rolechange", "limited");
		}
		return XmlUtil.instance().parseXmltoMap(
				postRequest("/maskroom/create/"
						+ SConfig.getInstance().getProfile().getSession()
						+ "/call.xml", values), "UTF-8");
	}

	public String[] getCreateMaskRoomResultFromServer(String roomName,
			String roomMember, String roomValidity, boolean advanced,
			boolean allowchange) throws Exception {
		// 建立一个聊天室
		/*
		 * 聊天室建立成功<b><r>room_create</r><id>roomid</id><secret>secret<
		 * /secret></b> 对room内成员：<br><r>room_create</r><id>roomid<
		 * /id><secret>secret</secret></b> client收到这个消息以后，重新获取聊天室名单
		 * 聊天室建立失败<b><r>failed</r></b>
		 */
		HashMap<String, String> xmlMap;
		// xmlMap = roomCreate2(sOwner);
		// LOG.v("wjy", roomName + "||" + roomMember + "||" + roomValidity);
		xmlMap = maskroomCreate(roomName, roomMember, roomValidity, advanced,
				allowchange);
		if (xmlMap != null && !xmlMap.isEmpty()) {
			if (xmlMap.get("r").equals("maskroom_create")) {

				String[] roomspre = new String[] { xmlMap.get("roomid"),
						xmlMap.get("secret"), xmlMap.get("omaskid") };
				return roomspre;
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * 添加公告
	 */
	public HashMap<String, String> addNotice(String roomId, String peerId,
			String content) {
		Map<String, Object> postContent = new HashMap<String, Object>();
		postContent.put("peerid", peerId);
		postContent.put("content", content);
		HashMap<String, String> xmlMap = new HashMap<String, String>();
		try {
			return XmlUtil.instance()
					.parseXmltoMap(
							postRequest("/room/notice/add/"
									+ SConfig.getInstance().getProfile()
											.getSession() + "/" + roomId
									+ "/call.xml", postContent), "UTF-8");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * 修改公告
	 * @param roomId
	 * @param peerId
	 * @param uuid
	 * @param content
	 * @return
	 */
	public  HashMap<String, String>  editNotice(String roomId, String peerId, String uuid,
			String content) {
		Map<String, Object> postContent = new HashMap<String, Object>();
		
		postContent.put("peerid", peerId);
		postContent.put("uuid", uuid);
		postContent.put("content", content);
		try {
			return XmlUtil.instance().parseXmltoMap(postRequest("/room/notice/edit/"
										+ SConfig.getInstance().getProfile()
												.getSession() + "/" + roomId
										+ "/call.xml", postContent), "UTF-8");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
    
	/**
	 * 修改公告
	 * @param roomId
	 * @param peerId
	 * @param uuid
	 * @param content
	 * @return
	 */
	public  HashMap<String, String>  deleteNotice(String roomId, String peerId, String uuid) {
		Map<String, Object> postContent = new HashMap<String, Object>();
		postContent.put("peerid", peerId);
		postContent.put("uuid", uuid);
	
		try {
			return XmlUtil.instance().parseXmltoMap(postRequest("/room/notice/delete/"
										+ SConfig.getInstance().getProfile()
												.getSession() + "/" + roomId
										+ "/call.xml", postContent), "UTF-8");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	/*
	 * 返回：<b><r>room_friends</r><u1>xxx</u1><u2>xxx</u2>...</b>
	 */
	public HashMap<String, String> roomGetFriends(String sRoomId) throws ClientProtocolException, IOException, ParserConfigurationException {
		String result="";
		try {
			result = postRequest(
					"/room/getlist/" + SConfig.getInstance().getProfile()
					.getSession() + "/" + sRoomId
							+ "/call.aspx",new HashMap<String, Object>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       System.out.println("1===>"+result);
		return XmlUtil.instance().parseXmltoMap(result,"UTF-8");
	}
	
	public HashMap<String, String> getMicInfo(String roomid) throws Exception{
		
		String result = postRequest(
				"/room/voice/info/" + SConfig.getInstance().getProfile()
				.getSession() + "/" + roomid,new HashMap<String, Object>());
		System.out.println("2===>"+result);
		return  XmlUtil.instance().parseXmltoMap(result,"UTF-8");
	}
	
	  public String getMicOwner(String roomid) throws Exception{
			String result = postRequest(
					"/room/voice/info/" + SConfig.getInstance().getProfile()
					.getSession() + "/" + roomid,new HashMap<String, Object>());
			HashMap<String, String> map =  XmlUtil.instance().parseXmltoMap(result,"UTF-8");
			System.out.println("3===>"+result);
			if(map.get("r").equals("ok")){
				return map.get("mic");
			}else{
				return null;
			}
			
		}
	  
	  public HashMap<String, String> maskroomGetFriends(String sRoomId) throws Exception {
			String result =  postRequest(
					"/maskroom/getlist/" + SConfig.getInstance().getProfile()
					.getSession() + "/" + sRoomId
							+ "/call.xml",new HashMap<String, Object>());
			return XmlUtil.instance().parseXmltoMap(result,"UTF-8");
		}
}
