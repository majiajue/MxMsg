package com.mx.client.webtools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
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
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

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
		HttpConnectionParams.setConnectionTimeout(mHttpParams, 3000); // 没有建立连接超时，设置超时时间，1000毫秒=1秒
		HttpConnectionParams.setSoTimeout(mHttpParams, 12000); // 没有收到
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
        System.out.println("url===="+url);
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
			System.out.println("sb==="+sb.toString());
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
	
	public String postRequest(String pos, Map<String,Object> postContent) throws Exception{
		return postRequest( pos,  postContent, null);
	}	
	public String postRequest(String pos, Map<String,Object> postContent,String murl)throws Exception {
		BufferedReader in = null;
		String url=null;
		if(murl==null)
		 {url = mHost + pos;}
		else{
			url=murl+pos;
		}
		HttpClient client = initPgHttpClient();
		
		try {
		    HttpPost request = new HttpPost(url);
			List<BasicNameValuePair> postParameters = new ArrayList<BasicNameValuePair>();
			if (postContent != null && postContent.size() > 0) {
				Iterator<Entry<String, Object>> i = postContent.entrySet().iterator();

				while (i.hasNext()) {
					Entry<String, Object> entry = i.next();

					if (entry.getValue() == null) {
						
						continue;
					}
					postParameters.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
				}
			}

			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters, "UTF-8");
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
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
	        if(client != null && client.getConnectionManager() != null) {
	            client.getConnectionManager().shutdown();
	        }			
		}
	}
	/**
	 * 獲取聯繫人
	 * @return
	 */
	public String getContacts(){
		
		String xml="";
		try {
			xml = postRequest(
					"/backup/relations/get/" + SConfig.getInstance().getSessionKey() + "/call.xml",
					null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("xml====>"+xml);
		String result= "";
		try {
			if(XmlUtil.instance().parseXmltoString(xml, "UTF-8","r").equalsIgnoreCase("ok")){
				result=XmlUtil.instance().parseXmltoString(xml, "UTF-8","relations");
				
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	

	

}
