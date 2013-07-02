package com.mx.client.webtools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mx.clent.vo.MsgFriendGroup;
import com.mx.clent.vo.MsgUser;

public class XmlUtil {
	private static final XmlUtil self = new XmlUtil();
	private DocumentBuilder builer = null;

	public synchronized static XmlUtil instance()
			throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		self.builer = factory.newDocumentBuilder();
		return self;
	}

	public Document createDocument(byte[] xml) throws SAXException, IOException {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(xml);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("xml解析异常");
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}

		}
		return builer.parse(is);

	}

	public String parseXmltoString(String xml, String encode, String tagName) {
		Document document = null;

		try {
			document = createDocument(xml.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		if (tagName == null) {
			System.out.println("后续扩展");
		} else {

			Element element = document.getDocumentElement();
			NodeList list = element.getElementsByTagName(tagName);
			if (list.getLength() == 0) {
				sb.append("");
				System.out.println("无数据");
			} else {
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					sb.append(node.getTextContent());
					if (i + 1 == list.getLength()) {
						sb.append("");
					} else {

						sb.append(",");
					}

				}

			}
		}

		return sb.toString();
	}

	/**
	 * 解析好友列表
	 * 
	 * @param xml
	 * @param encode
	 * @param tagName
	 * @return
	 */
	public List<MsgFriendGroup> parseXmltoString2(String xml, String encode,
			String tagName) {
		Document document = null;
		List<MsgFriendGroup> frList = new ArrayList<MsgFriendGroup>();
		MsgFriendGroup groups = new MsgFriendGroup();
		List<MsgUser> users = new ArrayList<MsgUser>();
		try {
			document = createDocument(xml.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();

		Element element = document.getDocumentElement();
		NodeList list = element.getElementsByTagName(tagName);
		if (list.getLength() == 0) {
			sb.append("");
			System.out.println("无数据");
		} else {
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				MsgUser msgUser = new MsgUser();
				for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2
						.getNextSibling()) {

					if (node2.getNodeName().equals("peerid")) {
						if (node2.getTextContent() != null)
							msgUser.setUserID(node2.getTextContent().trim());
						System.out.println("peerid==="
								+ node2.getTextContent().trim());
					}

					if (node2.getNodeName().equals("name")) {
						if (node2.getTextContent() != null)
							msgUser.setUserName(node2.getTextContent().trim());
						System.out.println("name====" + node2.getTextContent());
					}

					if (node2.getNodeName().equals("phone")) {
						if (node2.getTextContent() != null)
							msgUser.setPersonWord(node2.getTextContent().trim());
						System.out.println("phone===="
								+ node2.getTextContent().trim());
					}

				}
				msgUser.setOnline(true);
				users.add(msgUser);
			}

		}

		groups.setUserList(users);
		frList.add(groups);
		return frList;
	}

	/**
	 * 见xml解析为map
	 * 
	 * @param xml
	 * @param encode
	 * @param tagName
	 * @return
	 */
	public HashMap<String, String> parseXmltoMap(String xml, String encode) {
		HashMap<String, String> map = new HashMap<String, String>();
		Document document = null;

		try {
			document = createDocument(xml.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();


		NodeList list = document.getChildNodes();
		
		if (list.getLength() == 0) {
			sb.append("");
			System.out.println("无数据");
		} else {
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);

				for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2
						.getNextSibling()) {
					map.put(node2.getNodeName(), node2.getTextContent().trim());

				}

			}

		}
		;
		return map;
	}

	public String parseEnityToXml(Object object) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (object == null) {
			System.out.println("对象为空" + object);
			return "";
		} else {
			Class class1 = object.getClass();
			System.out.println(class1.getName());
			Field[] fields = class1.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				String stringLetter = fieldName.substring(0, 1).toUpperCase();
				String getName = "get" + stringLetter + fieldName.substring(1);
				if (getName.equals("getSerialVersionUID")) {
					continue;
				}
				Method getMethod = class1.getDeclaredMethod(getName,
						new Class[] {});
				Object value = getMethod.invoke(object, new Object[] {});
				System.out.println(class1.getName() + "属性" + field.getName()
						+ "值" + value);
			}
			return null;
		}
	}

	public static void main(String[] args) {
		String xml = "<b><r>sendmsg</r><from>27632</from><msgid>6</msgid><msg>jt8du++d4QG07vXZpOJUzWr2Sq8eMb7l/bOc/GWjWM8YyfjFF7dOP38vJL84Wx/3UToHu7wcdKXSLmpec0H1/C4kCf1TbH748nw6vkQ9pMg0SblhQcLqugHB5asr777HNyR2rYksGzLP4eo3/xqHwqzXfcSWaSuiH3C3gA51alo=</msg><time>1372773442718</time></b>";
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map=XmlUtil.instance().parseXmltoMap(xml,"UTF-8");
			System.out.println(map.toString());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
